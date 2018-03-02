package imagenetic.scene.asset.voxel;

import imagenetic.scene.MainScene;
import piengine.object.asset.domain.AssetArgument;

public class LineAssetArgument implements AssetArgument {

    public final MainScene mainScene;
    public final int geneticAlgorithmSize;

    public LineAssetArgument(final MainScene mainScene, final int geneticAlgorithmSize) {
        this.mainScene = mainScene;
        this.geneticAlgorithmSize = geneticAlgorithmSize;
    }
}
