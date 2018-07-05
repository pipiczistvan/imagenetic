package imagenetic.common.util;

import org.joml.Vector3i;

public class Vector3iUtil {

    private Vector3iUtil() {
    }

    public static Vector3i interpolatePosition(final Vector3i source, final Vector3i destination, final float progression) {
        int x = (int) (source.x + (destination.x - source.x) * progression);
        int y = (int) (source.y + (destination.y - source.y) * progression);
        int z = (int) (source.z + (destination.z - source.z) * progression);
        return new Vector3i(x, y, z);
    }
}
