package imagenetic.scene.asset.voxel.genetic.function.mutation;

import imagenetic.scene.asset.voxel.genetic.VoxelGeneticAlgorithm;
import imagenetic.scene.asset.voxel.genetic.entity.LayerChromosome;
import imagenetic.scene.asset.voxel.genetic.entity.VoxelChromosome;
import puppeteer.annotation.premade.Component;

import java.util.Random;

import static imagenetic.scene.asset.voxel.genetic.function.mutation.MutationOperatorType.RANDOM;

@Component
public class RandomMode implements LayerMutationOperatorMode {

    private int maxSize;
    private int halfMaxSize;
    private final Random random = new Random();

    @Override
    public void initialize() {
        this.maxSize = VoxelGeneticAlgorithm.PARAMETERS.getMaxSize();
        this.halfMaxSize = maxSize / 2;
    }

    @Override
    public void mutate(final LayerChromosome genotype) {
        for (VoxelChromosome voxelChromosome : genotype.voxelChromosomes) {
            //todo: This should be dynamic.
            if (voxelChromosome.fitness < 0.1f) {
                voxelChromosome.position.set(
                        (int) (random.nextFloat() * maxSize - halfMaxSize),
                        (int) (random.nextFloat() * maxSize - halfMaxSize),
                        0//(int) (random.nextFloat() * maxSize - halfMaxSize)
                );
            }
        }
    }

    @Override
    public MutationOperatorType getType() {
        return RANDOM;
    }
}
