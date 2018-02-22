package imagenetic.gui.common;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.function.Consumer;

public class ImageChooser {

    private final Component parent;
    private final JFileChooser fileChooser;

    public ImageChooser(final Component parent) {
        this.parent = parent;

        this.fileChooser = new JFileChooser();
        this.fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("Image Files", "png"));
        this.fileChooser.setAcceptAllFileFilterUsed(false);
    }

    public void choose(final Consumer<BufferedImage> imageConsumer) {
        int returnVal = fileChooser.showOpenDialog(parent);

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            try {
                BufferedImage image = ImageIO.read(fileChooser.getSelectedFile());
                imageConsumer.accept(image);
            } catch (IOException e1) {
                throw new RuntimeException("Could not load image.");
            }
        }
    }

}
