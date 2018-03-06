package imagenetic.gui.frame.main.panel.control.panel.buttons;

import imagenetic.common.Bridge;
import imagenetic.gui.common.api.buttons.SlowerPressedListener;
import puppeteer.annotation.premade.Component;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

import static imagenetic.common.Config.IMAGE_LOADER;

@Component
public class ButtonSlower extends JButton {

    private final ImageIcon backwardButtonIcon = new ImageIcon(IMAGE_LOADER.getUrl("backward-button"));

    public ButtonSlower() {
        this.setToolTipText("Lassítás");
        this.setFocusPainted(false);
        this.setMaximumSize(new Dimension(40, 40));
        this.setIcon(backwardButtonIcon);
        this.addActionListener(this::slower);
    }

    private void slower(ActionEvent e) {
        Bridge.LISTENER_CONTAINER.slowerPressedListeners.forEach(SlowerPressedListener::onSlowerPressed);
    }
}
