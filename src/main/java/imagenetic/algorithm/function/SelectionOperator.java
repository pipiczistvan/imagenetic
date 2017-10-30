package imagenetic.algorithm.function;

import imagenetic.algorithm.entity.Entity;
import javafx.util.Pair;

import java.util.List;

public interface SelectionOperator<T> {
    Pair<Entity<T>, Entity<T>> select(List<Entity<T>> orderedPopulation);
}
