package imagenetic.scene.asset.line;

import imagenetic.scene.MainScene;
import imagenetic.scene.asset.line.genetic.LineGeneticAlgorithm;
import piengine.object.asset.domain.AssetArgument;

public class LineAssetArgument implements AssetArgument {

    public final MainScene mainScene;
    public final LineGeneticAlgorithm geneticAlgorithm;

    public LineAssetArgument(final MainScene mainScene, final LineGeneticAlgorithm geneticAlgorithm) {
        this.mainScene = mainScene;
        this.geneticAlgorithm = geneticAlgorithm;
    }
}
