package imagenetic.scene.asset.line;

import imagenetic.common.Bridge;
import imagenetic.common.Config;
import imagenetic.common.algorithm.genetic.entity.Entity;
import imagenetic.common.api.SceneSide;
import imagenetic.scene.asset.line.genetic.LineGeneticAlgorithm;
import imagenetic.scene.asset.line.genetic.entity.LayerChromosome;
import imagenetic.scene.asset.line.genetic.entity.LineChromosome;
import org.joml.Vector3f;
import piengine.object.asset.domain.WorldAsset;
import piengine.object.asset.manager.AssetManager;
import piengine.object.asset.plan.WorldRenderAssetContext;
import piengine.object.asset.plan.WorldRenderAssetContextBuilder;
import piengine.object.model.domain.Model;
import piengine.object.model.manager.ModelManager;
import piengine.visual.image.manager.ImageManager;
import piengine.visual.texture.domain.Texture;
import puppeteer.annotation.premade.Wire;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class LineAsset extends WorldAsset<LineAssetArgument> implements SceneSide {

    private static final int MODEL_POPULATION_COUNT = Config.MAX_POPULATION_COUNT;
    private static final int MODEL_POPULATION_SIZE = Config.MAX_POPULATION_SIZE;
    private static final float STICK_SCALE = 2.5f;

    private static List<LayerChromosome> chromosomes = new ArrayList<>();
    private static List<Entity<LayerChromosome>> population = new ArrayList<>();

    private final ModelManager modelManager;
    private final ImageManager imageManager;


    private LineGeneticAlgorithm geneticAlgorithm;
    private final List[] lineModels = new List[MODEL_POPULATION_COUNT];
    private Texture blackTexture, grayTexture;

    private float elapsedTime = 0;

    private boolean populationChanged = false;
    private int populationCount = Config.DEF_POPULATION_COUNT;
    private int populationSize = Config.DEF_POPULATION_SIZE;
    private float lineLength = Config.DEF_ENTITY_LENGTH;
    private float lineThickness = Config.DEF_ENTITY_THICKNESS;
    private boolean imageChanged = false;
    private BufferedImage image;

    private boolean paused = true;
    private boolean showAll = false;
    private float threshold = Config.DEF_ENTITY_THRESHOLD;
    private int speed = Config.DEF_SPEED;
    private float viewScale = 1;

    @Wire
    public LineAsset(final AssetManager assetManager, final ModelManager modelManager, final ImageManager imageManager) {
        super(assetManager);

        this.modelManager = modelManager;
        this.imageManager = imageManager;
    }

    @Override
    public void initialize() {
        blackTexture = imageManager.supply("black");
        grayTexture = imageManager.supply("gray");

        geneticAlgorithm = new LineGeneticAlgorithm(arguments.geneticAlgorithmSize);

        chromosomes.clear();
        setupChromosomePopulation();
        geneticAlgorithm.initialize();

        createModels();
    }

    private void createModels() {
        for (int i = 0; i < MODEL_POPULATION_COUNT; i++) {
            lineModels[i] = new ArrayList();
            for (int j = 0; j < MODEL_POPULATION_SIZE; j++) {
                Model lineModel = modelManager.supply(this, "octahedron", grayTexture, true);
                lineModels[i].add(lineModel);
            }
        }
    }

    private void setupChromosomePopulation() {
        if (chromosomes.size() > populationCount) {
            chromosomes.subList(populationCount, chromosomes.size()).clear();
        } else if (chromosomes.size() < populationCount) {
            while (chromosomes.size() < populationCount) {
                chromosomes.add(new LayerChromosome(new ArrayList<>()));
            }
        }

        for (LayerChromosome layer : chromosomes) {
            setupChromosomeLayer(layer.lineChromosomes);

        }

        population = geneticAlgorithm.createSortedPopulation(chromosomes);
    }

    private void setupChromosomeLayer(final List<LineChromosome> lineChromosomes) {
        if (lineChromosomes.size() > populationSize) {
            lineChromosomes.subList(populationSize, lineChromosomes.size()).clear();
        } else if (lineChromosomes.size() < populationSize) {
            while (lineChromosomes.size() < populationSize) {
                lineChromosomes.add(new LineChromosome(
                        new Vector3f(),
                        new Vector3f(),
                        new Vector3f()
                ));
            }
        }

        for (LineChromosome lineChromosome : lineChromosomes) {
            lineChromosome.scale.set(
                    lineThickness * STICK_SCALE / 10f,
                    lineLength * STICK_SCALE,
                    lineThickness * STICK_SCALE / 10f);
        }
    }

    @Override
    public void update(final float delta) {
        if (imageChanged) {
            geneticAlgorithm.setImage(image);
            imageChanged = false;
        }

        if (populationChanged) {
            setupChromosomePopulation();
            synchronizeModelsWithChromosomes();
            populationChanged = false;
        }

        if (!paused) {
            if (elapsedTime > 1) {
                elapsedTime = 0;
                evaluateGeneticAlgorithm();
                synchronizeModelsWithChromosomes();
                Bridge.frameSide.updateLabels();
            } else {
                elapsedTime += delta * speed;
            }
        }
    }

    public void setViewScale(final float viewScale) {
        this.viewScale = viewScale;
        synchronizeModelsWithChromosomes();
    }

    @Override
    public void setPopulationCount(final int populationCount) {
        this.populationCount = populationCount;
        this.populationChanged = true;
    }

    @Override
    public void setPopulationSize(final int populationSize) {
        this.populationSize = populationSize;
        this.populationChanged = true;
    }

    @Override
    public void showAll(final boolean showAll) {
        this.showAll = showAll;
        synchronizeModelsWithChromosomes();
    }

    @Override
    public void setImage(final BufferedImage image) {
        this.image = image;
        this.imageChanged = true;
    }

    @Override
    public int getNumberOfGenerations() {
        return geneticAlgorithm.getNumberOfGenerations();
    }

    @Override
    public float getAverageFitness() {
        return geneticAlgorithm.getAverageFitness();
    }

    @Override
    public float getBestFitness() {
        return geneticAlgorithm.getBestFitness();
    }

    @Override
    public boolean isAlgorithmPaused() {
        return paused;
    }

    @Override
    public void setAlgorithmStatus(final boolean paused) {
        this.paused = paused;
    }

    @Override
    public void setAlgorithmSpeed(final int speed) {
        this.speed = speed;
    }

    @Override
    public void setThreshold(final float threshold) {
        this.threshold = threshold;
        synchronizeModelsWithChromosomes();
    }

    @Override
    public void setThickness(final float thickness) {
        this.lineThickness = thickness;
        this.populationChanged = true;
    }

    @Override
    public void setLength(final float length) {
        this.lineLength = length;
        this.populationChanged = true;
    }

    private void synchronizeModelsWithChromosomes() {
        for (int i = 0; i < MODEL_POPULATION_COUNT; i++) {
            LayerChromosome layerChromosome = null;
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

    private void evaluateGeneticAlgorithm() {
        chromosomes = geneticAlgorithm.nextGeneration(population, 1f, 2f);
        population = geneticAlgorithm.createSortedPopulation(chromosomes);
    }

    @Override
    public WorldRenderAssetContext getAssetContext() {
        return WorldRenderAssetContextBuilder.create()
                .loadModels(getLineModels())
                .build();
    }

    private Model[] getLineModels() {
        Model[] models = new Model[MODEL_POPULATION_COUNT * MODEL_POPULATION_SIZE];
        for (int i = 0; i < MODEL_POPULATION_COUNT; i++) {
            for (int j = 0; j < MODEL_POPULATION_SIZE; j++) {
                models[i * MODEL_POPULATION_SIZE + j] = (Model) lineModels[i].get(j);
            }
        }

        return models;
    }
}
