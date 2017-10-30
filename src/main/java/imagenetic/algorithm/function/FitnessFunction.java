package imagenetic.algorithm.function;

public interface FitnessFunction<T> {
    Float calculate(T element);
}
