package imagenetic.scene.asset.voxel.genetic.function.selection;

import imagenetic.common.algorithm.genetic.entity.Entity;
import imagenetic.scene.asset.voxel.genetic.entity.LayerChromosome;
import javafx.util.Pair;
import puppeteer.annotation.premade.Component;

import java.util.List;
import java.util.Random;

import static imagenetic.scene.asset.voxel.genetic.function.selection.SelectionOperatorType.RANDOM;

@Component
public class RandomMode implements LayerSelectionOperatorMode {

    private final Random random = new Random();

    @Override
    public Pair<Entity<LayerChromosome>, Entity<LayerChromosome>> select(List<Entity<LayerChromosome>> orderedPopulation) {
        int leftIndex = random.nextInt(orderedPopulation.size());
        int rightIndex = random.nextInt(orderedPopulation.size());

        Entity<LayerChromosome> parent1 = orderedPopulation.get(leftIndex);
        Entity<LayerChromosome> parent2 = orderedPopulation.get(rightIndex);

        return new Pair<>(parent1, parent2);
    }

    @Override
    public SelectionOperatorType getType() {
        return RANDOM;
    }
}
