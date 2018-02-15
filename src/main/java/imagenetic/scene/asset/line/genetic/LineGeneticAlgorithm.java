package imagenetic.scene.asset.line.genetic;

import imagenetic.common.algorithm.genetic.GeneticAlgorithm;
import imagenetic.scene.asset.line.genetic.entity.LayerChromosome;
import imagenetic.scene.asset.line.genetic.function.LayerCriterionFunction;
import imagenetic.scene.asset.line.genetic.function.LayerCrossoverOperator;
import imagenetic.scene.asset.line.genetic.function.LayerFitnessFunction;
import imagenetic.scene.asset.line.genetic.function.LayerMutationOperator;
import imagenetic.scene.asset.line.genetic.function.LayerSelectionOperator;

import java.awt.image.BufferedImage;

public class LineGeneticAlgorithm extends GeneticAlgorithm<LayerChromosome> {

    public LineGeneticAlgorithm(final int maxSize) {
        super(
                new LayerFitnessFunction(maxSize),
                new LayerCriterionFunction(),
                new LayerSelectionOperator(),
                new LayerCrossoverOperator(),
                new LayerMutationOperator(maxSize)
        );
    }

    public void setImage(final BufferedImage image) {
        ((LayerFitnessFunction) super.fitnessFunction).setImage(image);
        initialize();
    }
}
