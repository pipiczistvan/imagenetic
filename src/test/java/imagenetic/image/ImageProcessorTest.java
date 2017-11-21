package imagenetic.image;

import matcher.BufferedImageMatcher;
import org.junit.Assert;
import org.junit.Test;

import java.awt.image.BufferedImage;

public class ImageProcessorTest {

    @Test
    public void convertToGrayScale() throws Exception {
        String normalFlag = getClass().getResource("/images/normalFlag.png").getFile();
        String grayScaleFlag = getClass().getResource("/images/grayScaleFlag.png").getFile();

        BufferedImage actualImage = ImageProcessor.loadImage(normalFlag)
                .toGrayScale()
                .get();

        BufferedImage expectedImage = ImageProcessor.loadImage(grayScaleFlag)
                .get();

        Assert.assertThat(expectedImage, new BufferedImageMatcher(actualImage));
    }

    @Test
    public void convertToNegative() throws Exception {
        String normalFlag = getClass().getResource("/images/normalFlag.png").getFile();
        String negativeFlag = getClass().getResource("/images/negativeFlag.png").getFile();

        BufferedImage actualImage = ImageProcessor.loadImage(normalFlag)
                .toNegative()
                .get();

        BufferedImage expectedImage = ImageProcessor.loadImage(negativeFlag)
                .get();

        Assert.assertThat(expectedImage, new BufferedImageMatcher(actualImage));
    }

    @Test
    public void resize() throws Exception {
        String normalFlag = getClass().getResource("/images/normalFlag.png").getFile();
        String scaledFlag = getClass().getResource("/images/scaledFlag.png").getFile();

        BufferedImage actualImage = ImageProcessor.loadImage(normalFlag)
                .resize(100, 200)
                .get();

        BufferedImage expectedImage = ImageProcessor.loadImage(scaledFlag)
                .get();

        Assert.assertThat(expectedImage, new BufferedImageMatcher(actualImage));
    }

}
