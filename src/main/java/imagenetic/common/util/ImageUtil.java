package imagenetic.common.util;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ImageUtil {

    private ImageUtil() {
    }

    public static BufferedImage copyImage(final BufferedImage source) {
        BufferedImage b = new BufferedImage(source.getWidth(), source.getHeight(), source.getType());
        Graphics g = b.getGraphics();
        g.drawImage(source, 0, 0, null);
        g.dispose();
        return b;
    }
}
