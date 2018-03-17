package imagenetic.common.util;

import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;

public class ImageUtil {

    private ImageUtil() {
    }

    public static BufferedImage copyImage(final BufferedImage bi) {
        ColorModel cm = bi.getColorModel();
        boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
        WritableRaster raster = bi.copyData(bi.getRaster().createCompatibleWritableRaster());
        return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
    }
}
