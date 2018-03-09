package imagenetic.scene.asset.voxel.genetic.function.selection;

import imagenetic.common.algorithm.genetic.entity.Entity;
import imagenetic.scene.asset.voxel.genetic.entity.LayerChromosome;
import javafx.util.Pair;
import puppeteer.annotation.premade.Component;

import java.util.List;

import static imagenetic.scene.asset.voxel.genetic.function.selection.SelectionOperatorType.RANKING;

@Component
public class RankingMode implements LayerSelectionOperatorMode {

    @Override
    public Pair<Entity<LayerChromosome>, Entity<LayerChromosome>> select(final List<Entity<LayerChromosome>> orderedPopulation) {
        return new Pair<>(orderedPopulation.get(0), orderedPopulation.get(1));
    }

    @Override
    public SelectionOperatorType getType() {
        return RANKING;
    }
}
