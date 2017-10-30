package imagenetic.algorithm.function;

import imagenetic.algorithm.entity.Entity;

import java.util.List;

public class NumberCriterionFunction implements CriterionFunction<Integer[]> {

    @Override
    public boolean matches(List<Entity<Integer[]>> orderedPopulation) {
        return orderedPopulation.stream().anyMatch(e -> e.getFitness().equals(1f));
    }
}
