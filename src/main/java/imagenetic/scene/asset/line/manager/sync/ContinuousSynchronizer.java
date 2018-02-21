package imagenetic.scene.asset.line.manager.sync;

import imagenetic.common.Config;
import imagenetic.common.algorithm.genetic.Generation;
import imagenetic.common.algorithm.genetic.entity.Entity;
import imagenetic.common.util.Vector3fUtil;
import imagenetic.scene.asset.line.genetic.entity.LayerChromosome;
import imagenetic.scene.asset.line.genetic.entity.LineChromosome;
import org.joml.Vector3f;
import piengine.visual.image.domain.Image;

import java.util.ArrayList;
import java.util.List;

public class ContinuousSynchronizer extends Synchronizer {

    private Generation<LayerChromosome> oldGeneration;
    private Generation<LayerChromosome> newGeneration;

    private int lastGenerationSize = -1;
    private float progression = 0.0f;

    public ContinuousSynchronizer(final int modelPopulationCount, final int modelPopulationSize,
                                  final Image blackTexture, final Image grayTexture, final List[] lineModels) {
        super(modelPopulationCount, modelPopulationSize, blackTexture, grayTexture, lineModels);
    }

    @Override
    protected Generation<LayerChromosome> calculateCurrentGeneration(final float delta) {
        if (generations.size() != lastGenerationSize && generations.size() % Config.INTERPOLATION_THRESHOLD == 0) {
            lastGenerationSize = generations.size();

            progression = 0.0f;

            oldGeneration = currentGeneration;
            newGeneration = generations.get(generations.size() - 1);

            List<Entity<LayerChromosome>> nOldLayer = new ArrayList<>();
            List<Entity<LayerChromosome>> nNewLayer = new ArrayList<>();
            for (int i = 0; i < oldGeneration.population.size(); i++) {
                Entity<LayerChromosome> oldLayer = oldGeneration.population.get(i);
                Entity<LayerChromosome> newLayer = newGeneration.population.get(i);

                List<LineChromosome> nOldChromosomes = new ArrayList<>();
                List<LineChromosome> nNewChromosomes = new ArrayList<>();
                List<LineChromosome> choosableChromosomes = new ArrayList<>(newLayer.getGenoType().lineChromosomes);
                for (int j = 0; j < oldGeneration.population.get(i).getGenoType().lineChromosomes.size(); j++) {
                    LineChromosome oldLineChromosome = oldGeneration.population.get(i).getGenoType().lineChromosomes.get(j);

                    int chosenIndex = 0;
                    float closest = new Vector3f(oldLineChromosome.position).sub(choosableChromosomes.get(chosenIndex).position).length();
                    for (int k = 1; k < choosableChromosomes.size(); k++) {
                        float distance = new Vector3f(oldLineChromosome.position).sub(choosableChromosomes.get(k).position).length();

                        if (distance < closest) {
                            closest = distance;
                            chosenIndex = k;
                        }
                    }
                    LineChromosome newLineChromosome = choosableChromosomes.get(chosenIndex);
                    choosableChromosomes.remove(chosenIndex);

                    nOldChromosomes.add(new LineChromosome(oldLineChromosome.position, oldLineChromosome.rotation, oldLineChromosome.scale, oldLineChromosome.fitness));
                    nNewChromosomes.add(new LineChromosome(newLineChromosome.position, newLineChromosome.rotation, newLineChromosome.scale, newLineChromosome.fitness));
                }

                nOldLayer.add(new Entity<>(new LayerChromosome(nOldChromosomes), c -> oldLayer.getFitness()));
                nNewLayer.add(new Entity<>(new LayerChromosome(nNewChromosomes), c -> newLayer.getFitness()));
            }

            oldGeneration = new Generation<>(nOldLayer);
            newGeneration = new Generation<>(nNewLayer);
        }

        progression += delta;

        float interpolationProgression = progression / Config.INTERPOLATION_THRESHOLD;
        if (interpolationProgression > 1) {
            interpolationProgression = 1;
        }

        List<Entity<LayerChromosome>> currentPopulation = new ArrayList<>();
        for (int i = 0; i < oldGeneration.population.size(); i++) {
            Entity<LayerChromosome> oldLayer = oldGeneration.population.get(i);
            Entity<LayerChromosome> newLayer = newGeneration.population.get(i);

            List<LineChromosome> oldLineChromosomes = oldLayer.getGenoType().lineChromosomes;
            List<LineChromosome> newLineChromosomes = newLayer.getGenoType().lineChromosomes;

            List<LineChromosome> lineChromosomes = new ArrayList<>();
            for (int j = 0; j < oldLineChromosomes.size(); j++) {
                LineChromosome oldChromosome = oldLineChromosomes.get(j);
                LineChromosome newChromosome = newLineChromosomes.get(j);

                LineChromosome currentChromosome = new LineChromosome(
                        Vector3fUtil.interpolatePosition(oldChromosome.position, newChromosome.position, interpolationProgression),
                        Vector3fUtil.interpolateRotation(oldChromosome.rotation, newChromosome.rotation, interpolationProgression),
                        Vector3fUtil.interpolatePosition(oldChromosome.scale, newChromosome.scale, interpolationProgression),
                        newChromosome.fitness
                );
                lineChromosomes.add(currentChromosome);
            }
            currentPopulation.add(new Entity<>(new LayerChromosome(lineChromosomes), element -> newLayer.getFitness()));
        }

        return new Generation<>(currentPopulation);
    }

    @Override
    public void setGenerations(final List<Generation<LayerChromosome>> generations) {
        super.setGenerations(generations);

        this.lastGenerationSize = -1;
        this.progression = 0.0f;
        this.oldGeneration = generations.get(0);
        this.newGeneration = generations.get(0);
    }
}
