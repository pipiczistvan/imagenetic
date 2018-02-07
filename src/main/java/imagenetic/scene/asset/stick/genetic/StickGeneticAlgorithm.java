package imagenetic.scene.asset.stick.genetic;

import imagenetic.common.algorithm.genetic.GeneticAlgorithm;
import imagenetic.scene.asset.stick.genetic.entity.LayerChromosome;
import imagenetic.scene.asset.stick.genetic.function.LayerCriterionFunction;
import imagenetic.scene.asset.stick.genetic.function.LayerCrossoverOperator;
import imagenetic.scene.asset.stick.genetic.function.LayerFitnessFunction;
import imagenetic.scene.asset.stick.genetic.function.LayerMutationOperator;
import imagenetic.scene.asset.stick.genetic.function.LayerSelectionOperator;

import java.awt.image.BufferedImage;

public class StickGeneticAlgorithm extends GeneticAlgorithm<LayerChromosome> {

    public StickGeneticAlgorithm(final int maxSize) {
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
        numberOfGenerations = 0;
    }
}
