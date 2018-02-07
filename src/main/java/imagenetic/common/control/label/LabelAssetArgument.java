package imagenetic.common.control.label;

import org.joml.Vector2i;
import piengine.object.asset.domain.AssetArgument;

public class LabelAssetArgument implements AssetArgument {

    final String text;
    final Vector2i viewport;
    final float maxLength;

    public LabelAssetArgument(final String text, final Vector2i viewport, final float maxLength) {
        this.text = text;
        this.viewport = viewport;
        this.maxLength = maxLength;
    }
}
