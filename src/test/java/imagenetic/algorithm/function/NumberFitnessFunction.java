package imagenetic.algorithm.function;

public class NumberFitnessFunction implements FitnessFunction<Integer[]> {

    private static final Integer[] OPTIMUM = {1, 1, 1, 1};

    @Override
    public Float calculate(Integer[] element) {
        float fitness = 0;

        for (int i = 0; i < OPTIMUM.length; i++) {
            float value = element[i].equals(OPTIMUM[i]) ? 1f : 0f;
            fitness += value / OPTIMUM.length;
        }

        return fitness;
    }
}
