package imagenetic.scene.asset.stick.genetic.function;

import imagenetic.common.algorithm.genetic.entity.Entity;
import imagenetic.common.algorithm.genetic.function.CriterionFunction;
import imagenetic.scene.asset.stick.genetic.entity.StickChromosome;

import java.util.List;

public class StickCriterionFunction implements CriterionFunction<StickChromosome> {

    @Override
    public boolean matches(List<Entity<StickChromosome>> orderedPopulation) {
        float avg = orderedPopulation.stream()
                .map(Entity::getFitness)
                .reduce(0f, (a, b) -> a + b) / (float) orderedPopulation.size();

        return avg >= 0.5;
    }
}
