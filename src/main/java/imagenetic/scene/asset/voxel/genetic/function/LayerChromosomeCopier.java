package imagenetic.scene.asset.voxel.genetic.function;

import imagenetic.common.algorithm.genetic.function.ChromosomeCopier;
import imagenetic.scene.asset.voxel.genetic.entity.LayerChromosome;
import imagenetic.scene.asset.voxel.genetic.entity.VoxelChromosome;
import org.joml.Vector3i;

import java.util.List;
import java.util.stream.Collectors;

public class LayerChromosomeCopier implements ChromosomeCopier<LayerChromosome> {

    @Override
    public LayerChromosome copy(final LayerChromosome genotype) {
        List<VoxelChromosome> voxelChromosomes = genotype.voxelChromosomes
                .stream()
                .map(c -> new VoxelChromosome(
                        new Vector3i(c.position))
                )
                .collect(Collectors.toList());

        return new LayerChromosome(voxelChromosomes);
    }
}
