package imagenetic.gui.frame.main.menubar.control;

import imagenetic.common.Bridge;
import imagenetic.gui.common.api.buttons.FasterPressedListener;
import puppeteer.annotation.premade.Component;

import javax.swing.*;
import java.awt.event.ActionEvent;

@Component
public class MenuItemFaster extends JMenuItem {

    public MenuItemFaster() {
        super("Gyorsítás");
        this.addActionListener(this::onClick);
    }

    private void onClick(ActionEvent e) {
        Bridge.LISTENER_CONTAINER.fasterPressedListeners.forEach(FasterPressedListener::onFasterPressed);
    }
}
