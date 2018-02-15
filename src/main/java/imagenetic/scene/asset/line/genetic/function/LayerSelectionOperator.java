package imagenetic.scene.asset.line.genetic.function;

import imagenetic.common.algorithm.genetic.entity.Entity;
import imagenetic.common.algorithm.genetic.function.SelectionOperator;
import imagenetic.scene.asset.line.genetic.entity.LayerChromosome;
import javafx.util.Pair;

import java.util.List;
import java.util.Random;

public class LayerSelectionOperator implements SelectionOperator<LayerChromosome> {

    private final Random random = new Random();

    @Override
    public Pair<Entity<LayerChromosome>, Entity<LayerChromosome>> select(final List<Entity<LayerChromosome>> orderedPopulation) {
        int leftIndex = random.nextInt(orderedPopulation.size() / 2);
        int rightIndex;

        do {
            rightIndex = random.nextInt(orderedPopulation.size());
        } while (rightIndex == leftIndex);

        Entity<LayerChromosome> parent1 = orderedPopulation.get(leftIndex);
        Entity<LayerChromosome> parent2 = orderedPopulation.get(rightIndex);

        return new Pair<>(parent1, parent2);
    }
}
