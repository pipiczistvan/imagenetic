package imagenetic.gui.frame.main.panel.control.panel.buttons;

import imagenetic.common.Bridge;
import imagenetic.gui.common.api.buttons.SyncPressedListener;
import imagenetic.gui.common.api.image.ImageSelectionListener;
import puppeteer.annotation.premade.Component;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;

import static imagenetic.common.Config.IMAGE_LOADER;

@Component
public class ButtonSync extends JButton implements SyncPressedListener, ImageSelectionListener {

    private final ImageIcon syncButtonIcon = new ImageIcon(IMAGE_LOADER.getUrl("sync-button"));

    private boolean interpolated = false;

    public ButtonSync() {
        this.setToolTipText("Folyamatos szinkronizáció");
        this.setFocusPainted(false);
        this.setMaximumSize(new Dimension(40, 40));
        this.setIcon(syncButtonIcon);
        this.addActionListener(this::onClick);
        this.setEnabled(false);
    }

    private void onClick(ActionEvent e) {
        boolean interpolated = this.interpolated;
        Bridge.LISTENER_CONTAINER.syncPressedListeners.forEach(l -> l.onSyncPressed(!interpolated));
    }

    @Override
    public void onImageSelect(final BufferedImage image) {
        this.setEnabled(true);
    }

    @Override
    public void onSyncPressed(final boolean interpolated) {
        this.interpolated = interpolated;

        if (interpolated) {
            this.setToolTipText("Diszkrét szinkronizáció");
        } else {
            this.setToolTipText("Folyamatos szinkronizáció");
        }
    }
}
