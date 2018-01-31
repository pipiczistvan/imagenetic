package imagenetic.scene.asset.stick.genetic.entity;

import org.joml.Vector3f;

public class StickChromosome implements Comparable<StickChromosome> {

    public float fitness = 0;
    public final Vector3f position;
    public final Vector3f rotation;
    public final Vector3f scale;

    public StickChromosome(Vector3f position, Vector3f rotation, Vector3f scale) {
        this.position = position;
        this.rotation = rotation;
        this.scale = scale;
    }

    @Override
    public int compareTo(StickChromosome other) {
        return Float.compare(other.fitness, fitness);
    }
}
