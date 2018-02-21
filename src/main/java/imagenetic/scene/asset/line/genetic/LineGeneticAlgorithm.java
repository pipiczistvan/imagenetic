package imagenetic.scene.asset.line.genetic;

import imagenetic.common.algorithm.genetic.GeneticAlgorithm;
import imagenetic.scene.asset.line.genetic.entity.LayerChromosome;
import imagenetic.scene.asset.line.genetic.function.LayerChromosomeCopier;
import imagenetic.scene.asset.line.genetic.function.LayerChromosomeCreator;
import imagenetic.scene.asset.line.genetic.function.LayerCriterionFunction;
import imagenetic.scene.asset.line.genetic.function.LayerCrossoverOperator;
import imagenetic.scene.asset.line.genetic.function.LayerFitnessFunction;
import imagenetic.scene.asset.line.genetic.function.LayerMutationOperator;
import imagenetic.scene.asset.line.genetic.function.LayerSelectionOperator;

public class LineGeneticAlgorithm extends GeneticAlgorithm<LayerChromosome> {

    private final AlgorithmParameters parameters;

    public LineGeneticAlgorithm(final AlgorithmParameters parameters) {
        super(
                new LayerFitnessFunction(parameters),
                new LayerCriterionFunction(),
                new LayerSelectionOperator(),
                new LayerCrossoverOperator(),
                new LayerMutationOperator(parameters),
                new LayerChromosomeCopier(),
                new LayerChromosomeCreator(parameters)
        );

        this.parameters = parameters;
    }

    @Override
    public void initialize(int populationCount) {
        ((LayerMutationOperator) mutationOperator).initialize();
        super.initialize(populationCount);

        parameters.changed = false;
    }

    public void initializePopulation() {
        initialize(parameters.populationCount);
    }

    public void initializeFitnessFunction() {
        ((LayerFitnessFunction) fitnessFunction).initialize();
        parameters.imageChanged = false;
    }
}
