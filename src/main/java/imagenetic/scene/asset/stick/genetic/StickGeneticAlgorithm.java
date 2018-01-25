package imagenetic.scene.asset.stick.genetic;

import imagenetic.common.algorithm.genetic.GeneticAlgorithm;
import imagenetic.scene.asset.stick.genetic.entity.StickChromosome;
import imagenetic.scene.asset.stick.genetic.function.StickCriterionFunction;
import imagenetic.scene.asset.stick.genetic.function.StickCrossoverOperator;
import imagenetic.scene.asset.stick.genetic.function.StickFitnessFunction;
import imagenetic.scene.asset.stick.genetic.function.StickMutationOperator;
import imagenetic.scene.asset.stick.genetic.function.StickSelectionOperator;

import java.awt.image.BufferedImage;

public class StickGeneticAlgorithm extends GeneticAlgorithm<StickChromosome> {

    public StickGeneticAlgorithm(BufferedImage originalImage, int maxSize) {
        super(
                new StickFitnessFunction(originalImage, maxSize),
                new StickCriterionFunction(),
                new StickSelectionOperator(),
                new StickCrossoverOperator(),
                new StickMutationOperator(maxSize)
        );
    }
}
