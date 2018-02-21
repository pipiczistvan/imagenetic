package imagenetic.scene.asset.line.manager.sync;

import imagenetic.common.Config;
import imagenetic.common.algorithm.genetic.Generation;
import imagenetic.common.algorithm.genetic.entity.Entity;
import imagenetic.scene.asset.line.genetic.entity.LayerChromosome;
import imagenetic.scene.asset.line.genetic.entity.LineChromosome;
import org.joml.Vector3f;
import piengine.object.model.domain.Model;
import piengine.visual.image.domain.Image;

import java.util.List;

public abstract class Synchronizer {

    private final int modelPopulationCount;
    private final int modelPopulationSize;
    private final Image blackTexture;
    private final Image grayTexture;
    private final List[] lineModels;

    protected List<Generation<LayerChromosome>> generations;
    protected Generation<LayerChromosome> currentGeneration;

    private float viewScale = Config.DEF_SCALE;
    private boolean showAll = Config.DEF_SHOW_ALL;
    private float threshold = Config.DEF_ENTITY_THRESHOLD;

    public Synchronizer(final int modelPopulationCount, final int modelPopulationSize,
                        final Image blackTexture, final Image grayTexture, final List[] lineModels) {
        this.modelPopulationCount = modelPopulationCount;
        this.modelPopulationSize = modelPopulationSize;
        this.blackTexture = blackTexture;
        this.grayTexture = grayTexture;
        this.lineModels = lineModels;
    }

    public void synchronize(final float delta) {
        currentGeneration = calculateCurrentGeneration(delta);
        updateView();
    }

    public void updateView() {
        if (currentGeneration != null) {
            syncModelsWithChromosomes();
        }
    }

    public void setGenerations(final List<Generation<LayerChromosome>> generations) {
        this.generations = generations;
        this.currentGeneration = generations.get(0);
    }

    public void setViewScale(final float viewScale) {
        this.viewScale = viewScale;
    }

    public void setShowAll(final boolean showAll) {
        this.showAll = showAll;
    }

    public void setThreshold(final float threshold) {
        this.threshold = threshold;
    }

    protected abstract Generation<LayerChromosome> calculateCurrentGeneration(final float delta);

    private void syncModelsWithChromosomes() {
        for (int i = 0; i < modelPopulationCount; i++) {
            LayerChromosome layerChromosome = null;
            List<Entity<LayerChromosome>> population = currentGeneration.population;

            if (i < population.size()) {
                layerChromosome = population.get(i).getGenoType();
            }
            for (int j = 0; j < modelPopulationSize; j++) {
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
    }
}
