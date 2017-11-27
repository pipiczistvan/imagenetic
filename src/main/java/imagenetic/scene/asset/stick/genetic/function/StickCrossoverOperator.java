package imagenetic.scene.asset.stick.genetic.function;

import imagenetic.common.algorithm.genetic.entity.Entity;
import imagenetic.common.algorithm.genetic.function.CrossoverOperator;
import imagenetic.scene.asset.stick.genetic.entity.StickChromosome;
import javafx.util.Pair;
import org.joml.Vector3f;

public class StickCrossoverOperator implements CrossoverOperator<StickChromosome> {

    @Override
    public StickChromosome crossover(Pair<Entity<StickChromosome>, Entity<StickChromosome>> parents) {
//        return parents.getKey().getFitness() > parents.getValue().getFitness() ? parents.getKey().getGenoType() : parents.getValue().getGenoType();

//        Entity<StickChromosome> key = parents.getKey();
//        Entity<StickChromosome> value = parents.getValue();
//
//        if (key.getFitness() > value.getFitness()) {
//            return new StickChromosome(
//                    new Vector3f(key.getGenoType().position.x, key.getGenoType().position.y, key.getGenoType().position.x),
//                    key.getGenoType().rotation,
//                    key.getGenoType().scale);
//        } else {
//            return new StickChromosome(
//                    new Vector3f(value.getGenoType().position.x, value.getGenoType().position.y, value.getGenoType().position.x),
//                    value.getGenoType().rotation,
//                    value.getGenoType().scale);
//        }

        return new StickChromosome(
                calculatePosition(parents),
                calculateRotation(parents),
                calculateScale(parents)
        );
    }

    private Vector3f calculatePosition(Pair<Entity<StickChromosome>, Entity<StickChromosome>> parents) {
        Vector3f point1 = parents.getKey().getGenoType().position;
        Vector3f point2 = parents.getValue().getGenoType().position;

        float fitness1 = parents.getKey().getFitness();
        float fitness2 = parents.getValue().getFitness();

        float ratio1 = 0.5f;
        float ratio2 = 0.5f;

        if (fitness1 + fitness2 != 0) {
            ratio1 = fitness1 / (fitness1 + fitness2);
            ratio2 = fitness2 / (fitness1 + fitness2);
        }

        return new Vector3f(
                (point1.x * ratio1 + point2.x * ratio2),
                (point1.y * ratio1 + point2.y * ratio2),
                (point1.z * ratio1 + point2.z * ratio2)
        );
    }

    private Vector3f calculateRotation(Pair<Entity<StickChromosome>, Entity<StickChromosome>> parents) {
        Vector3f rotation1 = parents.getKey().getGenoType().rotation;
        Vector3f rotation2 = parents.getValue().getGenoType().rotation;

        float fitness1 = parents.getKey().getFitness();
        float fitness2 = parents.getValue().getFitness();

        float ratio1 = 0.5f;
        float ratio2 = 0.5f;

        if (fitness1 + fitness2 != 0) {
            ratio1 = fitness1 / (fitness1 + fitness2);
            ratio2 = fitness2 / (fitness1 + fitness2);
        }

        return new Vector3f(
                (rotation1.x * ratio1 + rotation2.x * ratio2),
                (rotation1.y * ratio1 + rotation2.y * ratio2),
                (rotation1.z * ratio1 + rotation2.z * ratio2)
        );
    }

    private Vector3f calculateScale(Pair<Entity<StickChromosome>, Entity<StickChromosome>> parents) {
        return parents.getKey().getGenoType().scale;
    }


}
