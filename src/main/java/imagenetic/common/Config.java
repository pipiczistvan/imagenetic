package imagenetic.common;

public class Config {

    public static final int MIN_POPULATION_COUNT = 2;
    public static final int MAX_POPULATION_COUNT = 10;
    public static final int DEFAULT_POPULATION_COUNT = (MIN_POPULATION_COUNT + MAX_POPULATION_COUNT) / 2;

    public static final int MIN_SPEED = 1;
    public static final int MAX_SPEED = 10;
    public static final int DEFAULT_SPEED = (MIN_SPEED + MAX_SPEED) / 2;

    private Config() {
    }
}
