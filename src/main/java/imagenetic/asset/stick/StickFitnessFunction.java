package imagenetic.asset.stick;

import imagenetic.algorithm.function.FitnessFunction;
import imagenetic.image.BresenhamAlgorithm;
import imagenetic.image.ImageProcessor;
import org.joml.Vector2i;

import java.awt.image.BufferedImage;
import java.util.List;

public class StickFitnessFunction implements FitnessFunction<StickChromosome> {

    private final int[][] colorValues;
    private final Vector2i[][] grid;
    private final int width;
    private final int height;

    public StickFitnessFunction(int maxSize) {
        BufferedImage eiffelImage = prepareImage(maxSize);

        this.width = eiffelImage.getWidth();
        this.height = eiffelImage.getHeight();

        this.colorValues = getColorValues(eiffelImage);
        this.grid = createGrid();
    }

    @Override
    public Float calculate(StickChromosome element) {
        double length = Math.abs(Math.cos(Math.toRadians(element.rotation.x))) * element.scale.y;
        double rotation = Math.toRadians(element.rotation.z);
        double x = length * Math.sin(rotation) / 2;
        double y = length * Math.cos(rotation) / 2;

        //todo: if the element is on and edge, these coordinates can be out of bound
        int x0 = (int) (element.position.x + width / 2 + x);
        int y0 = (int) (element.position.y + height / 2 + y);
        int x1 = (int) (element.position.x + width / 2 - x);
        int y1 = (int) (element.position.y + height / 2 - y);

        List<Vector2i> line = BresenhamAlgorithm.findLine(grid, x0, y0, x1, y1);

        // make it camera view based

        return 0f;
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

    private BufferedImage prepareImage(int maxSize) {
        String eiffelPath = getClass().getResource("/images/eiffel.png").getFile();
        BufferedImage image = ImageProcessor.loadImage(eiffelPath).get();

        int width = image.getWidth();
        int height = image.getHeight();

        if (width > height) {
            height = maxSize * height / width;
            width = maxSize;
        } else {
            width = maxSize * width / height;
            height = maxSize;
        }

        return ImageProcessor.loadImage(image).resize(width, height).get();
    }

    private int[][] getColorValues(BufferedImage image) {
        int[][] colorValues = new int[image.getHeight()][image.getWidth()];
        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                colorValues[y][x] = image.getRGB(x, y) & 0xff;
            }
        }

        return colorValues;
    }
}
