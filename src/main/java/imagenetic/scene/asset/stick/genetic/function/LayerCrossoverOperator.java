package imagenetic.scene.asset.stick.genetic.function;

import imagenetic.common.algorithm.genetic.entity.Entity;
import imagenetic.common.algorithm.genetic.function.CrossoverOperator;
import imagenetic.scene.asset.stick.genetic.entity.LayerChromosome;
import imagenetic.scene.asset.stick.genetic.entity.StickChromosome;
import javafx.util.Pair;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.List;

import static imagenetic.common.util.NumberUtil.nullSafeDivide;
import static imagenetic.common.util.Vector3fUtil.interpolatePosition;
import static imagenetic.common.util.Vector3fUtil.interpolateRotation;

public class LayerCrossoverOperator implements CrossoverOperator<LayerChromosome> {

    @Override
    public LayerChromosome crossover(Pair<Entity<LayerChromosome>, Entity<LayerChromosome>> parents) {
        LayerChromosome left = parents.getKey().getGenoType();
        LayerChromosome right = parents.getValue().getGenoType();

        List<StickChromosome> stickChromosomes = new ArrayList<>();

        int pointer = 0;
        while (stickChromosomes.size() < left.stickChromosomes.size()) {
            StickChromosome leftChromosome = left.stickChromosomes.get(pointer);
            StickChromosome rightChromosome = right.stickChromosomes.get(pointer);

            if (leftChromosome.fitness >= 1 && rightChromosome.fitness >= 1 && stickChromosomes.size() < left.stickChromosomes.size() - 2) {
                stickChromosomes.add(createNewChromosome(leftChromosome.position, leftChromosome.rotation, leftChromosome.scale));
                stickChromosomes.add(createNewChromosome(rightChromosome.position, rightChromosome.rotation, rightChromosome.scale));
            } else {
                float leftScaled, rightScaled;
                if (leftChromosome.fitness > rightChromosome.fitness) {
                    leftScaled = 1.0f;
                    rightScaled = nullSafeDivide(rightChromosome.fitness, leftChromosome.fitness);
                } else {
                    rightScaled = 1.0f;
                    leftScaled = nullSafeDivide(leftChromosome.fitness, rightChromosome.fitness);
                }

                float progression = 0.5f + (rightScaled - leftScaled) / 2;

                stickChromosomes.add(createNewChromosome(
                        interpolatePosition(leftChromosome.position, rightChromosome.position, progression),
                        interpolateRotation(leftChromosome.rotation, rightChromosome.rotation, progression),
                        leftChromosome.scale
                ));
            }

            pointer++;
        }

        return new LayerChromosome(stickChromosomes);
    }

    private StickChromosome createNewChromosome(final Vector3f position, final Vector3f rotation, final Vector3f scale) {
        return new StickChromosome(new Vector3f(position), new Vector3f(rotation), new Vector3f(scale));
    }
}
