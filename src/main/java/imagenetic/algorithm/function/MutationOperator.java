package imagenetic.algorithm.function;

public interface MutationOperator<T> {
    T mutate(T genotype);
}
