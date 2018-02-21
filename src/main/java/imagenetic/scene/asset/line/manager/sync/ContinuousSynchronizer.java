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

            sortGenerations(currentGeneration, generations.get(generations.size() - 1));
        }

        progression += delta;
        float interpolationProgression = progression / Config.INTERPOLATION_THRESHOLD;
        if (interpolationProgression > 1) {
            interpolationProgression = 1;
        }

        return createInterpolatedGeneration(oldGeneration, newGeneration, interpolationProgression);
    }

    @Override
    public void setGenerations(final List<Generation<LayerChromosome>> generations) {
        super.setGenerations(generations);

        setupGenerations();
        this.lastGenerationSize = -1;
        this.progression = 0.0f;
    }

    public void setupGenerations() {
        this.oldGeneration = currentGeneration;
        this.newGeneration = generations.get(generations.size() - 1);
    }

    private void sortGenerations(final Generation<LayerChromosome> src, final Generation<LayerChromosome> dest) {
        List<Entity<LayerChromosome>> nOldLayer = new ArrayList<>();
        List<Entity<LayerChromosome>> nNewLayer = new ArrayList<>();
        for (int i = 0; i < src.population.size(); i++) {
            Entity<LayerChromosome> oldLayer = src.population.get(i);
            Entity<LayerChromosome> newLayer = dest.population.get(i);

            List<LineChromosome> nOldChromosomes = new ArrayList<>();
            List<LineChromosome> nNewChromosomes = new ArrayList<>();
            List<LineChromosome> choosableChromosomes = new ArrayList<>(newLayer.getGenoType().lineChromosomes);
            for (int j = 0; j < src.population.get(i).getGenoType().lineChromosomes.size(); j++) {
                LineChromosome oldLineChromosome = src.population.get(i).getGenoType().lineChromosomes.get(j);
                LineChromosome newLineChromosome = pickClosest(choosableChromosomes, oldLineChromosome);

                nOldChromosomes.add(new LineChromosome(oldLineChromosome));
                nNewChromosomes.add(new LineChromosome(newLineChromosome));
            }

            nOldLayer.add(new Entity<>(new LayerChromosome(nOldChromosomes), c -> oldLayer.getFitness()));
            nNewLayer.add(new Entity<>(new LayerChromosome(nNewChromosomes), c -> newLayer.getFitness()));
        }

        oldGeneration = new Generation<>(nOldLayer);
        newGeneration = new Generation<>(nNewLayer);
    }

    private LineChromosome pickClosest(final List<LineChromosome> choosables, final LineChromosome reference) {
        int chosenIndex = 0;

        float closest = new Vector3f(reference.position).sub(choosables.get(chosenIndex).position).length();
        for (int k = 1; k < choosables.size(); k++) {
            float distance = new Vector3f(reference.position).sub(choosables.get(k).position).length();

            if (distance < closest) {
                closest = distance;
                chosenIndex = k;
            }
        }
        LineChromosome chosenChromosome = choosables.get(chosenIndex);
        choosables.remove(chosenIndex);

        return chosenChromosome;
    }

    private Generation<LayerChromosome> createInterpolatedGeneration(final Generation<LayerChromosome> src, final Generation<LayerChromosome> dest, final float progression) {
        List<Entity<LayerChromosome>> currentPopulation = new ArrayList<>();
        for (int i = 0; i < src.population.size(); i++) {
            Entity<LayerChromosome> oldLayer = src.population.get(i);
            Entity<LayerChromosome> newLayer = dest.population.get(i);

            List<LineChromosome> oldLineChromosomes = oldLayer.getGenoType().lineChromosomes;
            List<LineChromosome> newLineChromosomes = newLayer.getGenoType().lineChromosomes;

            List<LineChromosome> lineChromosomes = new ArrayList<>();
            for (int j = 0; j < oldLineChromosomes.size(); j++) {
                LineChromosome oldChromosome = oldLineChromosomes.get(j);
                LineChromosome newChromosome = newLineChromosomes.get(j);

                LineChromosome currentChromosome = new LineChromosome(
                        Vector3fUtil.interpolatePosition(oldChromosome.position, newChromosome.position, progression),
                        Vector3fUtil.interpolateRotation(oldChromosome.rotation, newChromosome.rotation, progression),
                        Vector3fUtil.interpolatePosition(oldChromosome.scale, newChromosome.scale, progression),
                        newChromosome.fitness
                );
                lineChromosomes.add(currentChromosome);
            }
            currentPopulation.add(new Entity<>(new LayerChromosome(lineChromosomes), element -> newLayer.getFitness()));
        }

        return new Generation<>(currentPopulation);
    }
}
