package imagenetic.scene.asset.camera;

import imagenetic.common.Bridge;
import org.joml.Vector2f;
import org.joml.Vector3f;
import piengine.core.input.manager.InputManager;
import piengine.object.asset.manager.AssetManager;
import piengine.object.camera.asset.CameraAsset;
import piengine.visual.display.manager.DisplayManager;
import puppeteer.annotation.premade.Wire;

import static piengine.core.input.domain.Key.KEY_R;
import static piengine.core.input.domain.Key.MOUSE_BUTTON_1;
import static piengine.core.input.domain.KeyEventType.PRESS;
import static piengine.core.input.domain.KeyEventType.RELEASE;

public class ObserverCameraAsset extends CameraAsset {

    private static final Vector3f DEFAULT_ROTATION = new Vector3f(180, 0, 180);

    private final InputManager inputManager;
    private final DisplayManager displayManager;
    private final Vector2f lastPos;
    private final Vector2f looking;

    @Wire
    public ObserverCameraAsset(final AssetManager assetManager, final InputManager inputManager, final DisplayManager displayManager) {
        super(assetManager, inputManager, displayManager);
        this.inputManager = inputManager;
        this.displayManager = displayManager;
        this.lastPos = new Vector2f();
        this.looking = new Vector2f();
    }

    @Override
    public void initialize() {
        resetRotation();

        inputManager.addKeyEvent(MOUSE_BUTTON_1, PRESS, () -> {
            Vector2f pointer = displayManager.getPointer();

            int frameX = Bridge.mainFrame.getX();
            int frameY = Bridge.mainFrame.getY();
            int canvasX = frameX + Bridge.mainFrame.canvas_main.getX();
            int canvasY = frameY + Bridge.mainFrame.canvas_main.getY();
            int canvasWidth = canvasX + Bridge.mainFrame.canvas_main.getWidth();
            int canvasHeight = canvasY + Bridge.mainFrame.canvas_main.getHeight();

            if (pointer.x >= canvasX && pointer.x <= canvasWidth
                    && pointer.y >= canvasY && pointer.y <= canvasHeight) {
                lookingEnabled = true;
                lastPos.set(pointer);
            }
        });
        inputManager.addKeyEvent(MOUSE_BUTTON_1, RELEASE, () -> lookingEnabled = false);
        inputManager.addKeyEvent(KEY_R, PRESS, this::resetRotation);
        inputManager.addCursorEvent(v -> {
            if (lookingEnabled) {
                Vector2f delta = new Vector2f();
                v.sub(lastPos, delta);

                looking.set(delta);
                lastPos.set(v);
            }
        });
    }

    @Override
    public void update(final float delta) {
        Vector3f newRotation = calculateRotation(delta);

        setRotation(newRotation);

        looking.set(0, 0);
    }

    private void resetRotation() {
        setRotation(DEFAULT_ROTATION);
    }

    private Vector3f calculateRotation(final double delta) {
        Vector3f newRotation = new Vector3f(getRotation());

        newRotation.add(looking.x * arguments.lookSpeed * (float) delta, -looking.y * arguments.lookSpeed * (float) delta, 0.0f);

        if (newRotation.x > 360) {
            newRotation.sub(360, 0, 0);
        } else if (newRotation.x < 0) {
            newRotation.add(360, 0, 0);
        }

        if (newRotation.y > arguments.lookUpLimit) {
            newRotation.set(newRotation.x, arguments.lookUpLimit, newRotation.z);
        } else if (newRotation.y < arguments.lookDownLimit) {
            newRotation.set(newRotation.x, arguments.lookDownLimit, newRotation.z);
        }

        return newRotation;
    }
}
