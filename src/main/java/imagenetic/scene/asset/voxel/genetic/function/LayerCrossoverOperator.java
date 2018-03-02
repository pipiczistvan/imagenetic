package imagenetic.scene.asset.voxel.genetic.function;

import imagenetic.common.algorithm.genetic.entity.Entity;
import imagenetic.common.algorithm.genetic.function.CrossoverOperator;
import imagenetic.scene.asset.voxel.genetic.entity.LayerChromosome;
import imagenetic.scene.asset.voxel.genetic.entity.VoxelChromosome;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class LayerCrossoverOperator implements CrossoverOperator<LayerChromosome> {

    private final Random random = new Random();

    @Override
    public LayerChromosome crossover(final Pair<Entity<LayerChromosome>, Entity<LayerChromosome>> parents) {
        LayerChromosome left = parents.getKey().getGenoType();
        LayerChromosome right = parents.getValue().getGenoType();

        List<VoxelChromosome> voxelChromosomes = new ArrayList<>();

        int pointer = 0;
        while (voxelChromosomes.size() < left.voxelChromosomes.size()) {
            if (random.nextBoolean()) {
                voxelChromosomes.add(new VoxelChromosome(left.voxelChromosomes.get(pointer)));
            } else {
                voxelChromosomes.add(new VoxelChromosome(right.voxelChromosomes.get(pointer)));
            }
            pointer++;
        }

        return new LayerChromosome(voxelChromosomes);
    }
}
