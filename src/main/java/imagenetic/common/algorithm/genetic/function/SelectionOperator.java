package imagenetic.common.algorithm.genetic.function;

import imagenetic.common.algorithm.genetic.entity.Entity;
import javafx.util.Pair;

import java.util.List;

public interface SelectionOperator<T> {
    Pair<Entity<T>, Entity<T>> select(List<Entity<T>> orderedPopulation);
}
