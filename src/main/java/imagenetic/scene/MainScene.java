package imagenetic.scene;

import imagenetic.scene.asset.camera.ObserverCameraAsset;
import imagenetic.scene.asset.stick.StickAsset;
import imagenetic.scene.asset.stick.StickAssetArgument;
import imagenetic.scene.asset.stick.genetic.StickGeneticAlgorithm;
import imagenetic.scene.asset.ui.UiAsset;
import imagenetic.scene.asset.ui.UiAssetArgument;
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
import piengine.visual.framebuffer.domain.Framebuffer;
import piengine.visual.framebuffer.manager.FramebufferManager;
import piengine.visual.render.domain.plan.GuiRenderPlanBuilder;
import piengine.visual.render.domain.plan.RenderPlan;
import piengine.visual.render.domain.plan.WorldRenderPlanBuilder;
import piengine.visual.render.manager.RenderManager;
import piengine.visual.window.manager.WindowManager;
import puppeteer.annotation.premade.Wire;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;
import static piengine.core.base.type.property.ApplicationProperties.get;
import static piengine.core.base.type.property.PropertyKeys.CAMERA_FOV;
import static piengine.core.base.type.property.PropertyKeys.CAMERA_LOOK_DOWN_LIMIT;
import static piengine.core.base.type.property.PropertyKeys.CAMERA_LOOK_SPEED;
import static piengine.core.base.type.property.PropertyKeys.CAMERA_LOOK_UP_LIMIT;
import static piengine.core.base.type.property.PropertyKeys.CAMERA_MOVE_SPEED;
import static piengine.core.base.type.property.PropertyKeys.WINDOW_HEIGHT;
import static piengine.core.base.type.property.PropertyKeys.WINDOW_WIDTH;
import static piengine.core.input.domain.KeyEventType.PRESS;
import static piengine.object.camera.domain.ProjectionType.ORTHOGRAPHIC;
import static piengine.visual.framebuffer.domain.FramebufferAttachment.COLOR_BUFFER_MULTISAMPLE_ATTACHMENT;
import static piengine.visual.framebuffer.domain.FramebufferAttachment.DEPTH_BUFFER_MULTISAMPLE_ATTACHMENT;
import static piengine.visual.postprocessing.domain.EffectType.ANTIALIAS_EFFECT;

public class MainScene extends Scene {

    private static final Vector2i VIEWPORT = new Vector2i(get(WINDOW_WIDTH), get(WINDOW_HEIGHT));
    public static final Vector2i STICK_SIZE = new Vector2i(VIEWPORT.x < VIEWPORT.y ? VIEWPORT.x : VIEWPORT.y);
    private static final StickGeneticAlgorithm GENETIC_ALGORITHM = new StickGeneticAlgorithm(STICK_SIZE.x);

    private static final float MIN_SCALE = 0.1f;
    private static final float MAX_SCALE = 1.5f;

    private final InputManager inputManager;
    private final WindowManager windowManager;
    private final FramebufferManager framebufferManager;
    private final CanvasManager canvasManager;

    // Main
    private Framebuffer mainFbo;
    private Canvas mainCanvas;
    // Stick
    public StickAsset stickAsset;
    public ObserverCameraAsset cameraAsset;
    private Camera camera;
    private float stickScale = MAX_SCALE;
    // UI
    public UiAsset uiAsset;

    @Wire
    public MainScene(final RenderManager renderManager, final AssetManager assetManager,
                     final InputManager inputManager, final WindowManager windowManager,
                     final FramebufferManager framebufferManager, final CanvasManager canvasManager) {
        super(renderManager, assetManager);

        this.inputManager = inputManager;
        this.windowManager = windowManager;
        this.framebufferManager = framebufferManager;
        this.canvasManager = canvasManager;
    }

    @Override
    public void initialize() {
        super.initialize();
        inputManager.addKeyEvent(GLFW_KEY_ESCAPE, PRESS, windowManager::closeWindow);
        inputManager.addScrollEvent(scroll -> {
            stickScale += scroll.y * 0.1f;
            if (stickScale > MAX_SCALE) {
                stickScale = MAX_SCALE;
            } else if (stickScale < MIN_SCALE) {
                stickScale = MIN_SCALE;
            }

            stickAsset.setViewScale(stickScale);
        });
    }

    @Override
    public void resize(final int oldWidth, final int oldHeight, final int width, final int height) {
        VIEWPORT.set(windowManager.getWidth(), windowManager.getHeight());
        STICK_SIZE.set(VIEWPORT.x < VIEWPORT.y ? VIEWPORT.x : VIEWPORT.y);

        uiAsset.resize(oldWidth, oldHeight, width, height);
    }

    @Override
    protected void createAssets() {
        // Main
        mainFbo = framebufferManager.supply(VIEWPORT, false, COLOR_BUFFER_MULTISAMPLE_ATTACHMENT, DEPTH_BUFFER_MULTISAMPLE_ATTACHMENT);
        mainCanvas = canvasManager.supply(this, mainFbo, ANTIALIAS_EFFECT);

        // UI
        uiAsset = createAsset(UiAsset.class, new UiAssetArgument(this, GENETIC_ALGORITHM, VIEWPORT));

        // Stick
        cameraAsset = createAsset(ObserverCameraAsset.class, new CameraAssetArgument(
                null,
                get(CAMERA_LOOK_UP_LIMIT),
                get(CAMERA_LOOK_DOWN_LIMIT),
                get(CAMERA_LOOK_SPEED),
                get(CAMERA_MOVE_SPEED)));
        cameraAsset.movingEnabled = false;
        cameraAsset.lookingEnabled = false;

        camera = new ThirdPersonCamera(cameraAsset, STICK_SIZE, new CameraAttribute(get(CAMERA_FOV), 0.1f, STICK_SIZE.x * (float) Math.sqrt(2)), STICK_SIZE.x * (float) Math.sqrt(2) / 2, ORTHOGRAPHIC);

        stickAsset = createAsset(StickAsset.class, new StickAssetArgument(this, GENETIC_ALGORITHM));
        stickAsset.setViewScale(stickScale);
    }

    @Override
    protected RenderPlan createRenderPlan() {
        return GuiRenderPlanBuilder
                .createPlan(VIEWPORT)
                .bindFrameBuffer(
                        mainFbo,
                        WorldRenderPlanBuilder
                                .createPlan(camera)
                                .loadAssets(stickAsset)
                                .clearScreen(ColorUtils.WHITE)
                                .render()
                )
                .bindFrameBuffer(
                        mainFbo,
                        GuiRenderPlanBuilder
                                .createPlan(mainFbo.getSize())
                                .loadAssets(uiAsset)
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
}
