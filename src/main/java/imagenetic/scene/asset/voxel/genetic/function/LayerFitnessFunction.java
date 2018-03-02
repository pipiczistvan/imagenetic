package imagenetic.scene.asset.voxel.genetic.function;

import imagenetic.common.algorithm.genetic.function.FitnessFunction;
import imagenetic.common.algorithm.image.ImageProcessor;
import imagenetic.scene.asset.voxel.genetic.AlgorithmParameters;
import imagenetic.scene.asset.voxel.genetic.entity.LayerChromosome;
import imagenetic.scene.asset.voxel.genetic.entity.VoxelChromosome;
import piengine.core.base.api.Initializable;

import java.awt.image.BufferedImage;

import static imagenetic.common.util.NumberUtil.nullSafeDivide;

public class LayerFitnessFunction implements FitnessFunction<LayerChromosome>, Initializable {

    private final AlgorithmParameters parameters;

    private int maxColorValue;
    private int[][] originalPixelValues;
    private int width;
    private int height;

    public LayerFitnessFunction(final AlgorithmParameters parameters) {
        this.parameters = parameters;
    }

    @Override
    public void initialize() {
        BufferedImage image = prepareImage(parameters.getImage(), parameters.getMaxSize());

        this.width = image.getWidth();
        this.height = image.getHeight();

        this.originalPixelValues = getColorValues(image);
    }

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

    private float fitnessOfStick(int[][] pixelValues1,int[][] pixelValues2, VoxelChromosome element) {
        float fitness1 = calculate(pixelValues1, element.position.x, element.position.y);
        float fitness2 = fitness1;
//        float fitness2 = calculate(pixelValues2, element.position.z, element.position.y);

        element.fitness = overall(fitness1, fitness2) * ratio(fitness1, fitness2);
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
        BufferedImage image = ImageProcessor.loadImage(originalImage)
                .toGrayScale()
                .toNegative()
                .get();

        int currentWidth = image.getWidth();
        int currentHeight = image.getHeight();

        if (currentWidth > currentHeight) {
            currentHeight = maxSize * currentHeight / currentWidth;
            currentWidth = maxSize;
        } else {
            currentWidth = maxSize * currentWidth / currentHeight;
            currentHeight = maxSize;
        }

        return ImageProcessor.loadImage(image).resize(currentWidth, currentHeight).get();
    }

    private int[][] getColorValues(final BufferedImage image) {
        maxColorValue = 0;
        int[][] colorValues = new int[image.getWidth()][image.getHeight()];
        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                colorValues[x][y] = image.getRGB(x, y) & 0xff;
                if (maxColorValue < colorValues[x][y]) {
                    maxColorValue = colorValues[x][y];
                }
            }
        }

        return colorValues;
    }
}
