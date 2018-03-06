package imagenetic.scene.asset.voxel.genetic.function.crossover;

import imagenetic.common.algorithm.genetic.function.CrossoverOperator;
import imagenetic.scene.asset.voxel.genetic.entity.LayerChromosome;

public interface LayerCrossoverOperatorMode extends CrossoverOperator<LayerChromosome> {
    CrossoverOperatorType getType();
}
