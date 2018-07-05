package imagenetic.scene.asset.voxel.genetic.function.selection;

import imagenetic.common.algorithm.genetic.function.SelectionOperator;
import imagenetic.scene.asset.voxel.genetic.entity.LayerChromosome;

public interface LayerSelectionOperatorMode extends SelectionOperator<LayerChromosome> {
    SelectionOperatorType getType();
}
