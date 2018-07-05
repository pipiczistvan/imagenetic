package imagenetic.scene.asset.voxel.genetic.entity;

import org.joml.Vector3i;

public class VoxelChromosome implements Comparable<VoxelChromosome> {

    public float fitness = 0;
    public final Vector3i position;

    public VoxelChromosome(final Vector3i position) {
        this.position = position;
    }

    public VoxelChromosome(final Vector3i position, final float fitness) {
        this.position = position;
        this.fitness = fitness;
    }

    public VoxelChromosome(final VoxelChromosome voxelChromosome) {
        this(new Vector3i(voxelChromosome.position), voxelChromosome.fitness);
    }

    @Override
    public int compareTo(final VoxelChromosome other) {
        return Float.compare(other.fitness, fitness);
    }
}
