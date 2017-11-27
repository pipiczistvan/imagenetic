package imagenetic.scene.asset.stick.genetic.function;

import imagenetic.common.algorithm.genetic.function.MutationOperator;
import imagenetic.scene.asset.stick.genetic.entity.StickChromosome;

import java.util.Random;

public class StickMutationOperator implements MutationOperator<StickChromosome> {

    private final int maxSize;
    private final int halfMaxSize;
    private final Random random;

    public StickMutationOperator(int maxSize) {
        this.maxSize = maxSize;
        this.halfMaxSize = maxSize / 2;
        this.random = new Random();
    }

    @Override
    public StickChromosome mutate(StickChromosome genotype) {
        genotype.position.add(
                (random.nextFloat() * halfMaxSize - maxSize) * 0.5f,
                (random.nextFloat() * halfMaxSize - maxSize) * 0.5f,
                (random.nextFloat() * halfMaxSize - maxSize) * 0.5f);
        genotype.rotation.add(
                random.nextFloat() * 90 - 180,
                random.nextFloat() * 90 - 180,
                random.nextFloat() * 90 - 180);

        return genotype;
    }
}
