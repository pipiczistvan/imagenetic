package imagenetic.gui.frame.main.menubar.view;

import imagenetic.common.Bridge;
import puppeteer.annotation.premade.Component;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import static imagenetic.gui.common.api.ViewChangedListener.VIEW_TYPE.NONE;

@Component
public class MenuItemZoomReset extends JMenuItem {

    public MenuItemZoomReset() {
        super("Közelítés visszaállítása");

        this.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, 0));
        this.addActionListener(this::onClick);
    }

    private void onClick(ActionEvent e) {
        Bridge.LISTENER_CONTAINER.viewChangedListeners.forEach(l ->
                l.viewChanged(NONE, true));
    }
}
