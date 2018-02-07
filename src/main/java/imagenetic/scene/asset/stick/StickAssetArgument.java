package imagenetic.scene.asset.stick;

import imagenetic.scene.asset.stick.genetic.StickGeneticAlgorithm;
import imagenetic.scene.asset.ui.UiAsset;
import piengine.object.asset.domain.AssetArgument;

public class StickAssetArgument implements AssetArgument {

    public final StickGeneticAlgorithm geneticAlgorithm;
    public final UiAsset uiAsset;

    public StickAssetArgument(final StickGeneticAlgorithm geneticAlgorithm, final UiAsset uiAsset) {
        this.geneticAlgorithm = geneticAlgorithm;
        this.uiAsset = uiAsset;
    }
}
