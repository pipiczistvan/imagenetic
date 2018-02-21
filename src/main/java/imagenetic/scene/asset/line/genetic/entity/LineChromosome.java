package imagenetic.scene.asset.line.genetic.entity;

import org.joml.Vector3f;

public class LineChromosome implements Comparable<LineChromosome> {

    public float fitness = 0;
    public final Vector3f position;
    public final Vector3f rotation;
    public final Vector3f scale;

    public LineChromosome(Vector3f position, Vector3f rotation, Vector3f scale) {
        this.position = position;
        this.rotation = rotation;
        this.scale = scale;
    }

    public LineChromosome(Vector3f position, Vector3f rotation, Vector3f scale, float fitness) {
        this.position = position;
        this.rotation = rotation;
        this.scale = scale;
        this.fitness = fitness;
    }

    @Override
    public int compareTo(LineChromosome other) {
        return Float.compare(other.fitness, fitness);
    }
}
