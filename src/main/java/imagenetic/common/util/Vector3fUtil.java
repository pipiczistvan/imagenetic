package imagenetic.common.util;

import org.joml.Vector3f;

public class Vector3fUtil {

    private Vector3fUtil() {
    }

    public static Vector3f interpolatePosition(final Vector3f source, final Vector3f destination, final float progression) {
        float x = source.x + (destination.x - source.x) * progression;
        float y = source.y + (destination.y - source.y) * progression;
        float z = source.z + (destination.z - source.z) * progression;
        return new Vector3f(x, y, z);
    }
}
