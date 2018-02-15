package imagenetic.scene.asset.line;

import imagenetic.common.Bridge;
import imagenetic.common.Config;
import imagenetic.common.algorithm.genetic.entity.Entity;
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

import java.util.ArrayList;
import java.util.List;

public class LineAsset extends WorldAsset<LineAssetArgument> {

    private static final int POPULATION_SIZE = 500;
    private static final int MODEL_POPULATION_COUNT = Config.MAX_POPULATION_COUNT;
    private static final int MODEL_POPULATION_SIZE = 300;
    private static final float STICK_SCALE = 2.5f;

    private final ModelManager modelManager;
    private final ImageManager imageManager;

    private final List[] lineModels = new List[MODEL_POPULATION_COUNT];

    private static List<LayerChromosome> chromosomes = new ArrayList<>();
    private static List<Entity<LayerChromosome>> population = new ArrayList<>();

    private Texture blackTexture, grayTexture;

    private float elapsedTime = 0;

    private int populationCount = Config.DEFAULT_POPULATION_COUNT;
    public boolean paused = true;
    private boolean showAll = false;
    public int speed = Config.DEFAULT_SPEED;
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

        chromosomes.clear();
        setupChromosomePopulation();
        arguments.geneticAlgorithm.initialize();

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
        if (chromosomes.size() == populationCount) {
            return;
        }

        if (chromosomes.size() > populationCount) {
            chromosomes.subList(populationCount, chromosomes.size()).clear();
        } else {
            for (int i = 0; i < populationCount - chromosomes.size(); i++) {
                List<LineChromosome> lineChromosomes = new ArrayList<>();
                for (int j = 0; j < POPULATION_SIZE; j++) {
                    lineChromosomes.add(new LineChromosome(
                            new Vector3f(),
                            new Vector3f(),
                            new Vector3f(STICK_SCALE, STICK_SCALE * 10, STICK_SCALE)
                    ));
                }
                chromosomes.add(new LayerChromosome(lineChromosomes));
            }
        }

        population = arguments.geneticAlgorithm.createSortedPopulation(chromosomes);
    }

    @Override
    public void update(final float delta) {
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

    public void setPopulationCount(int populationCount) {
        this.populationCount = populationCount;
        setupChromosomePopulation();
        synchronizeModelsWithChromosomes();
    }

    public void setShowAll(boolean showAll) {
        this.showAll = showAll;
        synchronizeModelsWithChromosomes();
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
                    LineChromosome chromosome = layerChromosome.lineChromosomes.get(j);

                    lineModel.setPosition(new Vector3f(chromosome.position).mul(viewScale));
                    lineModel.setRotation(new Vector3f(chromosome.rotation));
                    lineModel.setScale(new Vector3f(chromosome.scale).mul(viewScale));

                    if (i == 0) {
                        lineModel.texture = blackTexture;
                        lineModel.visible = true;
                    } else {
                        lineModel.texture = grayTexture;
                        lineModel.visible = showAll;
                    }
                } else {
                    lineModel.visible = false;
                }
            }
        }
    }

    private void evaluateGeneticAlgorithm() {
        chromosomes = arguments.geneticAlgorithm.nextGeneration(population, 1f, 2f);
        population = arguments.geneticAlgorithm.createSortedPopulation(chromosomes);
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
