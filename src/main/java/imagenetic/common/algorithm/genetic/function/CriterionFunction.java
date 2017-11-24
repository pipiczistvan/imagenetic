package imagenetic.common.algorithm.genetic.function;

import imagenetic.common.algorithm.genetic.entity.Entity;

import java.util.List;

public interface CriterionFunction<T> {
    boolean matches(List<Entity<T>> orderedPopulation);
}
