package imagenetic.common.util;

public class NumberUtil {

    private NumberUtil() {
    }

    public static float nullSafeDivide(final float dividend, final float divisor) {
        if (divisor == 0f) {
            return 0f;
        }
        return dividend / divisor;
    }

    public static int positiveMax(int value, int max) {
        return Math.max(0, Math.min(max, value));
    }
}
