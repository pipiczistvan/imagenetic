package imagenetic.asset.stick;

import imagenetic.algorithm.function.FitnessFunction;
import org.joml.Vector2f;

public class StickFitnessFunction implements FitnessFunction<StickChromosome> {

    private final int[][] colorValues;
    private final int halfWidth;
    private final int halfHeight;

    public StickFitnessFunction(int[][] colorValues) {
        this.colorValues = colorValues;

        halfHeight = colorValues.length / 2;
        halfWidth = colorValues[0].length / 2;
    }

    @Override
    public Float calculate(StickChromosome element) {
        float length = (float) Math.abs(Math.cos(Math.toRadians(element.rotation.x))) * element.scale.y;
        double rotation = Math.toRadians(element.rotation.z);

        float x = length * (float) Math.sin(rotation) / 2;
        float y = length * (float) Math.cos(rotation) / 2;

        Vector2f point1 = new Vector2f(x, y).add(element.position.x, element.position.y).add(halfWidth, halfHeight);
        Vector2f point2 = new Vector2f(-x, -y).add(element.position.x, element.position.y).add(halfWidth, halfHeight);

        // make it camera view based

        return length;
    }
}
