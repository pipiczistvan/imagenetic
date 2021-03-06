package imagenetic.scene.asset.line.manager.sync;

import imagenetic.common.Config;
import imagenetic.common.algorithm.genetic.Generation;
import imagenetic.common.algorithm.genetic.entity.Entity;
import imagenetic.scene.asset.line.genetic.entity.LayerChromosome;
import imagenetic.scene.asset.line.genetic.entity.LineChromosome;
import org.joml.Vector3f;
import piengine.core.utils.ColorUtils;
import piengine.object.model.domain.Model;

import java.util.List;

public abstract class Synchronizer {

    private final int modelPopulationCount;
    private final int modelPopulationSize;
    private final List[] lineModels;

    protected static Generation<LayerChromosome> currentGeneration;
    protected List<Generation<LayerChromosome>> generations;

    private float viewScale = Config.DEF_SCALE;
    private boolean showAll = Config.DEF_SHOW_ALL;
    private double threshold = Config.DEF_ENTITY_THRESHOLD;

    public Synchronizer(final int modelPopulationCount, final int modelPopulationSize, final List[] lineModels) {
        this.modelPopulationCount = modelPopulationCount;
        this.modelPopulationSize = modelPopulationSize;
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
        currentGeneration = generations.get(0);
    }

    public void setViewScale(final float viewScale) {
        this.viewScale = viewScale;
    }

    public void setShowAll(final boolean showAll) {
        this.showAll = showAll;
    }

    public void setThreshold(final double threshold) {
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
                            lineModel.color.set(ColorUtils.BLACK);
                            lineModel.visible = chromosome.fitness >= threshold;
                        } else {
                            lineModel.color.set(0.3f, 0.3f, 0.3f);
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
