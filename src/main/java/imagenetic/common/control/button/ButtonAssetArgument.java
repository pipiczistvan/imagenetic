package imagenetic.common.control.button;

import imagenetic.common.event.OnClick;
import org.joml.Vector2i;
import piengine.object.asset.domain.AssetArgument;

public class ButtonAssetArgument implements AssetArgument {

    final String defaultImageName;
    final String hoverImageName;
    final String pressImageName;
    final Vector2i viewport;
    final String text;
    final OnClick onClick;

    public ButtonAssetArgument(final String defaultImageName, final String hoverImageName,
                               final String pressImageName, final Vector2i viewport,
                               final String text, final OnClick onClick) {
        this.defaultImageName = defaultImageName;
        this.hoverImageName = hoverImageName;
        this.pressImageName = pressImageName;
        this.viewport = viewport;
        this.text = text;
        this.onClick = onClick;
    }
}
