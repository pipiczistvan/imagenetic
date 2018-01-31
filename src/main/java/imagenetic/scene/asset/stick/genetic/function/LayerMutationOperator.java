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
            if (stickChromosome.fitness < 0.15f) {
                stickChromosome.position.set(
                        random.nextFloat() * maxSize - halfMaxSize,
                        random.nextFloat() * maxSize - halfMaxSize,
                        random.nextFloat() * maxSize - halfMaxSize
                );
            }
            if (stickChromosome.fitness < 0.60f) {
                stickChromosome.rotation.add(
                        (1.0f - stickChromosome.fitness) * (random.nextFloat() * 45),
                        (1.0f - stickChromosome.fitness) * (random.nextFloat() * 45),
                        (1.0f - stickChromosome.fitness) * (random.nextFloat() * 45)
                );
            }
        }
    }
}
