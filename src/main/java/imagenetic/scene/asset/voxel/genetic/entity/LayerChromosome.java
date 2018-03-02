package imagenetic.scene.asset.voxel.genetic.entity;

import java.util.List;

public class LayerChromosome {

    public final List<VoxelChromosome> voxelChromosomes;

    public LayerChromosome(final List<VoxelChromosome> voxelChromosomes) {
        this.voxelChromosomes = voxelChromosomes;
    }
}
