package imagenetic.common.algorithm.genetic;

import imagenetic.common.algorithm.genetic.entity.Entity;

import java.util.List;

public class Generation<T> {

    public final List<Entity<T>> population;

    public Generation(final List<Entity<T>> population) {
        this.population = population;
    }
}
