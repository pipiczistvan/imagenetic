package imagenetic.scene.asset.line.genetic.function;

import imagenetic.common.algorithm.genetic.entity.Entity;
import imagenetic.common.algorithm.genetic.function.CrossoverOperator;
import imagenetic.scene.asset.line.genetic.entity.LayerChromosome;
import imagenetic.scene.asset.line.genetic.entity.LineChromosome;
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

        List<LineChromosome> lineChromosomes = new ArrayList<>();

        int pointer = 0;
        while (lineChromosomes.size() < left.lineChromosomes.size()) {
            LineChromosome leftChromosome = left.lineChromosomes.get(pointer);
            LineChromosome rightChromosome = right.lineChromosomes.get(pointer);

            if (leftChromosome.fitness >= 1 && rightChromosome.fitness >= 1 && lineChromosomes.size() < left.lineChromosomes.size() - 2) {
                lineChromosomes.add(createNewChromosome(leftChromosome.position, leftChromosome.rotation, leftChromosome.scale));
                lineChromosomes.add(createNewChromosome(rightChromosome.position, rightChromosome.rotation, rightChromosome.scale));
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

                lineChromosomes.add(createNewChromosome(
                        interpolatePosition(leftChromosome.position, rightChromosome.position, progression),
                        interpolateRotation(leftChromosome.rotation, rightChromosome.rotation, progression),
                        leftChromosome.scale
                ));
            }

            pointer++;
        }

        return new LayerChromosome(lineChromosomes);
    }

    private LineChromosome createNewChromosome(final Vector3f position, final Vector3f rotation, final Vector3f scale) {
        return new LineChromosome(new Vector3f(position), new Vector3f(rotation), new Vector3f(scale));
    }
}
