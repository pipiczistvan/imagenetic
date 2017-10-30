package imagenetic.algorithm.function;

import imagenetic.algorithm.entity.Entity;
import javafx.util.Pair;

import java.util.Random;

public class NumberCrossOverOperator implements CrossOverOperator<Integer[]> {

    @Override
    public Integer[] crossOver(Pair<Entity<Integer[]>, Entity<Integer[]>> parents) {
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
