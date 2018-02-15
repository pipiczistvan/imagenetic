package imagenetic.common.api;

import java.awt.image.BufferedImage;

public interface SceneSide {

    boolean isAlgorithmPaused();

    void setAlgorithmStatus(final boolean paused);

    void setAlgorithmSpeed(final int speed);

    void setPopulationCount(final int populationCount);

    void showAll(final boolean show);

    void setImage(final BufferedImage image);

    int getNumberOfGenerations();

    float getAverageFitness();

    float getBestFitness();
}
