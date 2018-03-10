package imagenetic.gui.frame.main.menubar.file;

import imagenetic.common.Bridge;
import imagenetic.gui.common.ImageChooser;
import puppeteer.annotation.premade.Component;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;

import static imagenetic.common.util.ImageUtil.copyImage;

@Component
public class MenuItemSelect extends JMenuItem {

    private final ImageChooser imageChooser = new ImageChooser(this);

    public MenuItemSelect() {
        super("Kép kiválasztása...");
        this.addActionListener(this::onClick);
    }

    private void onClick(ActionEvent e) {
        imageChooser.choose(this::updateImage);
    }

    private void updateImage(final BufferedImage image) {
        Bridge.LISTENER_CONTAINER.imageSelectionListeners.forEach(l -> l.onImageSelect(copyImage(image)));
    }
}
