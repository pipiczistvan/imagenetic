package imagenetic.scene.asset.voxel;

import imagenetic.common.Bridge;
import imagenetic.common.Config;
import imagenetic.gui.common.api.ViewChangedListener;
import imagenetic.gui.common.api.buttons.FasterPressedListener;
import imagenetic.gui.common.api.buttons.PlayPressedListener;
import imagenetic.gui.common.api.buttons.ResetPressedListener;
import imagenetic.gui.common.api.buttons.SlowerPressedListener;
import imagenetic.scene.asset.voxel.genetic.VoxelGeneticAlgorithm;
import imagenetic.scene.asset.voxel.manager.LineModelManager;
import piengine.core.input.manager.InputManager;
import piengine.object.asset.domain.WorldAsset;
import piengine.object.asset.manager.AssetManager;
import piengine.object.asset.plan.WorldRenderAssetContext;
import piengine.object.asset.plan.WorldRenderAssetContextBuilder;
import puppeteer.annotation.premade.Component;
import puppeteer.annotation.premade.Wire;

import static imagenetic.common.Config.MAX_SCALE;
import static imagenetic.common.Config.MIN_SCALE;
import static imagenetic.scene.asset.voxel.genetic.VoxelGeneticAlgorithm.PARAMETERS;

@Component
public class VoxelAsset extends WorldAsset<VoxelAssetArgument> implements PlayPressedListener, ResetPressedListener, FasterPressedListener, SlowerPressedListener, ViewChangedListener {

    private final LineModelManager lineModelManager;
    private final InputManager inputManager;
    private final VoxelGeneticAlgorithm geneticAlgorithm;

    private float elapsedTime = 0;
    private boolean visualChanged = false;
    private boolean paused = true;
    private int speed = Config.DEF_SPEED;
    private float viewScale = 1f;

    @Wire
    public VoxelAsset(final AssetManager assetManager, final LineModelManager lineModelManager, final InputManager inputManager, final VoxelGeneticAlgorithm voxelGeneticAlgorithm) {
        super(assetManager);

        this.lineModelManager = lineModelManager;
        this.inputManager = inputManager;
        this.geneticAlgorithm = voxelGeneticAlgorithm;
    }

    @Override
    public void initialize() {
        inputManager.addScrollEvent(scroll -> {
            viewScale += scroll.y * 0.1f;
            if (viewScale > MAX_SCALE) {
                viewScale = MAX_SCALE;
            } else if (viewScale < MIN_SCALE) {
                viewScale = MIN_SCALE;
            }

            setViewScale(viewScale);
        });
        lineModelManager.initialize(this);
    }

    @Override
    public void update(final float delta) {
        if (PARAMETERS.hasChanged()) {
            reset();
        }
        if (visualChanged) {
            visualChanged = false;
            lineModelManager.updateView();
        }

        if (!paused && !geneticAlgorithm.isDone()) {
            float progression = delta * speed;
            elapsedTime += progression;

            if (elapsedTime >= 1) {
                elapsedTime = 0;
                geneticAlgorithm.nextGeneration();
                updateLabels();
            }

            lineModelManager.synchronize(progression);
        }
    }

    private void reset() {
        geneticAlgorithm.initialize();
        lineModelManager.setGenerations(geneticAlgorithm.getGenerations());
        lineModelManager.updateView();
        updateLabels();
    }

    @Override
    public void viewChanged(final VIEW_TYPE view, final boolean zoomReset) {
        if (zoomReset) {
            setViewScale(1.0f);
        }
        arguments.cameraAsset.resetRotation(view);
    }

    @Override
    public void onPlayPressed() {
        this.paused = !this.paused;
    }

    @Override
    public void onResetPressed() {
        this.paused = true;
        reset();
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

    private void setViewScale(final float viewScale) {
        this.viewScale = viewScale;
        this.lineModelManager.setViewScale(viewScale);
        this.visualChanged = true;
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
