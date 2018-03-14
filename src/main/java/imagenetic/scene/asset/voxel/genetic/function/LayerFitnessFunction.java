package imagenetic.scene.asset.voxel.genetic.function;

import imagenetic.common.algorithm.genetic.function.FitnessFunction;
import imagenetic.common.algorithm.image.ImageProcessor;
import imagenetic.gui.common.api.settings.MultiCheckChangedListener;
import imagenetic.scene.asset.voxel.genetic.entity.LayerChromosome;
import imagenetic.scene.asset.voxel.genetic.entity.VoxelChromosome;
import puppeteer.annotation.premade.Component;

import java.awt.image.BufferedImage;

import static imagenetic.common.Config.VOXEL_RESOLUTION;
import static imagenetic.common.util.NumberUtil.nullSafeDivide;

@Component
public class LayerFitnessFunction implements FitnessFunction<LayerChromosome>, MultiCheckChangedListener {

    private boolean multiCheck;
    private int maxColorValue;
    private int[][] originalPixelValues;
    private int width;
    private int height;

    @Override
    public Float calculate(final LayerChromosome element) {
        if (originalPixelValues == null) {
            return 0.0f;
        }

        int[][] pixelValues1 = new int[originalPixelValues.length][originalPixelValues[0].length];
        int[][] pixelValues2 = new int[originalPixelValues.length][originalPixelValues[0].length];
        for (int i = 0; i < originalPixelValues.length; i++) {
            for (int j = 0; j < originalPixelValues[i].length; j++) {
                pixelValues1[i][j] = originalPixelValues[i][j];
                pixelValues2[i][j] = originalPixelValues[i][j];
            }
        }

        float fitnessOfSticks = 0;
        for (VoxelChromosome voxelChromosome : element.voxelChromosomes) {
            fitnessOfSticks += fitnessOfStick(pixelValues1, pixelValues2, voxelChromosome);
        }

        element.voxelChromosomes.sort(VoxelChromosome::compareTo);

        return fitnessOfSticks / element.voxelChromosomes.size();
    }

    public BufferedImage prepareImage(final BufferedImage image) {
        BufferedImage preparedImage = prepareImage(image, VOXEL_RESOLUTION);

        this.width = preparedImage.getWidth();
        this.height = preparedImage.getHeight();

        this.originalPixelValues = getColorValues(preparedImage);

        return preparedImage;
    }

    @Override
    public void onMultiCheckChanged(final boolean checked) {
        this.multiCheck = checked;
    }

    private float fitnessOfStick(int[][] pixelValues1, int[][] pixelValues2, VoxelChromosome element) {
        if (multiCheck) {
            float frontFitness = calculate(pixelValues1, element.position.x, element.position.y);
            float sideFitness = calculate(pixelValues2, element.position.z, element.position.y);
            element.fitness = overall(frontFitness, sideFitness) * ratio(frontFitness, sideFitness);
        } else {
            if (element.position.z != 0) {
                element.fitness = 0;
            } else {
                element.fitness = calculate(pixelValues1, element.position.x, element.position.y);
            }
        }
        return element.fitness;
    }

    private float overall(float fitness1, float fitness2) {
        return (fitness1 + fitness2) / 2f;
    }

    private float ratio(final float fitness1, final float fitness2) {
        return fitness1 < fitness2 ? nullSafeDivide(fitness1, fitness2) : nullSafeDivide(fitness2, fitness1);
    }

    private float calculate(final int[][] pixelValues, final int posX, final int posY) {
        int x = posX + width / 2;
        int y = posY + height / 2;

        if (x < 0 || x >= width || y < 0 || y >= height) {
            return 0;
        }

        float value = pixelValues[x][y] / (float) maxColorValue;
        pixelValues[x][y] = 0;

        return value;
    }

    private BufferedImage prepareImage(final BufferedImage originalImage, final int maxSize) {
        int currentWidth = originalImage.getWidth();
        int currentHeight = originalImage.getHeight();

        if (currentWidth > currentHeight) {
            currentHeight = maxSize * currentHeight / currentWidth;
            currentWidth = maxSize;
        } else {
            currentWidth = maxSize * currentWidth / currentHeight;
            currentHeight = maxSize;
        }

        return ImageProcessor.loadImage(originalImage)
                .resize(currentWidth, currentHeight)
                .toGrayScale()
                .toNegative()
                .get();
    }

    private int[][] getColorValues(final BufferedImage image) {
        maxColorValue = 0;
        int[][] colorValues = new int[image.getWidth()][image.getHeight()];
        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                colorValues[x][y] = getPixelValue(image.getRGB(x, y)) > 0 ? 1 : 0;
                if (maxColorValue < colorValues[x][y]) {
                    maxColorValue = colorValues[x][y];
                }
            }
        }

        return colorValues;
    }

    public static int getPixelValue(final int p) {
        int a = (p >> 24) & 0xff;
        int r = (p >> 16) & 0xff;
        int g = (p >> 8) & 0xff;
        int b = p & 0xff;

        return a < 255 ? 0 : (r + g + b) / 3;
    }
}
