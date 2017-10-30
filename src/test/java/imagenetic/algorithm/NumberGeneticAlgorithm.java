package imagenetic.algorithm;

import imagenetic.algorithm.function.NumberCriterionFunction;
import imagenetic.algorithm.function.NumberCrossOverOperator;
import imagenetic.algorithm.function.NumberFitnessFunction;
import imagenetic.algorithm.function.NumberMutationOperator;
import imagenetic.algorithm.function.NumberSelectionOperator;

public class NumberGeneticAlgorithm extends GeneticAlgorithm<Integer[]> {

    public NumberGeneticAlgorithm() {
        super(new NumberFitnessFunction(), new NumberCriterionFunction(),
                new NumberSelectionOperator(), new NumberCrossOverOperator(),
                new NumberMutationOperator());
    }
}
