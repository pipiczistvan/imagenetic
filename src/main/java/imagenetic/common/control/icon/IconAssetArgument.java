package imagenetic.common.control.icon;

import imagenetic.common.event.OnClick;
import org.joml.Vector2i;
import piengine.object.asset.domain.AssetArgument;

public class IconAssetArgument implements AssetArgument {

    final Vector2i viewport;
    final String icon;
    final OnClick onClick;

    public IconAssetArgument(final Vector2i viewport, final String icon, final OnClick onClick) {
        this.viewport = viewport;
        this.icon = icon;
        this.onClick = onClick;
    }
}
