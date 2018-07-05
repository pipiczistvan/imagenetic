package imagenetic.scene;

import imagenetic.scene.asset.camera.ObserverCameraAsset;
import imagenetic.scene.asset.voxel.VoxelAsset;
import imagenetic.scene.asset.voxel.VoxelAssetArgument;
import org.joml.Vector2i;
import piengine.core.architecture.scene.domain.Scene;
import piengine.core.base.type.color.Color;
import piengine.core.input.manager.InputManager;
import piengine.core.utils.ColorUtils;
import piengine.object.asset.manager.AssetManager;
import piengine.object.asset.plan.GuiRenderAssetContextBuilder;
import piengine.object.camera.asset.CameraAssetArgument;
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

import static imagenetic.common.Config.MAX_SCALE;
import static imagenetic.common.Config.SCENE_RESOLUTION;
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
import static piengine.visual.framebuffer.domain.FramebufferAttachment.COLOR_TEXTURE_ATTACHMENT;
import static piengine.visual.framebuffer.domain.FramebufferAttachment.DEPTH_BUFFER_MULTISAMPLE_ATTACHMENT;
import static piengine.visual.postprocessing.domain.EffectType.ANTIALIAS_EFFECT;
import static piengine.visual.postprocessing.domain.EffectType.RADIAL_GRADIENT_EFFECT;

public class MainScene extends Scene {

    public static final Color COLOR_WHITE = ColorUtils.createNormalizedColor(255, 255, 255);
    public static final Color COLOR_GOLD = ColorUtils.createNormalizedColor(250,250,210);
    public static final Color BACKGROUND_COLOR = new Color(COLOR_WHITE);
    private static final Vector2i VIEWPORT = new Vector2i();

    private final InputManager inputManager;
    private final DisplayManager displayManager;
    private final FramebufferManager framebufferManager;
    private final CanvasManager canvasManager;

    // Main
    private Framebuffer mainFbo;
    private Canvas mainCanvas;
    // Background
    private Framebuffer backgroundFbo;
    private Canvas backgroundCanvas;
    // Stick
    private VoxelAsset voxelAsset;
    private ObserverCameraAsset cameraAsset;
    private ThirdPersonCamera camera;

    @Wire
    public MainScene(final RenderManager renderManager, final AssetManager assetManager,
                     final InputManager inputManager, final DisplayManager displayManager,
                     final FramebufferManager framebufferManager, final CanvasManager canvasManager) {
        super(renderManager, assetManager);

        this.inputManager = inputManager;
        this.displayManager = displayManager;
        this.framebufferManager = framebufferManager;
        this.canvasManager = canvasManager;
    }

    @Override
    public void initialize() {
        recalculateViewport();
        super.initialize();

        inputManager.addKeyEvent(KEY_ESCAPE, PRESS, displayManager::closeDisplay);
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

        // Background
        backgroundFbo = framebufferManager.supply(VIEWPORT, false, COLOR_TEXTURE_ATTACHMENT);
        backgroundCanvas = canvasManager.supply(this, backgroundFbo, RADIAL_GRADIENT_EFFECT);

        // Stick
        cameraAsset = supplyAsset(ObserverCameraAsset.class, new CameraAssetArgument(
                null,
                get(CAMERA_LOOK_UP_LIMIT),
                get(CAMERA_LOOK_DOWN_LIMIT),
                get(CAMERA_LOOK_SPEED),
                get(CAMERA_MOVE_SPEED)));
        cameraAsset.movingEnabled = false;
        cameraAsset.lookingEnabled = false;

        camera = new ThirdPersonCamera(cameraAsset, VIEWPORT, new CameraAttribute(get(CAMERA_FOV), 0.1f, SCENE_RESOLUTION * MAX_SCALE * 2), SCENE_RESOLUTION * MAX_SCALE, ORTHOGRAPHIC);

        //todo: This should by dynamic
        voxelAsset = supplyAsset(VoxelAsset.class, new VoxelAssetArgument(this, cameraAsset));
    }

    @Override
    protected RenderPlan createRenderPlan() {
        return GuiRenderPlanBuilder
                .createPlan(VIEWPORT)
                .bindFrameBuffer(backgroundFbo, renderToBackground())
                .bindFrameBuffer(mainFbo, renderBackground())
                .bindFrameBuffer(mainFbo, renderLines())
                .loadAssetContext(
                        GuiRenderAssetContextBuilder
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
    }

    private RenderPlan renderToBackground() {
        return GuiRenderPlanBuilder
                .createPlan(backgroundFbo.getSize())
                .clearScreen(BACKGROUND_COLOR)
                .render();
    }

    private RenderPlan renderBackground() {
        return GuiRenderPlanBuilder
                .createPlan(VIEWPORT)
                .loadAssetContext(GuiRenderAssetContextBuilder
                        .create()
                        .loadCanvases(backgroundCanvas)
                        .build())
                .clearScreen(ColorUtils.BLACK)
                .render();
    }

    private RenderPlan renderLines() {
        return WorldRenderPlanBuilder
                .createPlan(camera)
                .loadAssets(voxelAsset)
                .render();
    }
}
