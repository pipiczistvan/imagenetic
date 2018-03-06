package imagenetic.scene.asset.voxel.genetic.function.mutation;

import imagenetic.common.algorithm.genetic.function.MutationOperator;
import imagenetic.scene.asset.voxel.genetic.entity.LayerChromosome;
import piengine.core.base.api.Initializable;

public interface LayerMutationOperatorMode extends MutationOperator<LayerChromosome>, Initializable {
    MutationOperatorType getType();
}
