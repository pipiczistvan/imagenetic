package imagenetic.algorithm.function;

import imagenetic.common.algorithm.genetic.function.MutationOperator;

import java.util.Random;

public class NumberMutationOperator implements MutationOperator<Integer[]> {

    private final Random random = new Random();

    @Override
    public void mutate(Integer[] genotype) {
        int index = random.nextInt(genotype.length);
        genotype[index] = random.nextInt(5) + 1;
    }
}
