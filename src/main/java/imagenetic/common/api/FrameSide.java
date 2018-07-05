package imagenetic.common.api;

public interface FrameSide {

    void updateLabels(final int numberOfGenerations, final float averageFitness, final float bestFitness, final int generationsPerSec, final int entityCount);

    int getX();

    int getY();

    int getCanvasX();

    int getCanvasY();

    int getCanvasWidth();

    int getCanvasHeight();
}
