package imagenetic.scene.asset.line;

import imagenetic.common.Config;
import imagenetic.common.algorithm.genetic.Generation;
import imagenetic.common.algorithm.genetic.entity.Entity;
import imagenetic.common.util.Vector3fUtil;
import imagenetic.scene.asset.line.genetic.entity.LayerChromosome;
import imagenetic.scene.asset.line.genetic.entity.LineChromosome;
import org.joml.Vector3f;
import piengine.object.model.domain.Model;
import piengine.object.model.manager.ModelManager;
import piengine.visual.image.domain.Image;
import piengine.visual.image.manager.ImageManager;

import java.util.ArrayList;
import java.util.List;

public class LineModelManager {

    private static final int INTERPOLATION_THRESHOLD = 10;
    private static final int MODEL_POPULATION_COUNT = Config.MAX_POPULATION_COUNT;
    private static final int MODEL_POPULATION_SIZE = Config.MAX_POPULATION_SIZE;

    private final LineAsset parent;
    private final ModelManager modelManager;
    private final ImageManager imageManager;
    private final List<Generation<LayerChromosome>> generations;
    private final List[] lineModels;

    private Image blackTexture;
    private Image grayTexture;

    private Generation<LayerChromosome> currentGeneration;

    private Generation<LayerChromosome> oldGeneration;
    private Generation<LayerChromosome> newGeneration;
    private int lastGenerationSize = -1;
    private float progression = 0.0f;

    private boolean showAll = false;
    private float threshold = Config.DEF_ENTITY_THRESHOLD;
    private float viewScale = 1;

    public LineModelManager(final LineAsset parent, final ModelManager modelManager, final ImageManager imageManager, final List<Generation<LayerChromosome>> generations) {
        this.parent = parent;
        this.modelManager = modelManager;
        this.imageManager = imageManager;
        this.generations = generations;
        this.lineModels = new List[MODEL_POPULATION_COUNT];
    }

    public void initializeModels() {
        blackTexture = imageManager.supply("black");
        grayTexture = imageManager.supply("gray");

        createModels();
    }

    public void initializeGenerations() {
        lastGenerationSize = -1;
        progression = 0.0f;
        oldGeneration = generations.get(0);
        newGeneration = generations.get(0);
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

    public void interpolate(final float elapsedTime) {
        if (generations.size() != lastGenerationSize && generations.size() % INTERPOLATION_THRESHOLD == 0) {
            lastGenerationSize = generations.size();

            progression = 0.0f;

            oldGeneration = currentGeneration;
            newGeneration = generations.get(generations.size() - 1);

            List<Entity<LayerChromosome>> nOldLayer = new ArrayList<>();
            List<Entity<LayerChromosome>> nNewLayer = new ArrayList<>();
            for (int i = 0; i < oldGeneration.population.size(); i++) {
                Entity<LayerChromosome> oldLayer = oldGeneration.population.get(i);
                Entity<LayerChromosome> newLayer = newGeneration.population.get(i);

                List<LineChromosome> nOldChromosomes = new ArrayList<>();
                List<LineChromosome> nNewChromosomes = new ArrayList<>();
                List<LineChromosome> choosableChromosomes = new ArrayList<>(newLayer.getGenoType().lineChromosomes);
                for (int j = 0; j < oldGeneration.population.get(i).getGenoType().lineChromosomes.size(); j++) {
                    LineChromosome oldLineChromosome = oldGeneration.population.get(i).getGenoType().lineChromosomes.get(j);

                    int chosenIndex = 0;
                    float closest = new Vector3f(oldLineChromosome.position).sub(choosableChromosomes.get(chosenIndex).position).length();
                    for (int k = 1; k < choosableChromosomes.size(); k++) {
                        float distance = new Vector3f(oldLineChromosome.position).sub(choosableChromosomes.get(k).position).length();

                        if (distance < closest) {
                            closest = distance;
                            chosenIndex = k;
                        }
                    }
                    LineChromosome newLineChromosome = choosableChromosomes.get(chosenIndex);
                    choosableChromosomes.remove(chosenIndex);

                    nOldChromosomes.add(new LineChromosome(oldLineChromosome.position, oldLineChromosome.rotation, oldLineChromosome.scale, oldLineChromosome.fitness));
                    nNewChromosomes.add(new LineChromosome(newLineChromosome.position, newLineChromosome.rotation, newLineChromosome.scale, newLineChromosome.fitness));
                }

                nOldLayer.add(new Entity<>(new LayerChromosome(nOldChromosomes), c -> oldLayer.getFitness()));
                nNewLayer.add(new Entity<>(new LayerChromosome(nNewChromosomes), c -> newLayer.getFitness()));
            }

            oldGeneration = new Generation<>(nOldLayer);
            newGeneration = new Generation<>(nNewLayer);
        }

        progression += elapsedTime;

        float interpolationProgression = progression / (float) INTERPOLATION_THRESHOLD;
        if (interpolationProgression > 1) {
            interpolationProgression = 1;
        }

        List<Entity<LayerChromosome>> currentPopulation = new ArrayList<>();
        for (int i = 0; i < oldGeneration.population.size(); i++) {
            Entity<LayerChromosome> oldLayer = oldGeneration.population.get(i);
            Entity<LayerChromosome> newLayer = newGeneration.population.get(i);

            List<LineChromosome> oldLineChromosomes = oldLayer.getGenoType().lineChromosomes;
            List<LineChromosome> newLineChromosomes = newLayer.getGenoType().lineChromosomes;

            List<LineChromosome> lineChromosomes = new ArrayList<>();
            for (int j = 0; j < oldLineChromosomes.size(); j++) {
                LineChromosome oldChromosome = oldLineChromosomes.get(j);
                LineChromosome newChromosome = newLineChromosomes.get(j);

                LineChromosome currentChromosome = new LineChromosome(
                        Vector3fUtil.interpolatePosition(oldChromosome.position, newChromosome.position, interpolationProgression),
                        Vector3fUtil.interpolateRotation(oldChromosome.rotation, newChromosome.rotation, interpolationProgression),
                        Vector3fUtil.interpolatePosition(oldChromosome.scale, newChromosome.scale, interpolationProgression),
                        newChromosome.fitness
                );
                lineChromosomes.add(currentChromosome);
            }
            currentPopulation.add(new Entity<>(new LayerChromosome(lineChromosomes), element -> newLayer.getFitness()));
        }

        currentGeneration = new Generation<>(currentPopulation);
        synchronize();
    }

    public void extrapolate() {
        currentGeneration = generations.get(generations.size() - 1);
        synchronize();
    }

    public void synchronize() {
        if (currentGeneration != null) {
            syncModelsWithChromosomes();
        }
    }

    private void syncModelsWithChromosomes() {
        for (int i = 0; i < MODEL_POPULATION_COUNT; i++) {
            LayerChromosome layerChromosome = null;
            List<Entity<LayerChromosome>> population = currentGeneration.population;

            if (i < population.size()) {
                layerChromosome = population.get(i).getGenoType();
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
