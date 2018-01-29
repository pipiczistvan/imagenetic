package imagenetic.scene.asset.stick;

import imagenetic.scene.asset.stick.genetic.StickGeneticAlgorithm;
import org.joml.Vector2i;
import piengine.object.asset.domain.AssetArgument;

public class StickAssetArgument implements AssetArgument {

    public final StickGeneticAlgorithm geneticAlgorithm;
    public final int size;
    public final int halfSize;

    public StickAssetArgument(final StickGeneticAlgorithm geneticAlgorithm, final Vector2i viewport) {
        this.geneticAlgorithm = geneticAlgorithm;
        this.size = viewport.x > viewport.y ? viewport.x : viewport.y;
        this.halfSize = size / 2;
    }
}
