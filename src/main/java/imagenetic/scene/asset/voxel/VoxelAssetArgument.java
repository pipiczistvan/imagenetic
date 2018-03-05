package imagenetic.scene.asset.voxel;

import imagenetic.scene.MainScene;
import imagenetic.scene.asset.camera.ObserverCameraAsset;
import piengine.object.asset.domain.AssetArgument;

public class VoxelAssetArgument implements AssetArgument {

    public final MainScene mainScene;
    public final int geneticAlgorithmSize;
    public final ObserverCameraAsset cameraAsset;

    public VoxelAssetArgument(final MainScene mainScene, final int geneticAlgorithmSize,
                              final ObserverCameraAsset cameraAsset) {
        this.mainScene = mainScene;
        this.geneticAlgorithmSize = geneticAlgorithmSize;
        this.cameraAsset = cameraAsset;
    }
}
