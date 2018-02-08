package imagenetic.scene.asset.stick;

import imagenetic.scene.MainScene;
import imagenetic.scene.asset.stick.genetic.StickGeneticAlgorithm;
import piengine.object.asset.domain.AssetArgument;

public class StickAssetArgument implements AssetArgument {

    public final MainScene mainScene;
    public final StickGeneticAlgorithm geneticAlgorithm;

    public StickAssetArgument(final MainScene mainScene, final StickGeneticAlgorithm geneticAlgorithm) {
        this.mainScene = mainScene;
        this.geneticAlgorithm = geneticAlgorithm;
    }
}
