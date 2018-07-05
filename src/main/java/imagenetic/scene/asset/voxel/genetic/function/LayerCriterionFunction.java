package imagenetic.scene.asset.voxel.genetic.function;

import imagenetic.common.algorithm.genetic.entity.Entity;
import imagenetic.common.algorithm.genetic.function.CriterionFunction;
import imagenetic.scene.asset.voxel.genetic.entity.LayerChromosome;

import java.util.List;

public class LayerCriterionFunction implements CriterionFunction<LayerChromosome> {

    @Override
    public float value(final List<Entity<LayerChromosome>> orderedPopulation) {
        return orderedPopulation.get(0).getFitness();
    }
}
