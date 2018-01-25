package imagenetic.scene;

import imagenetic.scene.asset.camera.ObserverCameraAsset;
import imagenetic.scene.asset.fps.FpsAsset;
import imagenetic.scene.asset.fps.FpsAssetArgument;
import imagenetic.scene.asset.stick.StickAsset;
import imagenetic.scene.asset.stick.StickAssetArgument;
import org.joml.Vector2i;
import piengine.core.architecture.scene.domain.Scene;
import piengine.core.input.manager.InputManager;
import piengine.core.utils.ColorUtils;
import piengine.gui.asset.ButtonAsset;
import piengine.gui.asset.ButtonAssetArgument;
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

    private final InputManager inputManager;
    private final WindowManager windowManager;
    private final FramebufferManager framebufferManager;
    private final CanvasManager canvasManager;

    private Framebuffer framebuffer;
    private Canvas mainCanvas;
    private Camera camera;

    private ObserverCameraAsset cameraAsset;
    private ButtonAsset buttonAsset;
    private FpsAsset fpsAsset;
    private StickAsset stickAsset;

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
        cameraAsset = createAsset(ObserverCameraAsset.class, new CameraAssetArgument(
                null,
                get(CAMERA_LOOK_UP_LIMIT),
                get(CAMERA_LOOK_DOWN_LIMIT),
                get(CAMERA_LOOK_SPEED),
                get(CAMERA_MOVE_SPEED)));
        cameraAsset.movingEnabled = false;
        cameraAsset.lookingEnabled = false;

        camera = new ThirdPersonCamera(cameraAsset, VIEWPORT, new CameraAttribute(get(CAMERA_FOV), get(CAMERA_NEAR_PLANE), get(CAMERA_FAR_PLANE)), VIEWPORT.x / 2, ORTHOGRAPHIC);

        framebuffer = framebufferManager.supply(VIEWPORT, COLOR_BUFFER_MULTISAMPLE_ATTACHMENT, DEPTH_BUFFER_MULTISAMPLE_ATTACHMENT);
        mainCanvas = canvasManager.supply(this, framebuffer, ANTIALIAS_EFFECT);
        mainCanvas.rotate(180, 180, 0);

        buttonAsset = createAsset(ButtonAsset.class, new ButtonAssetArgument(
                "buttonDefault", "buttonHover", "buttonPress",
                VIEWPORT, "Please press me!", () -> System.out.println("Button clicked!")));
        fpsAsset = createAsset(FpsAsset.class, new FpsAssetArgument());

        stickAsset = createAsset(StickAsset.class, new StickAssetArgument());
    }

    @Override
    protected RenderPlan createRenderPlan() {
        return GuiRenderPlanBuilder
                .createPlan(VIEWPORT)
                .bindFrameBuffer(
                        framebuffer,
                        WorldRenderPlanBuilder
                                .createPlan(camera)
                                .loadAssets(stickAsset)
                                .clearScreen(ColorUtils.WHITE)
                                .render()
                )
                .loadAssetContext(GuiRenderAssetContextBuilder
                        .create()
                        .loadCanvases(mainCanvas)
                        .build()
                )
                .loadAssets(fpsAsset)
                .clearScreen(ColorUtils.BLACK)
                .render();
    }
}
