package imagenetic.scene.asset.voxel.genetic.function.crossover;

import imagenetic.common.algorithm.genetic.entity.Entity;
import imagenetic.common.util.Vector3iUtil;
import imagenetic.scene.asset.voxel.genetic.entity.LayerChromosome;
import imagenetic.scene.asset.voxel.genetic.entity.VoxelChromosome;
import javafx.util.Pair;
import puppeteer.annotation.premade.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static imagenetic.common.util.NumberUtil.nullSafeDivide;
import static imagenetic.scene.asset.voxel.genetic.function.crossover.CrossoverOperatorType.AVERAGE;

@Component
public class AverageMode implements LayerCrossoverOperatorMode {

    private final Random random = new Random();

    @Override
    public LayerChromosome crossover(final Pair<Entity<LayerChromosome>, Entity<LayerChromosome>> parents) {
        LayerChromosome left = parents.getKey().getGenoType();
        LayerChromosome right = parents.getValue().getGenoType();

        List<VoxelChromosome> voxelChromosomes = new ArrayList<>();

        int pointer = 0;
        boolean inheritLeft;

        while (voxelChromosomes.size() < left.voxelChromosomes.size()) {
            VoxelChromosome leftChromosome = left.voxelChromosomes.get(pointer);
            VoxelChromosome rightChromosome = left.voxelChromosomes.get(pointer);

            float leftScaled, rightScaled;
            if (leftChromosome.fitness > rightChromosome.fitness) {
                leftScaled = 1.0f;
                rightScaled = nullSafeDivide(rightChromosome.fitness, leftChromosome.fitness);
            } else {
                rightScaled = 1.0f;
                leftScaled = nullSafeDivide(leftChromosome.fitness, rightChromosome.fitness);
            }

            float progression = 0.5f + (rightScaled - leftScaled) / 2f;

            voxelChromosomes.add(new VoxelChromosome(Vector3iUtil.interpolatePosition(
                    leftChromosome.position, rightChromosome.position, progression
            )));

            pointer++;
        }

        return new LayerChromosome(voxelChromosomes);
    }

    @Override
    public CrossoverOperatorType getType() {
        return AVERAGE;
    }
}
