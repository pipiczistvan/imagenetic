package imagenetic.asset.stick;

import imagenetic.algorithm.function.FitnessFunction;
import org.joml.Vector2f;

public class StickFitnessFunction implements FitnessFunction<StickChromosome> {

    private final int[][] colorValues;

    public StickFitnessFunction(int[][] colorValues) {
        this.colorValues = colorValues;
    }

    @Override
    public Float calculate(StickChromosome element) {
        float length = (float) Math.abs(Math.cos(Math.toRadians(element.rotation.x))) * element.scale.y;
        float rotation = element.rotation.z;
        Vector2f position = new Vector2f(element.position.x, element.position.y);



        // How many pixels does it cover?
        // make it camera view based

        return length;
    }
}
