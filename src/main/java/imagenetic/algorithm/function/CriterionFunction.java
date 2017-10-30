package imagenetic.algorithm.function;

import imagenetic.algorithm.entity.Entity;

import java.util.List;

public interface CriterionFunction<T> {
    boolean matches(List<Entity<T>> orderedPopulation);
}
