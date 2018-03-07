package imagenetic.scene.asset.voxel.manager;

import imagenetic.common.Config;
import imagenetic.common.algorithm.genetic.Generation;
import imagenetic.gui.common.api.buttons.SyncPressedListener;
import imagenetic.scene.asset.voxel.VoxelAsset;
import imagenetic.scene.asset.voxel.genetic.entity.LayerChromosome;
import imagenetic.scene.asset.voxel.manager.sync.ContinuousSynchronizer;
import imagenetic.scene.asset.voxel.manager.sync.DiscreteSynchronizer;
import piengine.core.base.type.color.Color;
import piengine.core.utils.ColorUtils;
import piengine.object.model.domain.Model;
import piengine.object.model.manager.ModelManager;
import puppeteer.annotation.premade.Component;
import puppeteer.annotation.premade.Wire;

import java.util.ArrayList;
import java.util.List;

@Component
public class LineModelManager implements SyncPressedListener {

    private static final int MODEL_POPULATION_COUNT = Config.MAX_POPULATION_COUNT;
    private static final int MODEL_POPULATION_SIZE = Config.POPULATION_SIZE;

    private final ModelManager modelManager;
    private final List[] lineModels;

    private boolean interpolated = false;

    private DiscreteSynchronizer discreteSynchronizer;
    private ContinuousSynchronizer continuousSynchronizer;

    @Wire
    public LineModelManager(final ModelManager modelManager) {
        this.modelManager = modelManager;
        this.lineModels = new List[MODEL_POPULATION_COUNT];
    }

    public void initialize(final VoxelAsset parent) {
        for (int i = 0; i < MODEL_POPULATION_COUNT; i++) {
            lineModels[i] = new ArrayList();
            for (int j = 0; j < MODEL_POPULATION_SIZE; j++) {
                Model lineModel = modelManager.supply(parent, "cube", null, new Color(ColorUtils.BLACK), true);
                lineModels[i].add(lineModel);
            }
        }

        discreteSynchronizer = new DiscreteSynchronizer(MODEL_POPULATION_COUNT, MODEL_POPULATION_SIZE, lineModels);
        continuousSynchronizer = new ContinuousSynchronizer(MODEL_POPULATION_COUNT, MODEL_POPULATION_SIZE, lineModels);
    }

    public void synchronize(final float delta) {
        if (interpolated) {
            continuousSynchronizer.synchronize(delta);
        } else {
            discreteSynchronizer.synchronize(0);
        }
    }

    public void updateView() {
        discreteSynchronizer.updateView();
    }

    public Model[] getLineModels() {
        Model[] models = new Model[MODEL_POPULATION_COUNT * MODEL_POPULATION_SIZE];
        for (int i = 0; i < MODEL_POPULATION_COUNT; i++) {
            for (int j = 0; j < MODEL_POPULATION_SIZE; j++) {
                models[i * MODEL_POPULATION_SIZE + j] = (Model) lineModels[i].get(j);
            }
        }

        return models;
    }

    public void setGenerations(final List<Generation<LayerChromosome>> generations) {
        discreteSynchronizer.setGenerations(generations);
        continuousSynchronizer.setGenerations(generations);
    }

    public void setShowAll(final boolean showAll) {
        discreteSynchronizer.setShowAll(showAll);
        continuousSynchronizer.setShowAll(showAll);
    }

    public void setViewScale(final float viewScale) {
        discreteSynchronizer.setViewScale(viewScale);
        continuousSynchronizer.setViewScale(viewScale);
    }

    public void setInterpolated(final boolean interpolated) {
        this.interpolated = interpolated;
        if (interpolated) {
            continuousSynchronizer.setupGenerations();
        }
    }

    public boolean isInterpolated() {
        return interpolated;
    }

    @Override
    public void onSyncPressed() {
        interpolated = !interpolated;
    }
}
