package imagenetic.scene.asset.stick;

import imagenetic.common.algorithm.genetic.entity.Entity;
import imagenetic.scene.asset.stick.genetic.StickGeneticAlgorithm;
import imagenetic.scene.asset.stick.genetic.entity.StickChromosome;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;
import piengine.core.base.resource.ResourceLoader;
import piengine.core.input.domain.KeyEventType;
import piengine.core.input.manager.InputManager;
import piengine.object.asset.domain.WorldAsset;
import piengine.object.asset.manager.AssetManager;
import piengine.object.asset.plan.WorldRenderAssetContext;
import piengine.object.asset.plan.WorldRenderAssetContextBuilder;
import piengine.object.model.domain.Model;
import piengine.object.model.manager.ModelManager;
import puppeteer.annotation.premade.Wire;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class StickAsset extends WorldAsset<StickAssetArgument> {

    private final ModelManager modelManager;
    private final InputManager inputManager;
    private final ResourceLoader imageLoader;

    private final Random random = new Random();
    private final List<Model> sticks = new ArrayList<>();
    private StickGeneticAlgorithm geneticAlgorithm;
    private List<StickChromosome> chromosomes;

    @Wire
    public StickAsset(final AssetManager assetManager, final ModelManager modelManager, final InputManager inputManager) {
        super(assetManager);

        this.modelManager = modelManager;
        this.inputManager = inputManager;
        this.imageLoader = new ResourceLoader("/images", "png");
    }

    @Override
    public void initialize() {
        createLotOfSticks();

        BufferedImage bufferedImage = null;
        try {
            bufferedImage = imageLoader.loadBufferedImage("eiffel");
        } catch (IOException e) {
            e.printStackTrace();
        }
        geneticAlgorithm = new StickGeneticAlgorithm(bufferedImage, arguments.size);
        chromosomes = convertToChromosomes(sticks);

        inputManager.addEvent(GLFW.GLFW_KEY_SPACE, KeyEventType.PRESS, this::evaluateGeneticAlgorithm);
    }

    private void createStick() {
        Model stick = modelManager.supply(this, "octahedron", "black", false);

        stick.setPosition(0, 0, 0);
        stick.setRotation(0, 0, 45);
        stick.setScale(2, 60, 2);

        sticks.add(stick);
    }

    private void createLotOfSticks() {
        for (int i = 0; i < 5_000; i++) {
            Model stick = modelManager.supply(this, "octahedron", "black", false);

            stick.setPosition(
                    random.nextFloat() * arguments.size - arguments.halfSize,
                    random.nextFloat() * arguments.size - arguments.halfSize,
                    random.nextFloat() * arguments.size - arguments.halfSize);
            stick.setRotation(
                    random.nextFloat() * 360 - 180,
                    random.nextFloat() * 360 - 180,
                    random.nextFloat() * 360 - 180);
            stick.setScale(2, 20, 2);

            sticks.add(stick);
        }
    }

    private List<StickChromosome> convertToChromosomes(List<Model> models) {
        return models.stream()
                .map(m -> new StickChromosome(
                        new Vector3f(m.getPosition()),
                        new Vector3f(m.getRotation()),
                        new Vector3f(m.getScale())))
                .collect(Collectors.toList());
    }

    @Override
    public void update(final float delta) {
//        evaluateGeneticAlgorithm();
    }

    private void evaluateGeneticAlgorithm() {
        List<Entity<StickChromosome>> population = geneticAlgorithm.createSortedPopulation(chromosomes);
        chromosomes = geneticAlgorithm.nextGeneration(population, 1f - 1f / population.size(), 0.25f);

        for (int i = 0; i < sticks.size(); i++) {
            Model stick = sticks.get(i);
            StickChromosome chromosome = chromosomes.get(i);

            stick.setPosition(chromosome.position.x, chromosome.position.y, chromosome.position.z);
            stick.setRotation(chromosome.rotation.x, chromosome.rotation.y, chromosome.rotation.z);
            stick.setScale(chromosome.scale.x, chromosome.scale.y, chromosome.scale.z);
        }
    }

    @Override
    public WorldRenderAssetContext getAssetContext() {
        return WorldRenderAssetContextBuilder.create()
                .loadModels(sticks.toArray(new Model[sticks.size()]))
                .build();
    }
}
