package imagenetic.scene.asset.voxel.genetic.function.crossover;

import imagenetic.common.algorithm.genetic.entity.Entity;
import imagenetic.scene.asset.voxel.genetic.entity.LayerChromosome;
import imagenetic.scene.asset.voxel.genetic.entity.VoxelChromosome;
import javafx.util.Pair;
import puppeteer.annotation.premade.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static imagenetic.scene.asset.voxel.genetic.function.crossover.CrossoverOperatorType.ONE_POINT;

@Component
public class OnePointMode implements LayerCrossoverOperatorMode {

    private final Random random = new Random();

    @Override
    public LayerChromosome crossover(final Pair<Entity<LayerChromosome>, Entity<LayerChromosome>> parents) {
        LayerChromosome left = parents.getKey().getGenoType();
        LayerChromosome right = parents.getValue().getGenoType();

        List<VoxelChromosome> voxelChromosomes = new ArrayList<>();

        int index = random.nextInt(left.voxelChromosomes.size());
        int pointer = 0;
        while (voxelChromosomes.size() < left.voxelChromosomes.size()) {
            if (pointer < index) {
                voxelChromosomes.add(new VoxelChromosome(left.voxelChromosomes.get(pointer)));
            } else {
                voxelChromosomes.add(new VoxelChromosome(right.voxelChromosomes.get(pointer)));
            }
            pointer++;
        }

        return new LayerChromosome(voxelChromosomes);
    }

    @Override
    public CrossoverOperatorType getType() {
        return ONE_POINT;
    }
}
