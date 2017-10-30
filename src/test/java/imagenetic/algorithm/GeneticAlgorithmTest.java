package imagenetic.algorithm;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class GeneticAlgorithmTest {

    @Test
    public void test() throws Exception {
        List<Integer[]> initialPopulation = Arrays.asList(
                new Integer[]{5, 2, 3, 4},
                new Integer[]{2, 4, 4, 5},
                new Integer[]{3, 4, 3, 2},
                new Integer[]{5, 3, 2, 3}
        );

        NumberGeneticAlgorithm geneticAlgorithm = new NumberGeneticAlgorithm();
        List<Integer[]> newPopulation = geneticAlgorithm.execute(initialPopulation, 0.01f);

        System.out.println(Arrays.toString(newPopulation.get(0)));
        System.out.println(geneticAlgorithm.getNumberOfGenerations());
    }
}
