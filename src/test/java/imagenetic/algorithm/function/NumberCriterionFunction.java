package imagenetic.algorithm.function;

import imagenetic.common.algorithm.genetic.entity.Entity;
import imagenetic.common.algorithm.genetic.function.CriterionFunction;

import java.util.List;

public class NumberCriterionFunction implements CriterionFunction<Integer[]> {

    @Override
    public float value(List<Entity<Integer[]>> orderedPopulation) {
        return orderedPopulation.get(0).getFitness();
    }
}
