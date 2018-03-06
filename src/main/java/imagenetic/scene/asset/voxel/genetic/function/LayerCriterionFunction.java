package imagenetic.scene.asset.voxel.genetic.function;

import imagenetic.common.algorithm.genetic.entity.Entity;
import imagenetic.common.algorithm.genetic.function.CriterionFunction;
import imagenetic.scene.asset.voxel.genetic.entity.LayerChromosome;
import imagenetic.scene.asset.voxel.genetic.entity.VoxelChromosome;

import java.util.List;

public class LayerCriterionFunction implements CriterionFunction<LayerChromosome> {

    @Override
    public float value(List<Entity<LayerChromosome>> orderedPopulation) {
        List<VoxelChromosome> chromosomes = orderedPopulation.get(0).getGenoType().voxelChromosomes;

        float avg = chromosomes.stream()
                .map(c -> c.fitness)
                .reduce(0f, (a, b) -> a + b) / (float) chromosomes.size();

        return avg;
    }
}
