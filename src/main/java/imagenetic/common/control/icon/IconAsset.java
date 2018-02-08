package imagenetic.common.control.icon;

import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;
import piengine.core.input.domain.KeyEventType;
import piengine.core.input.manager.InputManager;
import piengine.object.asset.domain.GuiAsset;
import piengine.object.asset.manager.AssetManager;
import piengine.object.asset.plan.GuiRenderAssetContext;
import piengine.object.asset.plan.GuiRenderAssetContextBuilder;
import piengine.object.canvas.domain.Canvas;
import piengine.object.canvas.manager.CanvasManager;
import piengine.visual.image.domain.Image;
import piengine.visual.image.manager.ImageManager;
import puppeteer.annotation.premade.Wire;

public class IconAsset extends GuiAsset<IconAssetArgument> {

    public static final float SCALE_X = 0.1f;
    public static final float SCALE_Y = 0.1f;

    private final ImageManager imageManager;
    private final CanvasManager canvasManager;
    private final InputManager inputManager;

    private Image defaultImage;
    private Image hoverImage;
    private Image pressImage;
    private Image iconImage;
    private Canvas buttonCanvas;
    private Canvas iconCanvas;

    private float x, y, width, height;
    private boolean hover = false;
    private boolean pressed = false;

    @Wire
    public IconAsset(final AssetManager assetManager, final ImageManager imageManager, final CanvasManager canvasManager,
                     final InputManager inputManager) {
        super(assetManager);

        this.imageManager = imageManager;
        this.canvasManager = canvasManager;
        this.inputManager = inputManager;
    }

    @Override
    public void initialize() {
        defaultImage = imageManager.supply("buttonDefault");
        hoverImage = imageManager.supply("buttonHover");
        pressImage = imageManager.supply("buttonPress");
        buttonCanvas = canvasManager.supply(this, defaultImage);
        buttonCanvas.setScale(SCALE_X, SCALE_Y, 1.0f);

        iconImage = imageManager.supply(arguments.icon);
        iconCanvas = canvasManager.supply(this, iconImage);
        iconCanvas.setScale(SCALE_X, SCALE_Y, 1.0f);

        setupButtonParameters();

        inputManager.addEvent(v -> {
            hover = v.x >= x && v.x <= x + width && v.y >= y && v.y <= y + height;

            if (pressed) {
                buttonCanvas.texture = pressImage;
            } else {
                if (hover) {
                    buttonCanvas.texture = hoverImage;
                } else {
                    buttonCanvas.texture = defaultImage;
                }
            }
        });
        inputManager.addEvent(GLFW.GLFW_MOUSE_BUTTON_1, KeyEventType.PRESS, () -> {
            pressed = hover;
            if (pressed) {
                buttonCanvas.texture = pressImage;
            }
        });
        inputManager.addEvent(GLFW.GLFW_MOUSE_BUTTON_1, KeyEventType.RELEASE, () -> {
            if (pressed && hover) {
                arguments.onClick.invoke();
            }
            pressed = false;
            if (hover) {
                buttonCanvas.texture = hoverImage;
            } else {
                buttonCanvas.texture = defaultImage;
            }
        });
    }

    @Override
    public void update(final float delta) {
    }

    @Override
    public GuiRenderAssetContext getAssetContext() {
        return GuiRenderAssetContextBuilder.create()
                .loadCanvases(buttonCanvas)
                .loadCanvases(iconCanvas)
                .build();
    }

    @Override
    public void translate(final float x, final float y, final float z) {
        super.translate(x, y, z);
        setupButtonParameters();
    }

    @Override
    public void scale(final float x, final float y, final float z) {
        super.scale(x, y, z);
        setupButtonParameters();
    }

    private void setupButtonParameters() {
        Vector3f scale = buttonCanvas.getScale();
        Vector3f position = buttonCanvas.getPosition();

        width = arguments.viewport.x * scale.x;
        height = arguments.viewport.y * scale.y;
        x = (arguments.viewport.x - width + arguments.viewport.x * position.x) / 2;
        y = (arguments.viewport.y - height - arguments.viewport.y * position.y) / 2;
    }
}
