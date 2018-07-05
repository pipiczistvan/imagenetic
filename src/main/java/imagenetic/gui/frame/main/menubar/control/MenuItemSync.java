package imagenetic.gui.frame.main.menubar.control;

import imagenetic.common.Bridge;
import imagenetic.gui.common.api.buttons.SyncPressedListener;
import imagenetic.gui.common.api.image.ImageSelectionListener;
import puppeteer.annotation.premade.Component;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;

@Component
public class MenuItemSync extends JMenuItem implements SyncPressedListener, ImageSelectionListener {

    private boolean interpolated = false;

    public MenuItemSync() {
        super("Folyamatos szinkronizáció");
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
            this.setText("Diszkrét szinkronizáció");
        } else {
            this.setText("Folyamatos szinkronizáció");
        }
    }
}
