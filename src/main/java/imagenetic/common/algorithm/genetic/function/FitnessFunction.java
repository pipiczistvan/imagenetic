package imagenetic.common.algorithm.genetic.function;

public interface FitnessFunction<T> {
    Float calculate(T element);
}
