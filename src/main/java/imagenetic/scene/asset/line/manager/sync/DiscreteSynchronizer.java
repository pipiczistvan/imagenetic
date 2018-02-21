package imagenetic.scene.asset.line.manager.sync;

import imagenetic.common.algorithm.genetic.Generation;
import imagenetic.scene.asset.line.genetic.entity.LayerChromosome;
import piengine.visual.image.domain.Image;

import java.util.List;

public class DiscreteSynchronizer extends Synchronizer {

    public DiscreteSynchronizer(final int modelPopulationCount, final int modelPopulationSize,
                                final Image blackTexture, final Image grayTexture, final List[] lineModels) {
        super(modelPopulationCount, modelPopulationSize, blackTexture, grayTexture, lineModels);
    }

    @Override
    protected Generation<LayerChromosome> calculateCurrentGeneration(final float delta) {
        return generations.get(generations.size() - 1);
    }
}
