package imagenetic.scene.asset.voxel.genetic.function;

import imagenetic.common.algorithm.genetic.function.ChromosomeCreator;
import imagenetic.scene.asset.voxel.genetic.AlgorithmParameters;
import imagenetic.scene.asset.voxel.genetic.entity.LayerChromosome;
import imagenetic.scene.asset.voxel.genetic.entity.VoxelChromosome;

import java.util.ArrayList;
import java.util.List;

public class LayerChromosomeCreator implements ChromosomeCreator<LayerChromosome> {

    private final AlgorithmParameters parameters;

    public LayerChromosomeCreator(final AlgorithmParameters parameters) {
        this.parameters = parameters;
    }

    @Override
    public LayerChromosome create() {
        return new LayerChromosome(createLineChromosomes());
    }

    private List<VoxelChromosome> createLineChromosomes() {
        List<VoxelChromosome> voxelChromosomes = new ArrayList<>();
        for (int i = 0; i < parameters.relevantPixelCount; i++) {
            voxelChromosomes.add(new VoxelChromosome());
        }

        return voxelChromosomes;
    }
}
