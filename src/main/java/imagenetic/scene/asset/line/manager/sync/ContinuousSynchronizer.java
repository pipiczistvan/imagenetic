package imagenetic.scene.asset.line.manager.sync;

import imagenetic.common.Config;
import imagenetic.common.algorithm.genetic.Generation;
import imagenetic.common.algorithm.genetic.entity.Entity;
import imagenetic.common.util.Vector3fUtil;
import imagenetic.scene.asset.line.genetic.entity.LayerChromosome;
import imagenetic.scene.asset.line.genetic.entity.LineChromosome;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.List;

public class ContinuousSynchronizer extends Synchronizer {

    private Generation<LayerChromosome> srcGeneration;
    private Generation<LayerChromosome> destGeneration;

    private int lastGenerationSize = -1;
    private float progression = 0.0f;

    public ContinuousSynchronizer(final int modelPopulationCount, final int modelPopulationSize, final List[] lineModels) {
        super(modelPopulationCount, modelPopulationSize, lineModels);
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

        return createInterpolatedGeneration(srcGeneration, destGeneration, interpolationProgression);
    }

    @Override
    public void setGenerations(final List<Generation<LayerChromosome>> generations) {
        super.setGenerations(generations);

        setupGenerations();
        this.lastGenerationSize = -1;
        this.progression = 0.0f;
    }

    public void setupGenerations() {
        int srcIndex = generations.size() - 2;
        if (srcIndex < 0) {
            srcIndex = 0;
        }

        this.srcGeneration = generations.get(srcIndex);
        this.destGeneration = generations.get(generations.size() - 1);
    }

    private void sortGenerations(final Generation<LayerChromosome> src, final Generation<LayerChromosome> dest) {
        List<Entity<LayerChromosome>> newSrcLayers = new ArrayList<>();
        List<Entity<LayerChromosome>> newDestLayers = new ArrayList<>();
        for (int i = 0; i < src.population.size(); i++) {
            Entity<LayerChromosome> srcLayer = src.population.get(i);
            Entity<LayerChromosome> destLayer = dest.population.get(i);

            List<LineChromosome> newSrcChromosomes = new ArrayList<>();
            List<LineChromosome> newDestChromosomes = new ArrayList<>();
            List<LineChromosome> choosableChromosomes = new ArrayList<>(destLayer.getGenoType().lineChromosomes);
            for (int j = 0; j < srcLayer.getGenoType().lineChromosomes.size(); j++) {
                LineChromosome srcLineChromosome = srcLayer.getGenoType().lineChromosomes.get(j);
                LineChromosome destLineChromosome = pickClosest(choosableChromosomes, srcLineChromosome);

                newSrcChromosomes.add(new LineChromosome(srcLineChromosome));
                newDestChromosomes.add(new LineChromosome(destLineChromosome));
            }

            newSrcLayers.add(new Entity<>(new LayerChromosome(newSrcChromosomes), srcLayer.getFitness()));
            newDestLayers.add(new Entity<>(new LayerChromosome(newDestChromosomes), destLayer.getFitness()));
        }

        srcGeneration = new Generation<>(newSrcLayers);
        destGeneration = new Generation<>(newDestLayers);
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
            Entity<LayerChromosome> srcLayer = src.population.get(i);
            Entity<LayerChromosome> destLayer = dest.population.get(i);

            List<LineChromosome> srcLineChromosomes = srcLayer.getGenoType().lineChromosomes;
            List<LineChromosome> destLineChromosomes = destLayer.getGenoType().lineChromosomes;

            List<LineChromosome> lineChromosomes = new ArrayList<>();
            for (int j = 0; j < srcLineChromosomes.size(); j++) {
                LineChromosome srcChromosome = srcLineChromosomes.get(j);
                LineChromosome destChromosome = destLineChromosomes.get(j);

                LineChromosome currentChromosome = new LineChromosome(
                        Vector3fUtil.interpolatePosition(srcChromosome.position, destChromosome.position, progression),
                        Vector3fUtil.interpolateRotation(srcChromosome.rotation, destChromosome.rotation, progression),
                        Vector3fUtil.interpolatePosition(srcChromosome.scale, destChromosome.scale, progression),
                        destChromosome.fitness
                );
                lineChromosomes.add(currentChromosome);
            }
            currentPopulation.add(new Entity<>(new LayerChromosome(lineChromosomes), element -> destLayer.getFitness()));
        }

        return new Generation<>(currentPopulation);
    }
}
