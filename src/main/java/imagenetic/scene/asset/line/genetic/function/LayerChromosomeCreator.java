package imagenetic.scene.asset.line.genetic.function;

import imagenetic.common.algorithm.genetic.function.ChromosomeCreator;
import imagenetic.scene.asset.line.genetic.AlgorithmParameters;
import imagenetic.scene.asset.line.genetic.entity.LayerChromosome;
import imagenetic.scene.asset.line.genetic.entity.LineChromosome;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.List;

public class LayerChromosomeCreator implements ChromosomeCreator<LayerChromosome> {

    private static final float STICK_SCALE = 2.5f;

    private final AlgorithmParameters parameters;

    public LayerChromosomeCreator(final AlgorithmParameters parameters) {
        this.parameters = parameters;
    }

    @Override
    public LayerChromosome create() {
        return new LayerChromosome(createLineChromosomes());
    }

    private List<LineChromosome> createLineChromosomes() {
        List<LineChromosome> lineChromosomes = new ArrayList<>();
        for (int i = 0; i < parameters.getPopulationSize(); i++) {
            lineChromosomes.add(new LineChromosome(
                    new Vector3f(),
                    new Vector3f(),
                    new Vector3f(
                            parameters.getLineThickness() * STICK_SCALE / 10f,
                            parameters.getLineLength() * STICK_SCALE,
                            parameters.getLineThickness() * STICK_SCALE / 10f)
            ));
        }

        return lineChromosomes;
    }
}
