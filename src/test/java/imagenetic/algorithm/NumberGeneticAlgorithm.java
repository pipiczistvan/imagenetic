package imagenetic.algorithm;

import imagenetic.algorithm.function.NumberCriterionFunction;
import imagenetic.algorithm.function.NumberCrossoverOperator;
import imagenetic.algorithm.function.NumberFitnessFunction;
import imagenetic.algorithm.function.NumberMutationOperator;
import imagenetic.algorithm.function.NumberSelectionOperator;
import imagenetic.common.algorithm.genetic.GeneticAlgorithm;

public class NumberGeneticAlgorithm extends GeneticAlgorithm<Integer[]> {

    public NumberGeneticAlgorithm() {
        super(new NumberFitnessFunction(), new NumberCriterionFunction(),
                new NumberSelectionOperator(), new NumberCrossoverOperator(),
                new NumberMutationOperator());
    }
}
