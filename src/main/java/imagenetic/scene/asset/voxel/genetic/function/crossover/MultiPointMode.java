package imagenetic.scene.asset.voxel.genetic.function.crossover;

import imagenetic.common.algorithm.genetic.entity.Entity;
import imagenetic.scene.asset.voxel.genetic.entity.LayerChromosome;
import imagenetic.scene.asset.voxel.genetic.entity.VoxelChromosome;
import javafx.util.Pair;
import puppeteer.annotation.premade.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static imagenetic.scene.asset.voxel.genetic.function.crossover.CrossoverOperatorType.MULTI_POINT;

@Component
public class MultiPointMode implements LayerCrossoverOperatorMode {

    private final Random random = new Random();

    @Override
    public LayerChromosome crossover(final Pair<Entity<LayerChromosome>, Entity<LayerChromosome>> parents) {
        LayerChromosome left = parents.getKey().getGenoType();
        LayerChromosome right = parents.getValue().getGenoType();

        List<VoxelChromosome> voxelChromosomes = new ArrayList<>();

        int pointer = 0;
        boolean inheritLeft;

        while (voxelChromosomes.size() < left.voxelChromosomes.size()) {
            inheritLeft = random.nextInt(left.voxelChromosomes.size()) < pointer;

            if (inheritLeft) {
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
        return MULTI_POINT;
    }
}
