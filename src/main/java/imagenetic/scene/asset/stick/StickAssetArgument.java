package imagenetic.scene.asset.stick;

import imagenetic.scene.asset.stick.genetic.StickGeneticAlgorithm;
import piengine.object.asset.domain.AssetArgument;

public class StickAssetArgument implements AssetArgument {

    public final StickGeneticAlgorithm geneticAlgorithm;
    public final int size;
    public final int halfSize;
    public final float viewScale;

    public StickAssetArgument(final StickGeneticAlgorithm geneticAlgorithm, final int size, final int maxStickSize) {
        this.geneticAlgorithm = geneticAlgorithm;
        this.size = size;
        this.halfSize = size / 2;
        this.viewScale = (float) size / (float) maxStickSize;
    }
}
