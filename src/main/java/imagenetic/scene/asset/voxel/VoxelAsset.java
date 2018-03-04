package imagenetic.scene.asset.voxel;

import imagenetic.common.Bridge;
import imagenetic.common.Config;
import imagenetic.common.api.SceneSide;
import imagenetic.gui.common.FasterPressedListener;
import imagenetic.gui.common.PlayPressedListener;
import imagenetic.gui.common.SlowerPressedListener;
import imagenetic.scene.asset.voxel.genetic.AlgorithmParameters;
import imagenetic.scene.asset.voxel.genetic.LineGeneticAlgorithm;
import imagenetic.scene.asset.voxel.manager.LineModelManager;
import piengine.object.asset.domain.WorldAsset;
import piengine.object.asset.manager.AssetManager;
import piengine.object.asset.plan.WorldRenderAssetContext;
import piengine.object.asset.plan.WorldRenderAssetContextBuilder;
import puppeteer.annotation.premade.Component;
import puppeteer.annotation.premade.Wire;

import java.awt.image.BufferedImage;

@Component
public class VoxelAsset extends WorldAsset<VoxelAssetArgument> implements SceneSide, PlayPressedListener, FasterPressedListener, SlowerPressedListener {

    private final LineModelManager lineModelManager;

    private AlgorithmParameters parameters;
    private LineGeneticAlgorithm geneticAlgorithm;

    private float elapsedTime = 0;
    private boolean visualChanged = false;

    private boolean paused = true;
    private int speed = Config.DEF_SPEED;

    @Wire
    public VoxelAsset(final AssetManager assetManager, final LineModelManager lineModelManager) {
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
            reset();
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
                updateLabels();
                progression = 0;
            }

            lineModelManager.synchronize(progression);
        }
    }

    @Override
    public void reset() {
        geneticAlgorithm.initializePopulation();
        lineModelManager.setGenerations(geneticAlgorithm.getGenerations());
        lineModelManager.updateView();
        updateLabels();
    }

    @Override
    public void onPlayPressed() {
        this.paused = !this.paused;
    }

    @Override
    public void onFasterPressed() {
        if (speed < Config.MAX_SPEED) {
            speed++;
            updateLabels();
        }
    }

    @Override
    public void onSlowerPressed() {
        if (speed > Config.MIN_SPEED) {
            speed--;
            updateLabels();
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
    public void setThreshold(final double threshold) {
        this.lineModelManager.setThreshold(threshold);
        this.visualChanged = true;
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
    public boolean isAlgorithmPaused() {
        return paused;
    }

    @Override
    public boolean isInterpolated() {
        return this.lineModelManager.isInterpolated();
    }

    @Override
    public WorldRenderAssetContext getAssetContext() {
        return WorldRenderAssetContextBuilder.create()
                .loadModels(lineModelManager.getLineModels())
                .build();
    }

    private void updateLabels() {
        Bridge.frameSide.updateLabels(geneticAlgorithm.getNumberOfGenerations(), geneticAlgorithm.getAverageFitness(), geneticAlgorithm.getBestFitness(), speed);
    }
}
