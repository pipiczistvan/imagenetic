package imagenetic.algorithm.function;

import imagenetic.algorithm.entity.Entity;
import javafx.util.Pair;

public interface CrossOverOperator<T> {
    T crossOver(Pair<Entity<T>, Entity<T>> parents);
}
