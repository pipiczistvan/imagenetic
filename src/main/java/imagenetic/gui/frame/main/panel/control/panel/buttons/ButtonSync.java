package imagenetic.gui.frame.main.panel.control.panel.buttons;

import imagenetic.common.Bridge;
import imagenetic.gui.common.api.buttons.SyncPressedListener;
import puppeteer.annotation.premade.Component;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

import static imagenetic.common.Config.IMAGE_LOADER;

@Component
public class ButtonSync extends JButton {

    private final ImageIcon discreteButtonIcon = new ImageIcon(IMAGE_LOADER.getUrl("discrete-button"));
    private final ImageIcon continuousButtonIcon = new ImageIcon(IMAGE_LOADER.getUrl("continuous-button"));

    private boolean discrete = true;

    public ButtonSync() {
        this.setToolTipText("Folyamatos szinkronizáció");
        this.setFocusPainted(false);
        this.setMaximumSize(new Dimension(40, 40));
        this.setIcon(discreteButtonIcon);
        this.addActionListener(this::onClick);
    }

    private void onClick(ActionEvent e) {
        if (discrete) {
            this.setIcon(continuousButtonIcon);
            this.setToolTipText("Folyamatos szinkronizáció");
        } else {
            this.setIcon(discreteButtonIcon);
            this.setToolTipText("Diszkrét szinkronizáció");
        }

        discrete = !discrete;
        Bridge.LISTENER_CONTAINER.syncPressedListeners.forEach(SyncPressedListener::onSyncPressed);
    }
}
