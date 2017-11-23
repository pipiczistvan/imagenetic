package imagenetic.asset.stick;

import org.joml.Vector3f;
import org.joml.Vector4f;
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

    private static final int MAX_SIZE = 200;

    private final ModelManager modelManager;

    private final Random random = new Random();
    private final List<Model> sticks = new ArrayList<>();
    private StickGeneticAlgorithm geneticAlgorithm;
    private List<StickChromosome> chromosomes;
    private List<StickChromosome> generatedChromosomes;

    @Wire
    public StickAsset(RenderManager renderManager, ModelManager modelManager) {
        super(renderManager);
        this.modelManager = modelManager;
    }

    @Override
    public void initialize() {
        createLotOfSticks();

        String eiffelPath = getClass().getResource("/images/eiffel.png").getFile();
        geneticAlgorithm = new StickGeneticAlgorithm(eiffelPath, MAX_SIZE);
        chromosomes = convertToChromosomes(sticks);

        generatedChromosomes = geneticAlgorithm.execute(chromosomes, 0f, 0.8f);
        for (int i = 0; i < sticks.size(); i++) {
            Model stick = sticks.get(i);
            StickChromosome chromosome = generatedChromosomes.get(0);

            stick.setPosition(chromosome.position.x, chromosome.position.y, chromosome.position.z);
            stick.setRotation(chromosome.rotation.x, chromosome.rotation.y, chromosome.rotation.z);
            stick.setScale(chromosome.scale.x, chromosome.scale.y, chromosome.scale.z);
        }
    }

    private void createStick() {
        Model stick = modelManager.supply("octahedron", this);

        stick.setPosition(0, 0, 0);
        stick.setRotation(0, 0, 45);
        stick.setScale(2, 60, 2);

        sticks.add(stick);
    }

    private void createLotOfSticks() {
        for (int i = 0; i < 100; i++) {
            Model stick = modelManager.supply("octahedron", this);

            stick.setPosition(
                    random.nextFloat() * MAX_SIZE - MAX_SIZE / 2,
                    random.nextFloat() * MAX_SIZE - MAX_SIZE / 2,
                    random.nextFloat() * MAX_SIZE - MAX_SIZE / 2);
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
    }

    @Override
    protected AssetPlan createRenderPlan() {
        return createPlan()
                .withModels(sticks)
                .withColor(new Vector4f(0, 0, 0, 1));
    }

}
