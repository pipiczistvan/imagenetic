package imagenetic.common;

import piengine.core.base.type.property.IntegerProperty;

public class Properties {

    public static final IntegerProperty THREAD_COUNT = new IntegerProperty("imagenetic.thread.count");

    private Properties() {
    }

    public static void preload() {
    }
}
