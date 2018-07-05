package imagenetic.scene.asset.voxel.genetic.function.crossover;

import imagenetic.common.algorithm.genetic.entity.Entity;
import imagenetic.scene.asset.voxel.genetic.entity.LayerChromosome;
import imagenetic.scene.asset.voxel.genetic.entity.VoxelChromosome;
import javafx.util.Pair;
import org.joml.Vector3i;
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

        int index = random.nextInt(3);
        int pointer = 0;
        while (voxelChromosomes.size() < left.voxelChromosomes.size()) {
            Vector3i leftPosition = left.voxelChromosomes.get(pointer).position;
            Vector3i rightPosition = right.voxelChromosomes.get(pointer).position;

            int x = leftPosition.x;
            int y = leftPosition.y;
            int z = leftPosition.z;

            if (index > 0) {
                x = rightPosition.x;
                if (index > 1) {
                    y = rightPosition.y;
                    if (index > 2) {
                        z = rightPosition.z;
                    }
                }
            }

            voxelChromosomes.add(new VoxelChromosome(new Vector3i(x, y, z)));

            pointer++;
        }

        return new LayerChromosome(voxelChromosomes);
    }

    @Override
    public CrossoverOperatorType getType() {
        return ONE_POINT;
    }
}
