package imagenetic.scene.asset.voxel.manager.sync;

import imagenetic.common.Config;
import imagenetic.common.algorithm.genetic.Generation;
import imagenetic.common.algorithm.genetic.entity.Entity;
import imagenetic.common.util.Vector3iUtil;
import imagenetic.scene.asset.voxel.genetic.entity.LayerChromosome;
import imagenetic.scene.asset.voxel.genetic.entity.VoxelChromosome;
import org.joml.Vector2i;

import java.util.ArrayList;
import java.util.List;

public class ContinuousSynchronizer extends Synchronizer {

    private final Vector2i referencePosition = new Vector2i();
    private final Vector2i chosenPosition = new Vector2i();

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

            List<VoxelChromosome> newSrcChromosomes = new ArrayList<>();
            List<VoxelChromosome> newDestChromosomes = new ArrayList<>();
            List<VoxelChromosome> choosableChromosomes = new ArrayList<>(destLayer.getGenoType().voxelChromosomes);
            for (int j = 0; j < srcLayer.getGenoType().voxelChromosomes.size(); j++) {
                VoxelChromosome srcVoxelChromosome = srcLayer.getGenoType().voxelChromosomes.get(j);
                VoxelChromosome destVoxelChromosome = pickClosest(choosableChromosomes, srcVoxelChromosome);

                newSrcChromosomes.add(new VoxelChromosome(srcVoxelChromosome));
                newDestChromosomes.add(new VoxelChromosome(destVoxelChromosome));
            }

            newSrcLayers.add(new Entity<>(new LayerChromosome(newSrcChromosomes), srcLayer.getFitness()));
            newDestLayers.add(new Entity<>(new LayerChromosome(newDestChromosomes), destLayer.getFitness()));
        }

        srcGeneration = new Generation<>(newSrcLayers);
        destGeneration = new Generation<>(newDestLayers);
    }

    private VoxelChromosome pickClosest(final List<VoxelChromosome> choosables, final VoxelChromosome reference) {
        int chosenIndex = 0;

        referencePosition.set(reference.position.x, reference.position.y);
        chosenPosition.set(choosables.get(chosenIndex).position.x, choosables.get(chosenIndex).position.y);

        double closest = new Vector2i(referencePosition).sub(chosenPosition).length();
        for (int k = 1; k < choosables.size(); k++) {
            chosenPosition.set(choosables.get(k).position.x, choosables.get(k).position.y);
            double distance = new Vector2i(referencePosition).sub(chosenPosition).length();

            if (distance < closest) {
                closest = distance;
                chosenIndex = k;
            }
        }
        VoxelChromosome chosenChromosome = choosables.get(chosenIndex);
        choosables.remove(chosenIndex);

        return chosenChromosome;
    }

    private Generation<LayerChromosome> createInterpolatedGeneration(final Generation<LayerChromosome> src, final Generation<LayerChromosome> dest, final float progression) {
        List<Entity<LayerChromosome>> currentPopulation = new ArrayList<>();
        for (int i = 0; i < src.population.size(); i++) {
            Entity<LayerChromosome> srcLayer = src.population.get(i);
            Entity<LayerChromosome> destLayer = dest.population.get(i);

            List<VoxelChromosome> srcVoxelChromosomes = srcLayer.getGenoType().voxelChromosomes;
            List<VoxelChromosome> destVoxelChromosomes = destLayer.getGenoType().voxelChromosomes;

            List<VoxelChromosome> voxelChromosomes = new ArrayList<>();
            for (int j = 0; j < srcVoxelChromosomes.size(); j++) {
                VoxelChromosome srcChromosome = srcVoxelChromosomes.get(j);
                VoxelChromosome destChromosome = destVoxelChromosomes.get(j);

                VoxelChromosome currentChromosome = new VoxelChromosome(
                        Vector3iUtil.interpolatePosition(srcChromosome.position, destChromosome.position, progression),
                        destChromosome.fitness
                );
                voxelChromosomes.add(currentChromosome);
            }
            currentPopulation.add(new Entity<>(new LayerChromosome(voxelChromosomes), element -> destLayer.getFitness()));
        }

        return new Generation<>(currentPopulation);
    }
}
