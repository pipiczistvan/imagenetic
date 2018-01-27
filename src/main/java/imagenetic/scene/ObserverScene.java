package imagenetic.scene;

import imagenetic.scene.asset.camera.ObserverCameraAsset;
import imagenetic.scene.asset.stick.StickAsset;
import imagenetic.scene.asset.stick.StickAssetArgument;
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
import static piengine.core.base.type.property.PropertyKeys.CAMERA_FAR_PLANE;
import static piengine.core.base.type.property.PropertyKeys.CAMERA_FOV;
import static piengine.core.base.type.property.PropertyKeys.CAMERA_LOOK_DOWN_LIMIT;
import static piengine.core.base.type.property.PropertyKeys.CAMERA_LOOK_SPEED;
import static piengine.core.base.type.property.PropertyKeys.CAMERA_LOOK_UP_LIMIT;
import static piengine.core.base.type.property.PropertyKeys.CAMERA_MOVE_SPEED;
import static piengine.core.base.type.property.PropertyKeys.CAMERA_NEAR_PLANE;
import static piengine.core.base.type.property.PropertyKeys.CAMERA_VIEWPORT_HEIGHT;
import static piengine.core.base.type.property.PropertyKeys.CAMERA_VIEWPORT_WIDTH;
import static piengine.core.input.domain.KeyEventType.PRESS;
import static piengine.object.camera.domain.ProjectionType.ORTHOGRAPHIC;
import static piengine.visual.framebuffer.domain.FramebufferAttachment.COLOR_BUFFER_MULTISAMPLE_ATTACHMENT;
import static piengine.visual.framebuffer.domain.FramebufferAttachment.DEPTH_BUFFER_MULTISAMPLE_ATTACHMENT;
import static piengine.visual.postprocessing.domain.EffectType.ANTIALIAS_EFFECT;

public class ObserverScene extends Scene {

    private static final Vector2i VIEWPORT = new Vector2i(get(CAMERA_VIEWPORT_WIDTH), get(CAMERA_VIEWPORT_HEIGHT));
    private static final Vector2i STICK_CANVAS_VIEWPORT = new Vector2i(600, VIEWPORT.y);
    private static final Vector2i UI_CANVAS_VIEWPORT = new Vector2i(200, VIEWPORT.y);

    private final InputManager inputManager;
    private final WindowManager windowManager;
    private final FramebufferManager framebufferManager;
    private final CanvasManager canvasManager;

    // Stick
    private Framebuffer stickFbo;
    private Canvas stickCanvas;
    private StickAsset stickAsset;
    private ObserverCameraAsset cameraAsset;
    private Camera camera;

    // UI
    private Framebuffer uiFbo;
    private Canvas uiCanvas;
    private UiAsset uiAsset;

    @Wire
    public ObserverScene(final RenderManager renderManager, final AssetManager assetManager,
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
        inputManager.addEvent(GLFW_KEY_ESCAPE, PRESS, windowManager::closeWindow);
    }

    @Override
    protected void createAssets() {
        // Stick
        cameraAsset = createAsset(ObserverCameraAsset.class, new CameraAssetArgument(
                null,
                get(CAMERA_LOOK_UP_LIMIT),
                get(CAMERA_LOOK_DOWN_LIMIT),
                get(CAMERA_LOOK_SPEED),
                get(CAMERA_MOVE_SPEED)));
        cameraAsset.movingEnabled = false;
        cameraAsset.lookingEnabled = false;

        camera = new ThirdPersonCamera(cameraAsset, STICK_CANVAS_VIEWPORT, new CameraAttribute(get(CAMERA_FOV), get(CAMERA_NEAR_PLANE), get(CAMERA_FAR_PLANE)), STICK_CANVAS_VIEWPORT.x / 2, ORTHOGRAPHIC);

        stickFbo = framebufferManager.supply(STICK_CANVAS_VIEWPORT, COLOR_BUFFER_MULTISAMPLE_ATTACHMENT, DEPTH_BUFFER_MULTISAMPLE_ATTACHMENT);
        stickCanvas = canvasManager.supply(this, stickFbo, ANTIALIAS_EFFECT);
        float ratio = STICK_CANVAS_VIEWPORT.x / (float) VIEWPORT.x;
        stickCanvas.setRotation(180, 180, 0);
        stickCanvas.setScale(ratio, 1, 1);
        stickCanvas.setPosition(-1 + ratio, 0, 0);
        stickAsset = createAsset(StickAsset.class, new StickAssetArgument(STICK_CANVAS_VIEWPORT));

        // UI
        uiFbo = framebufferManager.supply(UI_CANVAS_VIEWPORT, COLOR_BUFFER_MULTISAMPLE_ATTACHMENT, DEPTH_BUFFER_MULTISAMPLE_ATTACHMENT);
        uiCanvas = canvasManager.supply(this, uiFbo, ANTIALIAS_EFFECT);
        float ratio2 = UI_CANVAS_VIEWPORT.x / (float) VIEWPORT.x;
        uiCanvas.setRotation(180, 180, 0);
        uiCanvas.setScale(ratio2, 1, 1);
        uiCanvas.setPosition(1 - ratio2, 0, 0);
        uiAsset = createAsset(UiAsset.class, new UiAssetArgument(UI_CANVAS_VIEWPORT));
    }

    @Override
    protected RenderPlan createRenderPlan() {
        return GuiRenderPlanBuilder
                .createPlan(VIEWPORT)
                .bindFrameBuffer(
                        stickFbo,
                        WorldRenderPlanBuilder
                                .createPlan(camera)
                                .loadAssets(stickAsset)
                                .clearScreen(ColorUtils.WHITE)
                                .render()
                )
                .bindFrameBuffer(
                        uiFbo,
                        GuiRenderPlanBuilder
                                .createPlan(UI_CANVAS_VIEWPORT)
                                .loadAssets(uiAsset)
                                .clearScreen(ColorUtils.RED)
                                .render()
                )
                .loadAssetContext(GuiRenderAssetContextBuilder
                        .create()
                        .loadCanvases(stickCanvas, uiCanvas)
                        .build()
                )
                .clearScreen(ColorUtils.BLACK)
                .render();
    }
}
