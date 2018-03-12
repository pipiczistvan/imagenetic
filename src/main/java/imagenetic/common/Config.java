package imagenetic.common;

import piengine.core.base.resource.ResourceLoader;

import static piengine.core.base.type.property.ApplicationProperties.get;
import static piengine.core.base.type.property.PropertyKeys.IMAGES_LOCATION;

public class Config {

    public static final ResourceLoader IMAGE_LOADER = new ResourceLoader(get(IMAGES_LOCATION), "png");

    // UI
    public static final int MIN_POPULATION_COUNT = 2;
    public static final int MAX_POPULATION_COUNT = 5;
    public static final int DEF_POPULATION_COUNT = 5;

    public static final double MIN_MUTATION_RATE = 0.0;
    public static final double MAX_MUTATION_RATE = 1.0;
    public static final double DEF_MUTATION_RATE = 0.5;

    public static final double MIN_ELITISM_RATE = 0.0;
    public static final double MAX_ELITISM_RATE = 1.0;
    public static final double DEF_ELITISM_RATE = 1.0;

    public static final double MIN_CRITERIA_RATE = 0.0;
    public static final double MAX_CRITERIA_RATE = 1.0;
    public static final double DEF_CRITERIA_RATE = 1.0;

    public static final int MIN_SPEED = 1;
    public static final int MAX_SPEED = 10;
    public static final int DEF_SPEED = 6;

    public static final boolean DEF_SHOW_ALL = false;
    public static final boolean DEF_SHOW_BEST = false;

    // Scene
    public static final float MIN_SCALE = 0.1f;
    public static final float DEF_SCALE = 1.0f;
    public static final float MAX_SCALE = 3.0f;
    public static final int INTERPOLATION_THRESHOLD = 10;

    public static final int SCENE_RESOLUTION = 600;
    public static final int VOXEL_RESOLUTION = 100;
    public static final float VOXEL_VISUAL_SCALE = SCENE_RESOLUTION / (float) VOXEL_RESOLUTION;

    public static final int POPULATION_SIZE = VOXEL_RESOLUTION * VOXEL_RESOLUTION;

    private Config() {
    }
}
