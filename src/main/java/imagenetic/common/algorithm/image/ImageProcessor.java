package imagenetic.common.algorithm.image;

import imagenetic.common.exception.ImageneticException;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageProcessor {

    private BufferedImage image;

    private ImageProcessor(BufferedImage image) {
        this.image = image;
    }

    public static ImageProcessor loadImage(BufferedImage image) {
        return new ImageProcessor(image);
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
        process((a, r, g, b) -> {
            int avg = (r + g + b) / 3;

            return (a << 24) | (avg << 16) | (avg << 8) | avg;
        });

        return this;
    }

    public ImageProcessor toNegative() {
        process((a, r, g, b) -> {
            int nr = 255 - r;
            int ng = 255 - g;
            int nb = 255 - b;

            return (a << 24) | (nr << 16) | (ng << 8) | nb;
        });

        return this;
    }

    public ImageProcessor resize(int width, int height) {
        final BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        final Graphics2D graphics2D = bufferedImage.createGraphics();
        graphics2D.setComposite(AlphaComposite.Src);

//        graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
//        graphics2D.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
//        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics2D.drawImage(image, 0, 0, width, height, null);
        graphics2D.dispose();

        image = bufferedImage;

        return this;
    }

    public BufferedImage get() {
        return image;
    }

    private void process(ImageProcessorFunction processor) {
        int width = image.getWidth();
        int height = image.getHeight();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int p = image.getRGB(x, y);

                int a = (p >> 24) & 0xff;
                int r = (p >> 16) & 0xff;
                int g = (p >> 8) & 0xff;
                int b = p & 0xff;

                p = processor.process(a, r, g, b);
                image.setRGB(x, y, p);
            }
        }
    }

    private interface ImageProcessorFunction {
        int process(int a, int r, int g, int b);
    }

}
