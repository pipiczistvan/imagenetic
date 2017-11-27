package imagenetic.scene.asset.stick.genetic;

import imagenetic.common.algorithm.genetic.GeneticAlgorithm;
import imagenetic.scene.asset.stick.genetic.entity.StickChromosome;
import imagenetic.scene.asset.stick.genetic.function.StickCriterionFunction;
import imagenetic.scene.asset.stick.genetic.function.StickCrossoverOperator;
import imagenetic.scene.asset.stick.genetic.function.StickFitnessFunction;
import imagenetic.scene.asset.stick.genetic.function.StickMutationOperator;
import imagenetic.scene.asset.stick.genetic.function.StickSelectionOperator;

public class StickGeneticAlgorithm extends GeneticAlgorithm<StickChromosome> {

    public StickGeneticAlgorithm(String imagePath, int maxSize) {
        super(
                new StickFitnessFunction(imagePath, maxSize),
                new StickCriterionFunction(),
                new StickSelectionOperator(),
                new StickCrossoverOperator(),
                new StickMutationOperator(maxSize)
        );
    }
}
