package imagenetic.asset.stick;

import imagenetic.algorithm.function.MutationOperator;

public class StickMutationOperator implements MutationOperator<StickChromosome> {

    @Override
    public StickChromosome mutate(StickChromosome genotype) {
        return genotype;
    }
}
