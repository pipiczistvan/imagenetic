package imagenetic.scene.asset.line.genetic.entity;

import org.joml.Vector3f;

public class LineChromosome implements Comparable<LineChromosome> {

    public float fitness = 0;
    public final Vector3f position;
    public final Vector3f rotation;
    public final Vector3f scale;

    public LineChromosome(final Vector3f position, final Vector3f rotation, final Vector3f scale) {
        this.position = position;
        this.rotation = rotation;
        this.scale = scale;
    }

    public LineChromosome(final Vector3f position, final Vector3f rotation, final Vector3f scale, final float fitness) {
        this.position = position;
        this.rotation = rotation;
        this.scale = scale;
        this.fitness = fitness;
    }

    public LineChromosome(final LineChromosome lineChromosome) {
        this(lineChromosome.position, lineChromosome.rotation, lineChromosome.scale, lineChromosome.fitness);
    }

    @Override
    public int compareTo(final LineChromosome other) {
        return Float.compare(other.fitness, fitness);
    }
}
