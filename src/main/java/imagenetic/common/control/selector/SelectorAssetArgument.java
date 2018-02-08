package imagenetic.common.control.selector;

import imagenetic.common.event.OnSelected;
import org.joml.Vector2i;
import piengine.object.asset.domain.AssetArgument;
import piengine.object.entity.domain.Entity;

public class SelectorAssetArgument implements AssetArgument {

    public final Entity parent;
    public final Vector2i viewport;
    public final String text;
    public final OnSelected onSelected;

    public SelectorAssetArgument(final Entity parent, final Vector2i viewport, final String text, final OnSelected onSelected) {
        this.parent = parent;
        this.viewport = viewport;
        this.text = text;
        this.onSelected = onSelected;
    }
}
