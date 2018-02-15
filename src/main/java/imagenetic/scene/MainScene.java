package imagenetic.scene;

import imagenetic.common.Bridge;
import imagenetic.common.api.SceneSide;
import imagenetic.scene.asset.camera.ObserverCameraAsset;
import imagenetic.scene.asset.line.LineAsset;
import imagenetic.scene.asset.line.LineAssetArgument;
import imagenetic.scene.asset.line.genetic.LineGeneticAlgorithm;
import org.joml.Vector2i;
import piengine.core.architecture.scene.domain.Scene;
import piengine.core.input.manager.InputManager;
import piengine.core.utils.ColorUtils;
import piengine.object.asset.manager.AssetManager;
import piengine.object.asset.plan.GuiRenderAssetContextBuilder;
import piengine.object.camera.asset.CameraAssetArgument;
import piengine.object.camera.domain.Camera;
import piengine.object.camera.domain.CameraAttribute;
import piengine.object.camera.domain.ThirdPersonCamera;
import piengine.object.canvas.domain.Canvas;
import piengine.object.canvas.manager.CanvasManager;
import piengine.visual.display.manager.DisplayManager;
import piengine.visual.framebuffer.domain.Framebuffer;
import piengine.visual.framebuffer.manager.FramebufferManager;
import piengine.visual.render.domain.plan.GuiRenderPlanBuilder;
import piengine.visual.render.domain.plan.RenderPlan;
import piengine.visual.render.domain.plan.WorldRenderPlanBuilder;
import piengine.visual.render.manager.RenderManager;
import puppeteer.annotation.premade.Wire;

import java.awt.image.BufferedImage;

import static piengine.core.base.type.property.ApplicationProperties.get;
import static piengine.core.base.type.property.PropertyKeys.CAMERA_FOV;
import static piengine.core.base.type.property.PropertyKeys.CAMERA_LOOK_DOWN_LIMIT;
import static piengine.core.base.type.property.PropertyKeys.CAMERA_LOOK_SPEED;
import static piengine.core.base.type.property.PropertyKeys.CAMERA_LOOK_UP_LIMIT;
import static piengine.core.base.type.property.PropertyKeys.CAMERA_MOVE_SPEED;
import static piengine.core.input.domain.Key.KEY_ESCAPE;
import static piengine.core.input.domain.KeyEventType.PRESS;
import static piengine.object.camera.domain.ProjectionType.ORTHOGRAPHIC;
import static piengine.visual.framebuffer.domain.FramebufferAttachment.COLOR_BUFFER_MULTISAMPLE_ATTACHMENT;
import static piengine.visual.framebuffer.domain.FramebufferAttachment.DEPTH_BUFFER_MULTISAMPLE_ATTACHMENT;
import static piengine.visual.postprocessing.domain.EffectType.ANTIALIAS_EFFECT;

public class MainScene extends Scene implements SceneSide {

    private static final Vector2i VIEWPORT = new Vector2i();
    private static final Vector2i STICK_SIZE = new Vector2i();

    private static final float MIN_SCALE = 0.1f;
    private static final float MAX_SCALE = 3.0f;

    private final InputManager inputManager;
    private final DisplayManager displayManager;
    private final FramebufferManager framebufferManager;
    private final CanvasManager canvasManager;

    // Main
    private Framebuffer mainFbo;
    private Canvas mainCanvas;
    // Stick
    private LineGeneticAlgorithm geneticAlgorithm;
    private LineAsset lineAsset;
    private ObserverCameraAsset cameraAsset;
    private Camera camera;
    private float viewScale = 1f;

    @Wire
    public MainScene(final RenderManager renderManager, final AssetManager assetManager,
                     final InputManager inputManager, final DisplayManager displayManager,
                     final FramebufferManager framebufferManager, final CanvasManager canvasManager) {
        super(renderManager, assetManager);

        this.inputManager = inputManager;
        this.displayManager = displayManager;
        this.framebufferManager = framebufferManager;
        this.canvasManager = canvasManager;

        Bridge.sceneSide = this;
    }

    @Override
    public void initialize() {
        recalculateViewport();
        super.initialize();
        inputManager.addKeyEvent(KEY_ESCAPE, PRESS, displayManager::closeDisplay);
        inputManager.addScrollEvent(scroll -> {
            viewScale += scroll.y * 0.1f;
            if (viewScale > MAX_SCALE) {
                viewScale = MAX_SCALE;
            } else if (viewScale < MIN_SCALE) {
                viewScale = MIN_SCALE;
            }

            lineAsset.setViewScale(viewScale);
        });
    }

    @Override
    public void resize(final int oldWidth, final int oldHeight, final int width, final int height) {
        recalculateViewport();
        camera.recalculateProjection();
    }

    @Override
    protected void createAssets() {
        // Main
        mainFbo = framebufferManager.supply(VIEWPORT, false, COLOR_BUFFER_MULTISAMPLE_ATTACHMENT, DEPTH_BUFFER_MULTISAMPLE_ATTACHMENT);
        mainCanvas = canvasManager.supply(this, mainFbo, ANTIALIAS_EFFECT);

        // Stick
        geneticAlgorithm = new LineGeneticAlgorithm(STICK_SIZE.x);

        cameraAsset = createAsset(ObserverCameraAsset.class, new CameraAssetArgument(
                null,
                get(CAMERA_LOOK_UP_LIMIT),
                get(CAMERA_LOOK_DOWN_LIMIT),
                get(CAMERA_LOOK_SPEED),
                get(CAMERA_MOVE_SPEED)));
        cameraAsset.movingEnabled = false;
        cameraAsset.lookingEnabled = false;

        camera = new ThirdPersonCamera(cameraAsset, VIEWPORT, new CameraAttribute(get(CAMERA_FOV), 0.1f, 2000f), 1000f, ORTHOGRAPHIC);

        lineAsset = createAsset(LineAsset.class, new LineAssetArgument(this, geneticAlgorithm));
    }

    @Override
    protected RenderPlan createRenderPlan() {
        return GuiRenderPlanBuilder
                .createPlan(VIEWPORT)
                .bindFrameBuffer(
                        mainFbo,
                        WorldRenderPlanBuilder
                                .createPlan(camera)
                                .loadAssets(lineAsset)
                                .clearScreen(ColorUtils.WHITE)
                                .render()
                )
                .loadAssetContext(GuiRenderAssetContextBuilder
                        .create()
                        .loadCanvases(mainCanvas)
                        .build()
                )
                .clearScreen(ColorUtils.BLACK)
                .render();
    }

    @Override
    public void update(final float delta) {
    }

    private void recalculateViewport() {
        VIEWPORT.set(displayManager.getViewport());
        STICK_SIZE.set(VIEWPORT.x < VIEWPORT.y ? VIEWPORT.x : VIEWPORT.y);
    }

    @Override
    public boolean isAlgorithmPaused() {
        return lineAsset.paused;
    }

    @Override
    public void setAlgorithmStatus(boolean paused) {
        lineAsset.paused = paused;
    }

    @Override
    public void setAlgorithmSpeed(int speed) {
        lineAsset.speed = speed;
    }

    @Override
    public void setPopulationCount(int populationCount) {
        lineAsset.setPopulationCount(populationCount);
    }

    @Override
    public void showAll(final boolean show) {
        lineAsset.setShowAll(show);
    }

    @Override
    public void setImage(final BufferedImage image) {
        geneticAlgorithm.setImage(image);
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
}
