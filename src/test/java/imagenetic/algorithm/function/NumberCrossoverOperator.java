package imagenetic.algorithm.function;

import imagenetic.common.algorithm.genetic.entity.Entity;
import imagenetic.common.algorithm.genetic.function.CrossoverOperator;
import javafx.util.Pair;

import java.util.Random;

public class NumberCrossoverOperator implements CrossoverOperator<Integer[]> {

    @Override
    public Integer[] crossover(Pair<Entity<Integer[]>, Entity<Integer[]>> parents) {
        Integer[] left = parents.getKey().getGenoType();
        Integer[] right = parents.getValue().getGenoType();

        Integer[] child = new Integer[4];
        Random random = new Random();

        for (int i = 0; i < child.length; i++) {
            child[i] = random.nextInt(1) == 0 ? left[i] : right[i];
        }

        return child;
    }
}
