package imagenetic.scene.asset.ui;

import org.joml.Vector2i;
import piengine.object.asset.domain.AssetArgument;

public class UiAssetArgument implements AssetArgument {

    public final Vector2i viewport;

    public UiAssetArgument(final Vector2i viewport) {
        this.viewport = viewport;
    }
}
