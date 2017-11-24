package imagenetic.algorithm.function;

import imagenetic.common.algorithm.genetic.entity.Entity;
import imagenetic.common.algorithm.genetic.function.SelectionOperator;
import javafx.util.Pair;

import java.util.List;
import java.util.Random;

public class NumberSelectionOperator implements SelectionOperator<Integer[]> {

    @Override
    public Pair<Entity<Integer[]>, Entity<Integer[]>> select(List<Entity<Integer[]>> orderedPopulation) {
        Random random = new Random();

        int index = random.nextInt(orderedPopulation.size() - 1) + 1;

        return new Pair<>(orderedPopulation.get(0), orderedPopulation.get(index));
    }
}
