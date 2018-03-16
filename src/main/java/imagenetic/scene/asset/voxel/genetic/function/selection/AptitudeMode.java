package imagenetic.scene.asset.voxel.genetic.function.selection;

import imagenetic.common.algorithm.genetic.entity.Entity;
import imagenetic.scene.asset.voxel.genetic.entity.LayerChromosome;
import javafx.util.Pair;
import puppeteer.annotation.premade.Component;

import java.util.List;
import java.util.Random;

import static imagenetic.scene.asset.voxel.genetic.function.selection.SelectionOperatorType.APTITUDE;

@Component
public class AptitudeMode implements LayerSelectionOperatorMode {

    private final Random random = new Random();

    @Override
    public Pair<Entity<LayerChromosome>, Entity<LayerChromosome>> select(List<Entity<LayerChromosome>> orderedPopulation) {
        int leftIndex = createIndex(orderedPopulation);
        int rightIndex = createIndex(orderedPopulation);

        Entity<LayerChromosome> parent1 = orderedPopulation.get(leftIndex);
        Entity<LayerChromosome> parent2 = orderedPopulation.get(rightIndex);

        return new Pair<>(parent1, parent2);
    }

    @Override
    public SelectionOperatorType getType() {
        return APTITUDE;
    }

    private int createIndex(List<Entity<LayerChromosome>> orderedPopulation) {
        double fitnessSum = orderedPopulation.stream().mapToDouble(Entity::getFitness).sum();
        int index = 0;

        for (Entity<LayerChromosome> entity : orderedPopulation) {
            double probability = entity.getFitness() / fitnessSum;
            if (probability > random.nextDouble()) {
                return index;
            }
            index++;
        }

        return 0;
    }
}
