package imagenetic.common.algorithm.genetic.function;

import imagenetic.common.algorithm.genetic.entity.Entity;
import javafx.util.Pair;

public interface CrossoverOperator<T> {
    T crossover(Pair<Entity<T>, Entity<T>> parents);
}
