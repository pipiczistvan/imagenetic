package imagenetic.scene.asset.voxel.genetic.function;

import imagenetic.common.algorithm.genetic.function.ChromosomeCreator;
import imagenetic.scene.asset.voxel.genetic.AlgorithmParameters;
import imagenetic.scene.asset.voxel.genetic.entity.LayerChromosome;
import imagenetic.scene.asset.voxel.genetic.entity.VoxelChromosome;
import org.joml.Vector3i;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static imagenetic.common.Config.VOXEL_RESOLUTION;

public class LayerChromosomeCreator implements ChromosomeCreator<LayerChromosome> {

    private final Random random = new Random();
    private final AlgorithmParameters parameters;
    private final int maxSize;
    private final int halfMaxSize;

    public LayerChromosomeCreator(final AlgorithmParameters parameters) {
        this.parameters = parameters;
        this.maxSize = VOXEL_RESOLUTION;
        this.halfMaxSize = maxSize / 2;
    }

    @Override
    public LayerChromosome create() {
        return new LayerChromosome(createLineChromosomes());
    }

    private List<VoxelChromosome> createLineChromosomes() {
        List<VoxelChromosome> voxelChromosomes = new ArrayList<>();
        for (int i = 0; i < parameters.relevantPixelCount; i++) {
            voxelChromosomes.add(new VoxelChromosome(new Vector3i(
                    (int) (random.nextFloat() * maxSize - halfMaxSize),
                    (int) (random.nextFloat() * maxSize - halfMaxSize),
                    (int) (random.nextFloat() * maxSize - halfMaxSize)
            )));
        }

        return voxelChromosomes;
    }
}
