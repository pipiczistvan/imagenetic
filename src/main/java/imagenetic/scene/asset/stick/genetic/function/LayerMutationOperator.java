package imagenetic.scene.asset.stick.genetic.function;

import imagenetic.common.algorithm.genetic.function.MutationOperator;
import imagenetic.scene.asset.stick.genetic.entity.LayerChromosome;
import imagenetic.scene.asset.stick.genetic.entity.StickChromosome;

import java.util.Random;

public class LayerMutationOperator implements MutationOperator<LayerChromosome> {

    private final int maxSize;
    private final int halfMaxSize;
    private final Random random;

    public LayerMutationOperator(int maxSize) {
        this.maxSize = maxSize;
        this.halfMaxSize = maxSize / 2;
        this.random = new Random();
    }

    @Override
    public void mutate(final LayerChromosome genotype) {
        for (StickChromosome stickChromosome : genotype.stickChromosomes) {
            if (stickChromosome.fitness < 0.2f) {
                stickChromosome.position.set(
                        random.nextFloat() * maxSize - halfMaxSize,
                        random.nextFloat() * maxSize - halfMaxSize,
                        random.nextFloat() * maxSize - halfMaxSize
                );
            }
            if (stickChromosome.fitness < 0.5f) {
                stickChromosome.rotation.set(
                        random.nextFloat() * 360 - 180,
                        random.nextFloat() * 360 - 180,
                        random.nextFloat() * 360 - 180);
            }
        }
    }
}
