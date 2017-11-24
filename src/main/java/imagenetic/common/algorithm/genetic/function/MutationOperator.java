package imagenetic.common.algorithm.genetic.function;

public interface MutationOperator<T> {
    T mutate(T genotype);
}
