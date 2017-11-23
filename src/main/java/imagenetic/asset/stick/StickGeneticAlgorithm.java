package imagenetic.asset.stick;

import imagenetic.algorithm.GeneticAlgorithm;

public class StickGeneticAlgorithm extends GeneticAlgorithm<StickChromosome> {

    public StickGeneticAlgorithm(String imagePath, int maxSize) {
        super(
                new StickFitnessFunction(imagePath, maxSize),
                new StickCriterionFunction(),
                new StickSelectionOperator(),
                new StickCrossOverOperator(),
                new StickMutationOperator()
        );
    }
}
