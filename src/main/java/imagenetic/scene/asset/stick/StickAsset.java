package imagenetic.scene.asset.stick;

import imagenetic.common.algorithm.genetic.entity.Entity;
import imagenetic.scene.asset.stick.genetic.StickGeneticAlgorithm;
import imagenetic.scene.asset.stick.genetic.entity.StickChromosome;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.lwjgl.glfw.GLFW;
import piengine.core.input.domain.KeyEventType;
import piengine.core.input.manager.InputManager;
import piengine.object.asset.domain.Asset;
import piengine.object.model.domain.Model;
import piengine.object.model.manager.ModelManager;
import piengine.visual.render.domain.AssetPlan;
import piengine.visual.render.manager.RenderManager;
import puppeteer.annotation.premade.Wire;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import static piengine.visual.render.domain.AssetPlan.createPlan;

public class StickAsset extends Asset {

    private static final int MAX_SIZE = 800;
    private static final int HALF_MAX_SIZE = MAX_SIZE / 2;

    private final ModelManager modelManager;
    private final InputManager inputManager;

    private final Random random = new Random();
    private final List<Model> sticks = new ArrayList<>();
    private StickGeneticAlgorithm geneticAlgorithm;
    private List<StickChromosome> chromosomes;

    @Wire
    public StickAsset(RenderManager renderManager, ModelManager modelManager, InputManager inputManager) {
        super(renderManager);
        this.modelManager = modelManager;
        this.inputManager = inputManager;
    }

    @Override
    public void initialize() {
        createLotOfSticks();

        String imagePath = getClass().getResource("/images/java.png").getFile();
        geneticAlgorithm = new StickGeneticAlgorithm(imagePath, MAX_SIZE);
        chromosomes = convertToChromosomes(sticks);

        inputManager.addEvent(GLFW.GLFW_KEY_SPACE, KeyEventType.PRESS, this::evaluateGeneticAlgorithm);
    }

    private void createStick() {
        Model stick = modelManager.supply("octahedron", this);

        stick.setPosition(0, 0, 0);
        stick.setRotation(0, 0, 45);
        stick.setScale(2, 60, 2);

        sticks.add(stick);
    }

    private void createLotOfSticks() {
        for (int i = 0; i < 5_000; i++) {
            Model stick = modelManager.supply("octahedron", this);

            stick.setPosition(
                    random.nextFloat() * MAX_SIZE - HALF_MAX_SIZE,
                    random.nextFloat() * MAX_SIZE - HALF_MAX_SIZE,
                    random.nextFloat() * MAX_SIZE - HALF_MAX_SIZE);
            stick.setRotation(
                    random.nextFloat() * 360 - 180,
                    random.nextFloat() * 360 - 180,
                    random.nextFloat() * 360 - 180);
            stick.setScale(2, 60, 2);

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
    public void update(double delta) {
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
    protected AssetPlan createRenderPlan() {
        return createPlan()
                .withModels(sticks)
                .withColor(new Vector4f(0, 0, 0, 1));
    }

}
