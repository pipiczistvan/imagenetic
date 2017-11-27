package imagenetic.scene.asset.stick.genetic.function;

import imagenetic.common.algorithm.genetic.entity.Entity;
import imagenetic.common.algorithm.genetic.function.SelectionOperator;
import imagenetic.scene.asset.stick.genetic.entity.StickChromosome;
import javafx.util.Pair;
import org.joml.Vector3f;

import java.util.List;
import java.util.Random;

public class StickSelectionOperator implements SelectionOperator<StickChromosome> {

    private final Random random = new Random();
    private final Vector3f subVector = new Vector3f();

    @Override
    public Pair<Entity<StickChromosome>, Entity<StickChromosome>> select(List<Entity<StickChromosome>> orderedPopulation) {
//        int index1 = IntStream.range(0, 10).map(a -> random.nextInt(orderedPopulation.size())).min().getAsInt();
//        int index2 = IntStream.range(0, 10).map(a -> random.nextInt(orderedPopulation.size())).min().getAsInt();
//        Entity<StickChromosome> parent1 = orderedPopulation.get(index1);
//        Entity<StickChromosome> parent2 = orderedPopulation.get(index2);

//        Vector3f negPos = new Vector3f();
//        parent1.getGenoType().position.negate(negPos);
//
//        Entity<StickChromosome> parent2 = findClosest(orderedPopulation, negPos);

        Entity<StickChromosome> parent1 = orderedPopulation.get(random.nextInt(orderedPopulation.size() / 10));
        Entity<StickChromosome> parent2 = orderedPopulation.get(random.nextInt(orderedPopulation.size() / 10));
//        Entity<StickChromosome> parent2 = findClosest(orderedPopulation, parent1.getGenoType().position);

        return new Pair<>(parent1, parent2);
    }

    private Entity<StickChromosome> findClosest(List<Entity<StickChromosome>> population, Vector3f position) {
//        List<Entity<StickChromosome>> populationWithoutElement = population.stream().filter(e -> !e.equals(element)).collect(Collectors.toList());
//        Entity<StickChromosome> closestElement = element;
//
//        if (!populationWithoutElement.isEmpty()) {
//            float minLength = calculateLength(populationWithoutElement.get(0), element);
//
//            for (int i = 1; i < populationWithoutElement.size(); i++) {
//                float length = calculateLength(populationWithoutElement.get(i), element);
//
//                if (length < minLength) {
//                    minLength = length;
//                    closestElement = populationWithoutElement.get(i);
//                }
//            }
//        }
//
//        return closestElement;
        Entity<StickChromosome> closestElement = population.get(0);
        float minLength = calculateLength(population.get(0).getGenoType().position, position);

        for (int i = 1; i < population.size(); i++) {
            float length = calculateLength(population.get(i).getGenoType().position, position);

            if (length < minLength) {
                minLength = length;
                closestElement = population.get(i);
            }
        }

        return closestElement;
    }

//    private Entity<StickChromosome> findFurthest(List<Entity<StickChromosome>> population, Entity<StickChromosome> element) {
//        List<Entity<StickChromosome>> populationWithoutElement = population.stream().filter(e -> !e.equals(element)).collect(Collectors.toList());
//        Entity<StickChromosome> furthestElement = element;
//
//        if (!populationWithoutElement.isEmpty()) {
//            float maxLength = 0f;
//
//            for (Entity<StickChromosome> entity : populationWithoutElement) {
//                float length = calculateLength(entity, element);
//
//                if (length > maxLength) {
//                    maxLength = length;
//                    furthestElement = entity;
//                }
//            }
//        }
//
//        return furthestElement;
//    }

    private float calculateLength(Vector3f position1, Vector3f position2) {
        position1.sub(position2, subVector);
        return subVector.length();
    }

}
