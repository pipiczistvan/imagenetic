package imagenetic.common.api;

import java.awt.image.BufferedImage;

public interface SceneSide {

    void reset();

    boolean isAlgorithmPaused();

    boolean isInterpolated();

    void setAlgorithmStatus(final boolean paused);

    void setAlgorithmSpeed(final int speed);

    void setPopulationCount(final int populationCount);

    void setPopulationSize(final int populationSize);

    void showAll(final boolean show);

    void setImage(final BufferedImage image);

    void setThreshold(final double threshold);

    void setThickness(final float thickness);

    void setLength(final float length);

    void setInterpolated(final boolean interpolated);
}
