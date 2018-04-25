package imagenetic.algorithm;

import imagenetic.common.algorithm.genetic.Generation;
import org.junit.Test;

import java.util.Arrays;

public class GeneticAlgorithmTest {

    @Test
    public void test() {
        NumberGeneticAlgorithm geneticAlgorithm = new NumberGeneticAlgorithm();
        geneticAlgorithm.initialize();
        geneticAlgorithm.criteriaRateChanged(1);
        geneticAlgorithm.elitismRateChanged(1);
        geneticAlgorithm.mutationRateChanged(0.1);
        geneticAlgorithm.populationCountChanged(10);

        while (!geneticAlgorithm.isDone()) {
            Generation<Integer[]> lastGeneration = geneticAlgorithm.nextGeneration();
            System.out.printf("Current generation's best is: %s\n", Arrays.toString(lastGeneration.population.get(0).getGenoType()));
        }

        System.out.println("Done!");
        System.out.printf("Number of generations: %d", geneticAlgorithm.getNumberOfGenerations());
    }
}
