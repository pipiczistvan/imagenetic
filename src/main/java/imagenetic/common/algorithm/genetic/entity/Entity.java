package imagenetic.common.algorithm.genetic.entity;

import imagenetic.common.algorithm.genetic.function.FitnessFunction;

import javax.annotation.Nonnull;

public class Entity<T> implements Comparable<Entity<T>> {

    private final FitnessFunction<T> fitnessFunction;

    private T genoType;
    private Float fitness;

    public Entity(T genoType, FitnessFunction<T> fitnessFunction) {
        this.fitnessFunction = fitnessFunction;

        setGenoType(genoType);
    }

    public T getGenoType() {
        return genoType;
    }

    public void setGenoType(T genoType) {
        this.genoType = genoType;
        this.fitness = fitnessFunction != null ? fitnessFunction.calculate(genoType) : 0.0f;
    }

    public Float getFitness() {
        return fitness;
    }

    @Override
    public int compareTo(@Nonnull Entity<T> entity) {
        return entity.getFitness().compareTo(getFitness());
    }
}
