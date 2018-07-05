package imagenetic.common.algorithm.genetic.function;

import imagenetic.common.algorithm.genetic.entity.Entity;

import java.util.List;

public interface CriterionFunction<T> {
    float value(List<Entity<T>> orderedPopulation);
}
