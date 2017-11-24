package imagenetic.scene.asset.stick.genetic.function;

import imagenetic.common.algorithm.genetic.function.MutationOperator;
import imagenetic.scene.asset.stick.genetic.entity.StickChromosome;

public class StickMutationOperator implements MutationOperator<StickChromosome> {

    @Override
    public StickChromosome mutate(StickChromosome genotype) {
        return genotype;
    }
}
