package imagenetic.common;

public class Config {

    // UI
    public static final int MIN_POPULATION_COUNT = 2;
    public static final int MAX_POPULATION_COUNT = 10;
    public static final int DEF_POPULATION_COUNT = 6;

    public static final int MIN_POPULATION_SIZE = 50;
    public static final int MAX_POPULATION_SIZE = 500;
    public static final int DEF_POPULATION_SIZE = 300;

    public static final int MIN_ENTITY_THICKNESS = 5;
    public static final int MAX_ENTITY_THICKNESS = 15;
    public static final int DEF_ENTITY_THICKNESS = 10;

    public static final int MIN_ENTITY_LENGTH = 5;
    public static final int MAX_ENTITY_LENGTH = 15;
    public static final int DEF_ENTITY_LENGTH = 10;

    public static final float MIN_ENTITY_THRESHOLD = -0.01f;
    public static final float MAX_ENTITY_THRESHOLD = 1.01f;
    public static final float DEF_ENTITY_THRESHOLD = 0.0f;

    public static final int MIN_SPEED = 1;
    public static final int MAX_SPEED = 10;
    public static final int DEF_SPEED = 6;

    public static final boolean DEF_SHOW_ALL = false;

    // Scene
    public static final float MIN_SCALE = 0.1f;
    public static final float DEF_SCALE = 1.0f;
    public static final float MAX_SCALE = 3.0f;
    public static final int INTERPOLATION_THRESHOLD = 10;

    private Config() {
    }
}
