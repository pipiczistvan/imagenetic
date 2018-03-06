package imagenetic.gui.frame.main.panel.control.panel.buttons;

import imagenetic.common.Bridge;
import imagenetic.gui.common.api.buttons.FasterPressedListener;
import puppeteer.annotation.premade.Component;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

import static imagenetic.common.Config.IMAGE_LOADER;

@Component
public class ButtonFaster extends JButton {

    private final ImageIcon forwardButtonIcon = new ImageIcon(IMAGE_LOADER.getUrl("forward-button"));

    public ButtonFaster() {
        this.setToolTipText("Gyorsítás");
        this.setFocusPainted(false);
        this.setMaximumSize(new Dimension(40, 40));
        this.setIcon(forwardButtonIcon);
        this.addActionListener(this::faster);
    }

    private void faster(ActionEvent e) {
        Bridge.LISTENER_CONTAINER.fasterPressedListeners.forEach(FasterPressedListener::onFasterPressed);
    }
}
