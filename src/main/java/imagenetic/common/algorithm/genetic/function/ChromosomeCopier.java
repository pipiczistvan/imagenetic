package imagenetic.common.algorithm.genetic.function;

public interface ChromosomeCopier<T> {
    T copy(T genotype);
}
