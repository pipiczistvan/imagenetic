package imagenetic.scene.asset.stick;

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

    private static final int POPULATION_COUNT = 3;
    private static final int POPULATION_SIZE = 750;
    private static final int VISIBLE_POPULATION_COUNT = 1;
    private static final int VISIBLE_POPULATION_SIZE = 600;

    private final ModelManager modelManager;
    private final ImageManager imageManager;

    private final List[] stickModels = new List[VISIBLE_POPULATION_COUNT];

    private static List<LayerChromosome> chromosomes = new ArrayList<>();
    private static List<Entity<LayerChromosome>> population = new ArrayList<>();

    private Texture black, red;

    private float elapsedTime = 0;

    @Wire
    public StickAsset(final AssetManager assetManager, final ModelManager modelManager, final ImageManager imageManager) {
        super(assetManager);

        this.modelManager = modelManager;
        this.imageManager = imageManager;
    }

    @Override
    public void initialize() {
        black = imageManager.supply("black");
        red = imageManager.supply("red");

        if (chromosomes.isEmpty()) {
            createLotOfSticks();
        }
        createLotOfStickModels();
    }

    private void createLotOfStickModels() {
        for (int i = 0; i < VISIBLE_POPULATION_COUNT; i++) {
            stickModels[i] = new ArrayList();
            for (int j = 0; j < VISIBLE_POPULATION_SIZE; j++) {
                Model stick = modelManager.supply(this, "octahedron", black, true);
                stickModels[i].add(stick);
            }
        }
    }

    private void createLotOfSticks() {
        for (int i = 0; i < POPULATION_COUNT; i++) {
            List<StickChromosome> stickChromosomes = new ArrayList<>();
            for (int j = 0; j < POPULATION_SIZE; j++) {
                stickChromosomes.add(new StickChromosome(
                        new Vector3f(),
                        new Vector3f(),
                        new Vector3f(3, 25, 3)
                ));
            }
            chromosomes.add(new LayerChromosome(stickChromosomes));
        }

        population = arguments.geneticAlgorithm.createSortedPopulation(chromosomes);
    }

    @Override
    public void update(final float delta) {
        if (elapsedTime > 0.001) {
            elapsedTime = 0;
            evaluateGeneticAlgorithm();
        } else {
            elapsedTime += delta;
        }
    }

    private void evaluateGeneticAlgorithm() {
        chromosomes = arguments.geneticAlgorithm.nextGeneration(population, 1f, 2f);
        population = arguments.geneticAlgorithm.createSortedPopulation(chromosomes);

        for (int i = 0; i < VISIBLE_POPULATION_COUNT; i++) {
            LayerChromosome layerChromosome = population.get(i).getGenoType();
            for (int j = 0; j < VISIBLE_POPULATION_SIZE; j++) {
                Model stick = (Model) stickModels[i].get(j);
                StickChromosome chromosome = layerChromosome.stickChromosomes.get(j);

                stick.setPosition(new Vector3f(chromosome.position).mul(arguments.viewScale));
                stick.setRotation(new Vector3f(chromosome.rotation));
                stick.setScale(new Vector3f(chromosome.scale).mul(arguments.viewScale));

//                if (i == 0) {
//                    stick.texture = red;
//                } else {
//                    stick.texture = black;
//                }
            }
        }
    }

    @Override
    public WorldRenderAssetContext getAssetContext() {
        return WorldRenderAssetContextBuilder.create()
                .loadModels(getStickModels())
                .build();
    }

    private Model[] getStickModels() {
        Model[] models = new Model[VISIBLE_POPULATION_COUNT * VISIBLE_POPULATION_SIZE];
        for (int i = 0; i < VISIBLE_POPULATION_COUNT; i++) {
            for (int j = 0; j < VISIBLE_POPULATION_SIZE; j++) {
                models[i * VISIBLE_POPULATION_SIZE + j] = (Model) stickModels[i].get(j);
            }
        }

        return models;
    }
}
