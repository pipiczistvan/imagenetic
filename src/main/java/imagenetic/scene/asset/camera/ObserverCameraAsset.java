package imagenetic.scene.asset.camera;

import org.joml.Vector2f;
import org.joml.Vector3f;
import piengine.core.input.manager.InputManager;
import piengine.object.camera.asset.CameraAsset;
import piengine.visual.window.manager.WindowManager;
import puppeteer.annotation.premade.Wire;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_R;
import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_LEFT;
import static piengine.core.input.domain.KeyEventType.PRESS;
import static piengine.core.input.domain.KeyEventType.RELEASE;

public class ObserverCameraAsset extends CameraAsset {

    private static final Vector3f DEFAULT_POSITION = new Vector3f();
    private static final Vector3f DEFAULT_ROTATION = new Vector3f();

    private final InputManager inputManager;
    private final WindowManager windowManager;
    private final Vector2f lastPos;
    private final Vector2f looking;

    @Wire
    public ObserverCameraAsset(final InputManager inputManager, final WindowManager windowManager) {
        super(inputManager, windowManager);
        this.inputManager = inputManager;
        this.windowManager = windowManager;
        this.lastPos = new Vector2f();
        this.looking = new Vector2f();
    }

    @Override
    public void initialize() {
        inputManager.addEvent(GLFW_MOUSE_BUTTON_LEFT, PRESS, () -> {
            lookingEnabled = true;
            lastPos.set(windowManager.getPointer());
        });
        inputManager.addEvent(GLFW_MOUSE_BUTTON_LEFT, RELEASE, () -> lookingEnabled = false);
        inputManager.addEvent(GLFW_KEY_R, PRESS, this::resetRotation);
        inputManager.addEvent(v -> {
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

        setPositionRotation(DEFAULT_POSITION, newRotation);

        looking.set(0, 0);
    }

    private void resetRotation() {
        setPositionRotation(DEFAULT_POSITION, DEFAULT_ROTATION);
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
