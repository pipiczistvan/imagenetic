package imagenetic.scene.asset.line.genetic.function;

import imagenetic.common.algorithm.genetic.function.ChromosomeCopier;
import imagenetic.scene.asset.line.genetic.entity.LayerChromosome;
import imagenetic.scene.asset.line.genetic.entity.LineChromosome;
import org.joml.Vector3f;

import java.util.List;
import java.util.stream.Collectors;

public class LayerChromosomeCopier implements ChromosomeCopier<LayerChromosome> {

    @Override
    public LayerChromosome copy(final LayerChromosome genotype) {
        List<LineChromosome> lineChromosomes = genotype.lineChromosomes
                .stream()
                .map(c -> new LineChromosome(
                        new Vector3f(c.position),
                        new Vector3f(c.rotation),
                        new Vector3f(c.scale))
                )
                .collect(Collectors.toList());

        return new LayerChromosome(lineChromosomes);
    }
}
