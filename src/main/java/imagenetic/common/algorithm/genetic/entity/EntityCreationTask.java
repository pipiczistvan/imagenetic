package imagenetic.common.algorithm.genetic.entity;

import imagenetic.common.algorithm.genetic.function.FitnessFunction;

import java.util.List;
import java.util.concurrent.Callable;

public class EntityCreationTask<T> implements Callable<Entity<T>> {

    private final FitnessFunction<T> fitnessFunction;
    private final List<Entity<T>> entities;
    private final T genoType;

    public EntityCreationTask(final FitnessFunction<T> fitnessFunction, final List<Entity<T>> entities, final T genoType) {
        this.fitnessFunction = fitnessFunction;
        this.entities = entities;
        this.genoType = genoType;
    }

    @Override
    public Entity<T> call() {
        Entity<T> entity = new Entity<>(genoType, fitnessFunction);
        entities.add(entity);

        return entity;
    }
}
