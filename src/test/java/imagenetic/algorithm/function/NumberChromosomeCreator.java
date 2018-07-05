package imagenetic.algorithm.function;

import imagenetic.common.algorithm.genetic.function.ChromosomeCreator;

import java.util.Random;

public class NumberChromosomeCreator implements ChromosomeCreator<Integer[]> {

    private final Random random = new Random();

    @Override
    public Integer[] create() {
        return new Integer[]{
                random.nextInt(10),
                random.nextInt(10),
                random.nextInt(10),
                random.nextInt(10)
        };
    }
}
