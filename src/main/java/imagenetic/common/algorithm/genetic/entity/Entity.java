package imagenetic.common.algorithm.genetic.entity;

import imagenetic.common.algorithm.genetic.function.FitnessFunction;

import javax.annotation.Nonnull;

public class Entity<T> implements Comparable<Entity<T>> {

    private T genoType;
    private Float fitness;

    public Entity(final T genoType, final FitnessFunction<T> fitnessFunction) {
        this.genoType = genoType;
        this.fitness = fitnessFunction != null ? fitnessFunction.calculate(genoType) : 0.0f;
    }

    public Entity(final T genoType, final float fitness) {
        this.genoType = genoType;
        this.fitness = fitness;
    }

    public T getGenoType() {
        return genoType;
    }

    public Float getFitness() {
        return fitness;
    }

    @Override
    public int compareTo(@Nonnull Entity<T> entity) {
        return entity.getFitness().compareTo(getFitness());
    }
}
