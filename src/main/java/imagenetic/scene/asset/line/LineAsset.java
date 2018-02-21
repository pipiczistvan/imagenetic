package imagenetic.scene.asset.line;

import imagenetic.common.Bridge;
import imagenetic.common.Config;
import imagenetic.common.api.SceneSide;
import imagenetic.scene.asset.line.genetic.AlgorithmParameters;
import imagenetic.scene.asset.line.genetic.LineGeneticAlgorithm;
import imagenetic.scene.asset.line.manager.LineModelManager;
import piengine.object.asset.domain.WorldAsset;
import piengine.object.asset.manager.AssetManager;
import piengine.object.asset.plan.WorldRenderAssetContext;
import piengine.object.asset.plan.WorldRenderAssetContextBuilder;
import puppeteer.annotation.premade.Wire;

import java.awt.image.BufferedImage;

public class LineAsset extends WorldAsset<LineAssetArgument> implements SceneSide {

    private final LineModelManager lineModelManager;

    private AlgorithmParameters parameters;
    private LineGeneticAlgorithm geneticAlgorithm;

    private float elapsedTime = 0;
    private boolean visualChanged = false;

    private boolean paused = true;
    private int speed = Config.DEF_SPEED;

    @Wire
    public LineAsset(final AssetManager assetManager, final LineModelManager lineModelManager) {
        super(assetManager);

        this.lineModelManager = lineModelManager;
    }

    @Override
    public void initialize() {
        parameters = new AlgorithmParameters(
                arguments.geneticAlgorithmSize,
                Config.DEF_POPULATION_COUNT, Config.DEF_POPULATION_SIZE,
                Config.DEF_ENTITY_LENGTH, Config.DEF_ENTITY_THICKNESS
        );
        geneticAlgorithm = new LineGeneticAlgorithm(parameters);

        lineModelManager.initialize(this);
    }

    @Override
    public void update(final float delta) {
        if (parameters.hasImageChanged()) {
            geneticAlgorithm.initializeFitnessFunction();
        }
        if (parameters.hasChanged()) {
            geneticAlgorithm.initializePopulation();
            lineModelManager.setGenerations(geneticAlgorithm.getGenerations());
            lineModelManager.updateView();
        }
        if (visualChanged) {
            visualChanged = false;
            lineModelManager.updateView();
        }

        if (!paused) {
            float progression = delta * speed;
            elapsedTime += progression;

            if (elapsedTime >= 1) {
                elapsedTime = 0;
                geneticAlgorithm.nextGeneration(1f, 2f);
                Bridge.frameSide.updateLabels();
                progression = 0;
            }

            lineModelManager.synchronize(progression);
        }
    }

    // VISUAL

    public void setViewScale(final float viewScale) {
        this.lineModelManager.setViewScale(viewScale);
        this.visualChanged = true;
    }

    @Override
    public void showAll(final boolean showAll) {
        this.lineModelManager.setShowAll(showAll);
        this.visualChanged = true;
    }

    @Override
    public void setThreshold(final float threshold) {
        this.lineModelManager.setThreshold(threshold);
        this.visualChanged = true;
    }

    @Override
    public void setAlgorithmSpeed(final int speed) {
        this.speed = speed;
    }

    @Override
    public void setAlgorithmStatus(final boolean paused) {
        this.paused = paused;
    }

    @Override
    public void setInterpolated(final boolean interpolated) {
        this.lineModelManager.setInterpolated(interpolated);
    }

    // FUNDAMENTAL

    @Override
    public void setThickness(final float thickness) {
        this.parameters.setLineThickness(thickness);
    }

    @Override
    public void setLength(final float length) {
        this.parameters.setLineLength(length);
    }

    @Override
    public void setPopulationCount(final int populationCount) {
        this.parameters.setPopulationCount(populationCount);
    }

    @Override
    public void setPopulationSize(final int populationSize) {
        this.parameters.setPopulationSize(populationSize);
    }

    @Override
    public void setImage(final BufferedImage image) {
        this.parameters.setImage(image);
    }

    // GETTERS

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
    public WorldRenderAssetContext getAssetContext() {
        return WorldRenderAssetContextBuilder.create()
                .loadModels(lineModelManager.getLineModels())
                .build();
    }
}
