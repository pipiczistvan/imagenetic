package imagenetic.common.algorithm.genetic.function;

public interface MutationOperator<T> {
    void mutate(T genotype);
}
