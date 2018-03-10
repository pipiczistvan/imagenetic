package imagenetic.gui.frame.main.menubar.control;

import imagenetic.common.Bridge;
import imagenetic.gui.common.api.buttons.PlayPressedListener;
import imagenetic.gui.common.api.buttons.ResetPressedListener;
import imagenetic.gui.common.api.image.ImageSelectionListener;
import puppeteer.annotation.premade.Component;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;

@Component
public class MenuItemPlay extends JMenuItem implements ImageSelectionListener, ResetPressedListener, PlayPressedListener {

    private boolean paused = true;

    public MenuItemPlay() {
        super("Indítás");
        this.addActionListener(this::onClick);
        this.setEnabled(false);
    }

    @Override
    public void onImageSelect(final BufferedImage image) {
        this.setEnabled(true);
    }

    @Override
    public void onResetPressed() {
        this.paused = true;
        this.setText("Indítás");
    }

    @Override
    public void onPlayPressed(final boolean paused) {
        if (paused) {
            this.setText("Indítás");
        } else {
            this.setText("Megállítás");
        }

        this.paused = paused;
    }

    private void onClick(ActionEvent e) {
        boolean paused = this.paused;
        Bridge.LISTENER_CONTAINER.playPressedListeners.forEach(l -> l.onPlayPressed(!paused));
    }
}
