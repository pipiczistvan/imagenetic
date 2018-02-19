package imagenetic.scene.asset.line;

import imagenetic.common.Config;
import imagenetic.common.algorithm.genetic.entity.Entity;
import imagenetic.scene.asset.line.genetic.entity.LayerChromosome;
import imagenetic.scene.asset.line.genetic.entity.LineChromosome;
import org.joml.Vector3f;
import piengine.core.base.api.Initializable;
import piengine.object.model.domain.Model;
import piengine.object.model.manager.ModelManager;
import piengine.visual.image.domain.Image;
import piengine.visual.image.manager.ImageManager;

import java.util.ArrayList;
import java.util.List;

public class LineModelManager implements Initializable {

    private static final int INTERPOLATION_THRESHOLD = 5;
    private static final int MODEL_POPULATION_COUNT = Config.MAX_POPULATION_COUNT;
    private static final int MODEL_POPULATION_SIZE = Config.MAX_POPULATION_SIZE;

    private final LineAsset parent;
    private final ModelManager modelManager;
    private final ImageManager imageManager;
    private final List[] lineModels;

    private Image blackTexture;
    private Image grayTexture;

    private List<Entity<LayerChromosome>> actualPopulation = null;

    private boolean showAll = false;
    private float threshold = Config.DEF_ENTITY_THRESHOLD;
    private float viewScale = 1;

    private int interpolationIndex = 0;

    public LineModelManager(final LineAsset parent, final ModelManager modelManager, final ImageManager imageManager) {
        this.parent = parent;
        this.modelManager = modelManager;
        this.imageManager = imageManager;
        this.lineModels = new List[MODEL_POPULATION_COUNT];
    }

    @Override
    public void initialize() {
        blackTexture = imageManager.supply("black");
        grayTexture = imageManager.supply("gray");

        createModels();
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

    public void setShowAll(final boolean showAll) {
        this.showAll = showAll;
    }

    public void setThreshold(final float threshold) {
        this.threshold = threshold;
    }

    public void setViewScale(final float viewScale) {
        this.viewScale = viewScale;
    }

    public void setActualPopulation(final List<Entity<LayerChromosome>> actualPopulation) {
        this.actualPopulation = actualPopulation;
    }

    public void interpolate(final float delta) {
        if (actualPopulation != null) {
            // oldPopulation

            if (interpolationIndex < INTERPOLATION_THRESHOLD) {
                interpolationIndex++;
                actualPopulation = null;
            } else {
                interpolationIndex = 0;
                // newPopulation
                synchronize();
            }
        }

        // interpolation
    }

    public void synchronize() {
        if (actualPopulation == null) {
            return;
        }

        for (int i = 0; i < MODEL_POPULATION_COUNT; i++) {
            LayerChromosome layerChromosome = null;
            if (i < actualPopulation.size()) {
                layerChromosome = actualPopulation.get(i).getGenoType();
            }
            for (int j = 0; j < MODEL_POPULATION_SIZE; j++) {
                Model lineModel = (Model) lineModels[i].get(j);
                if (layerChromosome != null) {
                    if (j < layerChromosome.lineChromosomes.size()) {
                        LineChromosome chromosome = layerChromosome.lineChromosomes.get(j);

                        lineModel.setPosition(new Vector3f(chromosome.position).mul(viewScale));
                        lineModel.setRotation(new Vector3f(chromosome.rotation));
                        lineModel.setScale(new Vector3f(chromosome.scale).mul(viewScale));

                        if (i == 0) {
                            lineModel.texture = blackTexture;
                            lineModel.visible = chromosome.fitness >= threshold;
                        } else {
                            lineModel.texture = grayTexture;
                            lineModel.visible = showAll && chromosome.fitness >= threshold;
                        }
                    } else {
                        lineModel.visible = false;
                    }
                } else {
                    lineModel.visible = false;
                }
            }
        }

        actualPopulation = null;
    }

    private void createModels() {
        for (int i = 0; i < MODEL_POPULATION_COUNT; i++) {
            lineModels[i] = new ArrayList();
            for (int j = 0; j < MODEL_POPULATION_SIZE; j++) {
                Model lineModel = modelManager.supply(parent, "octahedron", grayTexture, true);
                lineModels[i].add(lineModel);
            }
        }
    }
}
