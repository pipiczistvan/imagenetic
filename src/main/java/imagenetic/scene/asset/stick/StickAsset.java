package imagenetic.scene.asset.stick;

import imagenetic.common.Bridge;
import imagenetic.common.Config;
import imagenetic.common.algorithm.genetic.entity.Entity;
import imagenetic.scene.asset.stick.genetic.entity.LayerChromosome;
import imagenetic.scene.asset.stick.genetic.entity.StickChromosome;
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

public class StickAsset extends WorldAsset<StickAssetArgument> {

    private static final int POPULATION_SIZE = 500;
    private static final int MODEL_POPULATION_COUNT = Config.MAX_POPULATION_COUNT;
    private static final int MODEL_POPULATION_SIZE = 300;
    private static final float STICK_SCALE = 2.5f;

    private final ModelManager modelManager;
    private final ImageManager imageManager;

    private final List[] stickModels = new List[MODEL_POPULATION_COUNT];

    private static List<LayerChromosome> chromosomes = new ArrayList<>();
    private static List<Entity<LayerChromosome>> population = new ArrayList<>();

    private Texture blackTexture, grayTexture;

    private float elapsedTime = 0;

    private int populationCount = Config.MIN_POPULATION_COUNT;
    public boolean paused = true;
    private boolean showAll = false;
    public int speed = Config.MIN_SPEED;
    private float viewScale = 1;

    @Wire
    public StickAsset(final AssetManager assetManager, final ModelManager modelManager, final ImageManager imageManager) {
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

        createStickModels();
    }

    private void createStickModels() {
        for (int i = 0; i < MODEL_POPULATION_COUNT; i++) {
            stickModels[i] = new ArrayList();
            for (int j = 0; j < MODEL_POPULATION_SIZE; j++) {
                Model stick = modelManager.supply(this, "octahedron", grayTexture, true);
                stickModels[i].add(stick);
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
                List<StickChromosome> stickChromosomes = new ArrayList<>();
                for (int j = 0; j < POPULATION_SIZE; j++) {
                    stickChromosomes.add(new StickChromosome(
                            new Vector3f(),
                            new Vector3f(),
                            new Vector3f(STICK_SCALE, STICK_SCALE * 10, STICK_SCALE)
                    ));
                }
                chromosomes.add(new LayerChromosome(stickChromosomes));
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
                updateModels();
                Bridge.frameSide.updateLabels();
            } else {
                elapsedTime += delta * speed;
            }
        }
    }

    public void setViewScale(final float viewScale) {
        this.viewScale = viewScale;
        updateModels();
    }

    public void setPopulationCount(int populationCount) {
        this.populationCount = populationCount;
        setupChromosomePopulation();
        updateModels();
    }

    public void setShowAll(boolean showAll) {
        this.showAll = showAll;
        updateModels();
    }

    private void updateModels() {
        for (int i = 0; i < MODEL_POPULATION_COUNT; i++) {
            LayerChromosome layerChromosome = null;
            if (i < population.size()) {
                layerChromosome = population.get(i).getGenoType();
            }
            for (int j = 0; j < MODEL_POPULATION_SIZE; j++) {
                Model stick = (Model) stickModels[i].get(j);
                if (layerChromosome != null) {
                    StickChromosome chromosome = layerChromosome.stickChromosomes.get(j);

                    stick.setPosition(new Vector3f(chromosome.position).mul(viewScale));
                    stick.setRotation(new Vector3f(chromosome.rotation));
                    stick.setScale(new Vector3f(chromosome.scale).mul(viewScale));

                    if (i == 0) {
                        stick.texture = blackTexture;
                        stick.visible = true;
                    } else {
                        stick.texture = grayTexture;
                        stick.visible = showAll;
                    }
                } else {
                    stick.visible = false;
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
                .loadModels(getStickModels())
                .build();
    }

    private Model[] getStickModels() {
        Model[] models = new Model[MODEL_POPULATION_COUNT * MODEL_POPULATION_SIZE];
        for (int i = 0; i < MODEL_POPULATION_COUNT; i++) {
            for (int j = 0; j < MODEL_POPULATION_SIZE; j++) {
                models[i * MODEL_POPULATION_SIZE + j] = (Model) stickModels[i].get(j);
            }
        }

        return models;
    }
}
