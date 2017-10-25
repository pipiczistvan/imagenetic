package matcher;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;

import java.awt.image.BufferedImage;

public class BufferedImageMatcher extends BaseMatcher<BufferedImage> {

    private final BufferedImage actualImage;

    public BufferedImageMatcher(BufferedImage actualImage) {
        this.actualImage = actualImage;
    }

    @Override
    public boolean matches(Object o) {
        BufferedImage expectedImage = (BufferedImage) o;

        int width = actualImage.getWidth();
        int height = actualImage.getHeight();

        if (expectedImage.getWidth() != width || expectedImage.getHeight() != height) {
            return false;
        }

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (actualImage.getRGB(x, y) != expectedImage.getRGB(x, y)) {
                    return false;
                }
            }
        }

        return true;
    }

    @Override
    public void describeTo(Description description) {
        description.appendValue(actualImage);
    }

}
