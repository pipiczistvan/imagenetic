package imagenetic.scene.asset.stick;

import org.joml.Vector2i;
import piengine.object.asset.domain.AssetArgument;

public class StickAssetArgument implements AssetArgument {

    public final int size;
    public final int halfSize;

    public StickAssetArgument(final Vector2i viewport) {
        size = viewport.x > viewport.y ? viewport.x : viewport.y;
        halfSize = size / 2;
    }
}
