package imagenetic.gui.frame.main.menubar.control;

import imagenetic.common.Bridge;
import imagenetic.gui.common.api.buttons.SlowerPressedListener;
import puppeteer.annotation.premade.Component;

import javax.swing.*;
import java.awt.event.ActionEvent;

@Component
public class MenuItemSlower extends JMenuItem {

    public MenuItemSlower() {
        super("Lassítás");
        this.addActionListener(this::onClick);
    }

    private void onClick(ActionEvent e) {
        Bridge.LISTENER_CONTAINER.slowerPressedListeners.forEach(SlowerPressedListener::onSlowerPressed);
    }
}
