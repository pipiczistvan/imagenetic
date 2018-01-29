package imagenetic.scene.asset.ui;

import imagenetic.scene.asset.stick.genetic.StickGeneticAlgorithm;
import org.joml.Vector2i;
import piengine.object.asset.domain.AssetArgument;

public class UiAssetArgument implements AssetArgument {

    public final StickGeneticAlgorithm geneticAlgorithm;
    public final Vector2i viewport;

    public UiAssetArgument(final StickGeneticAlgorithm geneticAlgorithm, final Vector2i viewport) {
        this.geneticAlgorithm = geneticAlgorithm;
        this.viewport = viewport;
    }
}
