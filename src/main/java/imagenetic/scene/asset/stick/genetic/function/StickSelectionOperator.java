package imagenetic.scene.asset.stick.genetic.function;

import imagenetic.common.algorithm.genetic.entity.Entity;
import imagenetic.common.algorithm.genetic.function.SelectionOperator;
import imagenetic.scene.asset.stick.genetic.entity.StickChromosome;
import javafx.util.Pair;
import org.joml.Vector3f;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class StickSelectionOperator implements SelectionOperator<StickChromosome> {

    private final Random random = new Random();
    private final Vector3f subVector = new Vector3f();

    @Override
    public Pair<Entity<StickChromosome>, Entity<StickChromosome>> select(List<Entity<StickChromosome>> orderedPopulation) {
        int index = IntStream.range(0, 1).map(a -> random.nextInt(orderedPopulation.size())).min().getAsInt();
        Entity<StickChromosome> parent1 = orderedPopulation.get(index);
        Entity<StickChromosome> parent2 = findClosest(orderedPopulation, parent1);

        return new Pair<>(parent1, parent2);
    }

    private Entity<StickChromosome> findClosest(List<Entity<StickChromosome>> population, Entity<StickChromosome> element) {
        List<Entity<StickChromosome>> populationWithoutElement = population.stream().filter(e -> !e.equals(element)).collect(Collectors.toList());
        Entity<StickChromosome> closestElement = element;

        if (!populationWithoutElement.isEmpty()) {
            float minLength = calculateLength(populationWithoutElement.get(0), element);

            for (int i = 1; i < populationWithoutElement.size(); i++) {
                float length = calculateLength(populationWithoutElement.get(i), element);

                if (length < minLength) {
                    minLength = length;
                    closestElement = populationWithoutElement.get(i);
                }
            }
        }

        return closestElement;
    }

    private float calculateLength(Entity<StickChromosome> element1, Entity<StickChromosome> element2) {
        element1.getGenoType().position.sub(element2.getGenoType().position, subVector);
        return subVector.length();
    }

}
