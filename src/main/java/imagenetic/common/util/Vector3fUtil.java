package imagenetic.common.util;

import org.joml.Quaternionf;
import org.joml.Vector3f;

public class Vector3fUtil {

    private static final Quaternionf TEMP_QUATERNION_1 = new Quaternionf();
    private static final Quaternionf TEMP_QUATERNION_2 = new Quaternionf();
    private static final Quaternionf TEMP_QUATERNION_3 = new Quaternionf();
    private static final Vector3f TEMP_VECTOR_1 = new Vector3f();

    private Vector3fUtil() {
    }

    public static Vector3f interpolatePosition(final Vector3f source, final Vector3f destination, final float progression) {
        float x = source.x + (destination.x - source.x) * progression;
        float y = source.y + (destination.y - source.y) * progression;
        float z = source.z + (destination.z - source.z) * progression;
        return new Vector3f(x, y, z);
    }

    public static Vector3f interpolateRotation(final Vector3f source, final Vector3f destination, final float progression) {
        resetTempVariables();

        Vector3f sourceRadians = degreesToRadians(source);
        TEMP_QUATERNION_1.rotateXYZ(sourceRadians.x, sourceRadians.y, sourceRadians.z);
        Vector3f destinationRadians = degreesToRadians(destination);
        TEMP_QUATERNION_2.rotateXYZ(destinationRadians.x, destinationRadians.y, destinationRadians.z);

        TEMP_QUATERNION_1.slerp(TEMP_QUATERNION_2, progression, TEMP_QUATERNION_3);

        return radiansToDegrees(TEMP_QUATERNION_3.getEulerAnglesXYZ(TEMP_VECTOR_1));
    }

    private static Vector3f degreesToRadians(final Vector3f rotation) {
        return new Vector3f((float) Math.toRadians(rotation.x), (float) Math.toRadians(rotation.y), (float) Math.toRadians(rotation.z));
    }

    private static Vector3f radiansToDegrees(final Vector3f rotation) {
        return new Vector3f((float) Math.toDegrees(rotation.x), (float) Math.toDegrees(rotation.y), (float) Math.toDegrees(rotation.z));
    }

    private static final void resetTempVariables() {
        TEMP_QUATERNION_1.identity();
        TEMP_QUATERNION_2.identity();
        TEMP_QUATERNION_3.identity();
        TEMP_VECTOR_1.set(0);
    }
}
