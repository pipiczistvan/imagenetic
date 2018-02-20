package imagenetic.scene.asset.line.genetic.function;

import imagenetic.common.algorithm.genetic.function.MutationOperator;
import imagenetic.scene.asset.line.genetic.AlgorithmParameters;
import imagenetic.scene.asset.line.genetic.entity.LayerChromosome;
import imagenetic.scene.asset.line.genetic.entity.LineChromosome;
import piengine.core.base.api.Initializable;

import java.util.Random;

public class LayerMutationOperator implements MutationOperator<LayerChromosome>, Initializable {

    private final AlgorithmParameters parameters;

    private int maxSize;
    private int halfMaxSize;
    private Random random;

    public LayerMutationOperator(final AlgorithmParameters parameters) {
        this.parameters = parameters;
    }

    @Override
    public void initialize() {
        this.maxSize = parameters.getMaxSize();
        this.halfMaxSize = maxSize / 2;
        this.random = new Random();
    }

    @Override
    public void mutate(final LayerChromosome genotype) {
        for (LineChromosome lineChromosome : genotype.lineChromosomes) {
            if (lineChromosome.fitness < 0.1f) {
                lineChromosome.position.set(
                        random.nextFloat() * maxSize - halfMaxSize,
                        random.nextFloat() * maxSize - halfMaxSize,
                        random.nextFloat() * maxSize - halfMaxSize
                );
            }
            if (lineChromosome.fitness < 0.4f) {
                lineChromosome.rotation.add(
                        (1.0f - lineChromosome.fitness) * (random.nextFloat() * 45),
                        (1.0f - lineChromosome.fitness) * (random.nextFloat() * 45),
                        (1.0f - lineChromosome.fitness) * (random.nextFloat() * 45)
                );
            }
        }
    }
}
