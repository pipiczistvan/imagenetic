package imagenetic.scene.asset.voxel.genetic.function;

import imagenetic.common.algorithm.genetic.function.MutationOperator;
import imagenetic.scene.asset.voxel.genetic.AlgorithmParameters;
import imagenetic.scene.asset.voxel.genetic.entity.LayerChromosome;
import imagenetic.scene.asset.voxel.genetic.entity.VoxelChromosome;
import piengine.core.base.api.Initializable;

import java.util.Random;

public class LayerMutationOperator implements MutationOperator<LayerChromosome>, Initializable {

    private final AlgorithmParameters parameters;

    private int maxSize;
    private int halfMaxSize;
    private Random random;

    public LayerMutationOperator(final AlgorithmParameters parameters) {
        this.parameters = parameters;
    }

    @Override
    public void initialize() {
        this.maxSize = parameters.getMaxSize();
        this.halfMaxSize = maxSize / 2;
        this.random = new Random();
    }

    @Override
    public void mutate(final LayerChromosome genotype) {
        for (VoxelChromosome voxelChromosome : genotype.voxelChromosomes) {
            //todo: This should be dynamic.
            if (voxelChromosome.fitness < 0.1f) {
                voxelChromosome.position.set(
                        (int) (random.nextFloat() * maxSize - halfMaxSize),
                        (int) (random.nextFloat() * maxSize - halfMaxSize),
                        (int) (random.nextFloat() * maxSize - halfMaxSize)
                );
            }
        }
    }
}
