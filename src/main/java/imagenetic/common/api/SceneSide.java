package imagenetic.common.api;

import java.awt.image.BufferedImage;

public interface SceneSide {

    boolean isAlgorithmPaused();

    void setAlgorithmStatus(final boolean paused);

    void setAlgorithmSpeed(final int speed);

    void setPopulationCount(final int populationCount);

    void setPopulationSize(final int populationSize);

    void showAll(final boolean show);

    void setImage(final BufferedImage image);

    int getNumberOfGenerations();

    float getAverageFitness();

    float getBestFitness();

    void setThreshold(final float threshold);

    void setThickness(final float thickness);

    void setLength(final float length);
}
