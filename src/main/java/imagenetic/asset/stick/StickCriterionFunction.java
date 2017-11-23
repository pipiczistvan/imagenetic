package imagenetic.asset.stick;

import imagenetic.algorithm.entity.Entity;
import imagenetic.algorithm.function.CriterionFunction;

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
