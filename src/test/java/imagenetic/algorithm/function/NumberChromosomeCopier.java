package imagenetic.algorithm.function;

import imagenetic.common.algorithm.genetic.function.ChromosomeCopier;

import java.util.Arrays;

public class NumberChromosomeCopier implements ChromosomeCopier<Integer[]> {

    @Override
    public Integer[] copy(final Integer[] genotype) {
        return Arrays.copyOf(genotype, genotype.length);
    }
}
