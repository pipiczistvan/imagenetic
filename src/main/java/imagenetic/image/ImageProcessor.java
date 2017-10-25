package imagenetic.image;

import imagenetic.exception.ImageneticException;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageProcessor {

    private final BufferedImage image;

    private ImageProcessor(BufferedImage image) {
        this.image = image;
    }

    public static ImageProcessor loadImage(String imageFile) {
        File file = new File(imageFile);
        BufferedImage image;
        try {
            image = ImageIO.read(file);
        } catch (IOException e) {
            throw new ImageneticException("Could not parse image file!", e);
        }

        return new ImageProcessor(image);
    }

    public ImageProcessor toGrayScale() {
        int width = image.getWidth();
        int height = image.getHeight();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int p = image.getRGB(x, y);

                int a = (p >> 24) & 0xff;
                int r = (p >> 16) & 0xff;
                int g = (p >> 8) & 0xff;
                int b = p & 0xff;

                int avg = (r + g + b) / 3;

                p = (a << 24) | (avg << 16) | (avg << 8) | avg;

                image.setRGB(x, y, p);
            }
        }

        return this;
    }

    public BufferedImage get() {
        return image;
    }

}
