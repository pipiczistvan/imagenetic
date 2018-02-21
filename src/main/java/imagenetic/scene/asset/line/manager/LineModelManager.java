package imagenetic.scene.asset.line.manager;

import imagenetic.common.Config;
import imagenetic.common.algorithm.genetic.Generation;
import imagenetic.scene.asset.line.LineAsset;
import imagenetic.scene.asset.line.genetic.entity.LayerChromosome;
import imagenetic.scene.asset.line.manager.sync.ContinuousSynchronizer;
import imagenetic.scene.asset.line.manager.sync.DiscreteSynchronizer;
import piengine.object.model.domain.Model;
import piengine.object.model.manager.ModelManager;
import piengine.visual.image.domain.Image;
import piengine.visual.image.manager.ImageManager;
import puppeteer.annotation.premade.Component;
import puppeteer.annotation.premade.Wire;

import java.util.ArrayList;
import java.util.List;

@Component
public class LineModelManager {

    private static final int MODEL_POPULATION_COUNT = Config.MAX_POPULATION_COUNT;
    private static final int MODEL_POPULATION_SIZE = Config.MAX_POPULATION_SIZE;

    private final ModelManager modelManager;
    private final ImageManager imageManager;
    private final List[] lineModels;

    private boolean interpolated = false;

    private DiscreteSynchronizer discreteSynchronizer;
    private ContinuousSynchronizer continuousSynchronizer;

    @Wire
    public LineModelManager(final ModelManager modelManager, final ImageManager imageManager) {
        this.modelManager = modelManager;
        this.imageManager = imageManager;
        this.lineModels = new List[MODEL_POPULATION_COUNT];
    }

    public void initialize(final LineAsset parent) {
        Image blackTexture = imageManager.supply("black");
        Image grayTexture = imageManager.supply("gray");

        for (int i = 0; i < MODEL_POPULATION_COUNT; i++) {
            lineModels[i] = new ArrayList();
            for (int j = 0; j < MODEL_POPULATION_SIZE; j++) {
                Model lineModel = modelManager.supply(parent, "octahedron", grayTexture, true);
                lineModels[i].add(lineModel);
            }
        }

        discreteSynchronizer = new DiscreteSynchronizer(MODEL_POPULATION_COUNT, MODEL_POPULATION_SIZE, blackTexture, grayTexture, lineModels);
        continuousSynchronizer = new ContinuousSynchronizer(MODEL_POPULATION_COUNT, MODEL_POPULATION_SIZE, blackTexture, grayTexture, lineModels);
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

    public void setThreshold(final float threshold) {
        discreteSynchronizer.setThreshold(threshold);
        continuousSynchronizer.setThreshold(threshold);
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
}
