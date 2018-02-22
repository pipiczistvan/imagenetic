package imagenetic.scene.asset.line.manager.sync;

import imagenetic.common.algorithm.genetic.Generation;
import imagenetic.scene.asset.line.genetic.entity.LayerChromosome;

import java.util.List;

public class DiscreteSynchronizer extends Synchronizer {

    public DiscreteSynchronizer(final int modelPopulationCount, final int modelPopulationSize, final List[] lineModels) {
        super(modelPopulationCount, modelPopulationSize, lineModels);
    }

    @Override
    protected Generation<LayerChromosome> calculateCurrentGeneration(final float delta) {
        return generations.get(generations.size() - 1);
    }
}
