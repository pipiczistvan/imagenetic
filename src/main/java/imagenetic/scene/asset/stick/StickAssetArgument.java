package imagenetic.scene.asset.stick;

import imagenetic.scene.asset.stick.genetic.StickGeneticAlgorithm;
import piengine.object.asset.domain.AssetArgument;

public class StickAssetArgument implements AssetArgument {

    public final StickGeneticAlgorithm geneticAlgorithm;

    public StickAssetArgument(final StickGeneticAlgorithm geneticAlgorithm) {
        this.geneticAlgorithm = geneticAlgorithm;
    }
}
