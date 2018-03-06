package imagenetic.scene.asset.camera;

import imagenetic.common.Bridge;
import imagenetic.gui.common.api.ViewChangedListener.VIEW_TYPE;
import org.joml.Vector2f;
import org.joml.Vector3f;
import piengine.core.input.manager.InputManager;
import piengine.object.asset.manager.AssetManager;
import piengine.object.camera.asset.CameraAsset;
import piengine.visual.display.manager.DisplayManager;
import puppeteer.annotation.premade.Wire;

import static piengine.core.input.domain.Key.MOUSE_BUTTON_1;
import static piengine.core.input.domain.KeyEventType.PRESS;
import static piengine.core.input.domain.KeyEventType.RELEASE;

public class ObserverCameraAsset extends CameraAsset {

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
        resetRotation(VIEW_TYPE.FRONT);

        inputManager.addKeyEvent(MOUSE_BUTTON_1, PRESS, () -> {
            Vector2f pointer = displayManager.getPointer();

            int frameX = Bridge.frameSide.getX();
            int frameY = Bridge.frameSide.getY();
            int canvasX = frameX + Bridge.frameSide.getCanvasX();
            int canvasY = frameY + Bridge.frameSide.getCanvasY();
            int canvasWidth = canvasX + Bridge.frameSide.getCanvasWidth();
            int canvasHeight = canvasY + Bridge.frameSide.getCanvasHeight();

            if (pointer.x >= canvasX && pointer.x <= canvasWidth
                    && pointer.y >= canvasY && pointer.y <= canvasHeight) {
                lookingEnabled = true;
                lastPos.set(pointer);
            }
        });
        inputManager.addKeyEvent(MOUSE_BUTTON_1, RELEASE, () -> lookingEnabled = false);
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

    public void resetRotation(VIEW_TYPE view) {
        if (view == VIEW_TYPE.FRONT) {
            setRotation(180, 0, 180);
        } else if (view == VIEW_TYPE.SIDE) {
            setRotation(270, 0, 180);
        }
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
