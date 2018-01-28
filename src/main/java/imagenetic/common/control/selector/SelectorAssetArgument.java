package imagenetic.common.control.selector;

import imagenetic.common.event.OnSelected;
import org.joml.Vector2i;
import piengine.object.asset.domain.AssetArgument;
import piengine.object.entity.domain.Entity;

public class SelectorAssetArgument implements AssetArgument {

    public final Entity parent;
    public final String defaultImageName;
    public final String hoverImageName;
    public final String pressImageName;
    public final Vector2i viewport;
    public final String text;
    public final OnSelected onSelected;

    public SelectorAssetArgument(final Entity parent, final String defaultImageName, final String hoverImageName,
                                 final String pressImageName, final Vector2i viewport, final String text,
                                 final OnSelected onSelected) {
        this.parent = parent;
        this.defaultImageName = defaultImageName;
        this.hoverImageName = hoverImageName;
        this.pressImageName = pressImageName;
        this.viewport = viewport;
        this.text = text;
        this.onSelected = onSelected;
    }
}
