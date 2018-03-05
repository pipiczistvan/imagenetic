package imagenetic.scene.asset.voxel.genetic.function;

import imagenetic.common.algorithm.genetic.entity.Entity;
import imagenetic.common.algorithm.genetic.function.CriterionFunction;
import imagenetic.scene.asset.voxel.genetic.entity.LayerChromosome;

import java.util.List;

public class LayerCriterionFunction implements CriterionFunction<LayerChromosome> {

    @Override
    public boolean matches(final List<Entity<LayerChromosome>> orderedPopulation) {
        float avg = orderedPopulation.stream()
                .map(Entity::getFitness)
                .reduce(0f, (a, b) -> a + b) / (float) orderedPopulation.size();

        return avg >= 0.9;
    }
}