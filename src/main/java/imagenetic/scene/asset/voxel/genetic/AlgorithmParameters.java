package imagenetic.scene.asset.voxel.genetic;

import java.awt.image.BufferedImage;

public class AlgorithmParameters {

    protected boolean changed = false;
    protected BufferedImage image;
    protected int maxSize;
    protected int populationSize;
    protected float lineLength;
    protected float lineThickness;

    public AlgorithmParameters(final int maxSize, final int populationSize,
                               final float lineLength, final float lineThickness) {
        this.maxSize = maxSize;
        this.populationSize = populationSize;

        this.lineLength = lineLength;
        this.lineThickness = lineThickness;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
        this.changed = true;
    }

    public void setMaxSize(int maxSize) {
        this.maxSize = maxSize;
        this.changed = true;
    }

    public void setPopulationSize(int populationSize) {
        this.populationSize = populationSize;
        this.changed = true;
    }

    public void setLineLength(float lineLength) {
        this.lineLength = lineLength;
        this.changed = true;
    }

    public void setLineThickness(float lineThickness) {
        this.lineThickness = lineThickness;
        this.changed = true;
    }

    public BufferedImage getImage() {
        return image;
    }

    public int getMaxSize() {
        return maxSize;
    }

    public int getPopulationSize() {
        return populationSize;
    }

    public float getLineLength() {
        return lineLength;
    }

    public float getLineThickness() {
        return lineThickness;
    }

    public boolean hasChanged() {
        return changed;
    }
}
