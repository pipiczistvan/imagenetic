package imagenetic.common.control.button;

import imagenetic.common.event.OnClick;
import org.joml.Vector2i;
import piengine.object.asset.domain.AssetArgument;

public class ButtonAssetArgument implements AssetArgument {

    final Vector2i viewport;
    final String text;
    final OnClick onClick;

    public ButtonAssetArgument(final Vector2i viewport, final String text, final OnClick onClick) {
        this.viewport = viewport;
        this.text = text;
        this.onClick = onClick;
    }
}
