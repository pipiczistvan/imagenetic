package imagenetic.scene.asset.stick.genetic.function;

import imagenetic.common.algorithm.genetic.function.FitnessFunction;
import imagenetic.common.algorithm.image.BresenhamAlgorithm;
import imagenetic.common.algorithm.image.ImageProcessor;
import imagenetic.scene.asset.stick.genetic.entity.StickChromosome;
import org.joml.Vector2i;

import java.awt.image.BufferedImage;
import java.util.List;

public class StickFitnessFunction implements FitnessFunction<StickChromosome> {

    private static final float MAX_COLOR_VALUE = 255f;

    private final int[][] pixelValues;
    private final Vector2i[][] pixelGrid;
    private final int width;
    private final int height;

    public StickFitnessFunction(String imagePath, int maxSize) {
        BufferedImage image = prepareImage(imagePath, maxSize);

        this.width = image.getWidth();
        this.height = image.getHeight();

        this.pixelValues = getColorValues(image);
        this.pixelGrid = createGrid();
    }

    @Override
    public Float calculate(StickChromosome element) {
        double length = Math.abs(Math.cos(Math.toRadians(element.rotation.x))) * element.scale.y;
        double rotation = Math.toRadians(element.rotation.z);
        double x = length * Math.sin(rotation) / 2;
        double y = length * Math.cos(rotation) / 2;

        int x0 = positiveMax((int) (element.position.x + width / 2d + x), width - 1);
        int y0 = positiveMax((int) (element.position.y + height / 2d + y), height - 1);
        int x1 = positiveMax((int) (element.position.x + width / 2d - x), width - 1);
        int y1 = positiveMax((int) (element.position.y + height / 2d - y), height - 1);

        List<Vector2i> line = BresenhamAlgorithm.findLine(pixelGrid, x0, y0, x1, y1);

        int sum = line.stream().map(point -> pixelValues[point.x][point.y]).reduce(0, Integer::sum);
        float max = MAX_COLOR_VALUE * line.size();

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

    private BufferedImage prepareImage(String imagePath, int maxSize) {
        BufferedImage image = ImageProcessor.loadImage(imagePath)
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

    private int[][] getColorValues(BufferedImage image) {
        int[][] colorValues = new int[image.getWidth()][image.getHeight()];
        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                colorValues[x][y] = image.getRGB(x, y) & 0xff;
            }
        }

        return colorValues;
    }

    private static int positiveMax(int value, int max) {
        return Math.max(0, Math.min(max, value));
    }
}
