package imagenetic.scene.asset.voxel.genetic.function;

import imagenetic.common.algorithm.genetic.function.MutationOperator;
import imagenetic.scene.asset.voxel.genetic.VoxelGeneticAlgorithm;
import imagenetic.scene.asset.voxel.genetic.entity.LayerChromosome;
import imagenetic.scene.asset.voxel.genetic.entity.VoxelChromosome;
import piengine.core.base.api.Initializable;
import puppeteer.annotation.premade.Component;

import java.util.Random;

@Component
public class LayerMutationOperator implements MutationOperator<LayerChromosome>, Initializable {

    private int maxSize;
    private int halfMaxSize;
    private Random random;

    @Override
    public void initialize() {
        this.maxSize = VoxelGeneticAlgorithm.PARAMETERS.getMaxSize();
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
