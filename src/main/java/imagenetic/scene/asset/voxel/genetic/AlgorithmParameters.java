package imagenetic.scene.asset.voxel.genetic;

import java.awt.image.BufferedImage;

import static imagenetic.scene.asset.voxel.genetic.function.LayerFitnessFunction.getPixelValue;

public class AlgorithmParameters {

    protected boolean changed = false;
    protected BufferedImage image;
    public int relevantPixelCount = 0;

    public void setImage(final BufferedImage image) {
        this.image = image;
        this.changed = true;

        this.relevantPixelCount = 0;
        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                this.relevantPixelCount += getPixelValue(image.getRGB(x, y)) > 0 ? 1 : 0;
            }
        }
    }

    public BufferedImage getImage() {
        return image;
    }

    public boolean hasChanged() {
        return changed;
    }
}
