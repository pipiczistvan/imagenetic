package imagenetic.scene.asset.stick.genetic.function;

import imagenetic.common.algorithm.genetic.entity.Entity;
import imagenetic.common.algorithm.genetic.function.CrossoverOperator;
import imagenetic.scene.asset.stick.genetic.entity.StickChromosome;
import javafx.util.Pair;

public class StickCrossoverOperator implements CrossoverOperator<StickChromosome> {

    @Override
    public StickChromosome crossover(Pair<Entity<StickChromosome>, Entity<StickChromosome>> parents) {
        return new StickChromosome(
                parents.getKey().getGenoType().position,
                parents.getValue().getGenoType().rotation,
                parents.getKey().getGenoType().scale
        );
    }
}
