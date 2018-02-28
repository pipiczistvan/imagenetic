package imagenetic.scene.asset.line.genetic.function;

import imagenetic.common.algorithm.genetic.function.FitnessFunction;
import imagenetic.common.algorithm.image.BresenhamAlgorithm;
import imagenetic.common.algorithm.image.ImageProcessor;
import imagenetic.scene.asset.line.genetic.AlgorithmParameters;
import imagenetic.scene.asset.line.genetic.entity.LayerChromosome;
import imagenetic.scene.asset.line.genetic.entity.LineChromosome;
import org.joml.Vector2f;
import org.joml.Vector2i;
import piengine.core.base.api.Initializable;

import java.awt.image.BufferedImage;
import java.util.List;

import static imagenetic.common.util.NumberUtil.nullSafeDivide;
import static imagenetic.common.util.NumberUtil.positiveMax;

public class LayerFitnessFunction implements FitnessFunction<LayerChromosome>, Initializable {

    private final AlgorithmParameters parameters;

    private int maxColorValue;
    private int[][] originalPixelValues;
    private Vector2i[][] pixelGrid;
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
        this.pixelGrid = createGrid();
    }

    @Override
    public Float calculate(final LayerChromosome element) {
        if (originalPixelValues == null) {
            return 0.0f;
        }

        int[][] pixelValues = new int[originalPixelValues.length][originalPixelValues[0].length];
        for (int i = 0; i < originalPixelValues.length; i++) {
            for (int j = 0; j < originalPixelValues[i].length; j++) {
                pixelValues[i][j] = originalPixelValues[i][j];
            }
        }

        float fitnessOfSticks = 0;
        for (LineChromosome lineChromosome : element.lineChromosomes) {
            fitnessOfSticks += fitnessOfStick(pixelValues, lineChromosome);
        }

        element.lineChromosomes.sort(LineChromosome::compareTo);

        return fitnessOfSticks / element.lineChromosomes.size();
    }

    private float fitnessOfStick(int[][] pixelValues, LineChromosome element) {
        float fitness1 = calculate(pixelValues, new Vector2f(element.position.x, element.position.y), new Vector2f(element.rotation.x, element.rotation.z), element.scale.y);
        float fitness2 = fitness1;
//        float fitness2 = calculate(new Vector2f(element.position.z, element.position.y), new Vector2f(element.rotation.z, element.rotation.x), element.scale.y);

        element.fitness = overall(fitness1, fitness2) * ratio(fitness1, fitness2);
        return element.fitness;
    }

    private float overall(float fitness1, float fitness2) {
        return (fitness1 + fitness2) / 2f;
    }

    private float ratio(final float fitness1, final float fitness2) {
        return fitness1 < fitness2 ? nullSafeDivide(fitness1, fitness2) : nullSafeDivide(fitness2, fitness1);
    }

    private float calculate(final int[][] pixelValues, final Vector2f position, final Vector2f rotation, final float scale) {
        double length = Math.abs(Math.cos(Math.toRadians(rotation.x))) * scale;
        double rot = Math.toRadians(rotation.y);
        double x = length * Math.sin(rot) / 2;
        double y = length * Math.cos(rot) / 2;

        int x0 = positiveMax((int) (position.x + width / 2d + x), width - 1);
        int y0 = positiveMax((int) (position.y + height / 2d + y), height - 1);
        int x1 = positiveMax((int) (position.x + width / 2d - x), width - 1);
        int y1 = positiveMax((int) (position.y + height / 2d - y), height - 1);

        List<Vector2i> line = BresenhamAlgorithm.findLine(pixelGrid, x0, y0, x1, y1);

        int sum = 0;
        for (Vector2i point : line) {
            sum += pixelValues[point.x][point.y];
            pixelValues[point.x][point.y] = 0;
        }
        float max = maxColorValue * (scale + 1);

        return sum / max;
    }

    private Vector2i[][] createGrid() {
        Vector2i[][] grid = new Vector2i[width][height];
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                grid[i][j] = new Vector2i(i, j);
            }
        }

        return grid;
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
