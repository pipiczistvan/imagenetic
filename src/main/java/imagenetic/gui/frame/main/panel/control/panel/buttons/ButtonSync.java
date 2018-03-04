package imagenetic.gui.frame.main.panel.control.panel.buttons;

import puppeteer.annotation.premade.Component;

import javax.swing.*;
import java.awt.*;

import static imagenetic.common.Config.IMAGE_LOADER;

@Component
public class ButtonSync extends JButton {

    private final ImageIcon discreteButtonIcon = new ImageIcon(IMAGE_LOADER.getUrl("discrete-button"));
    private final ImageIcon continuousButtonIcon = new ImageIcon(IMAGE_LOADER.getUrl("continuous-button"));

    public ButtonSync() {
        this.setToolTipText("Folyamatos szinkronizáció");
        this.setFocusPainted(false);
        this.setMaximumSize(new Dimension(40, 40));
        this.setIcon(discreteButtonIcon);
    }
}
