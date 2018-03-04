package imagenetic.gui.frame.main.panel.control.panel.buttons;

import imagenetic.common.Bridge;
import imagenetic.gui.common.ImageSelectionListener;
import imagenetic.gui.common.PlayPressedListener;
import imagenetic.gui.common.ResetPressedListener;
import puppeteer.annotation.premade.Component;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;

import static imagenetic.common.Config.IMAGE_LOADER;

@Component
public class ButtonPlay extends JButton implements ImageSelectionListener, ResetPressedListener {

    private final ImageIcon pauseButtonIcon = new ImageIcon(IMAGE_LOADER.getUrl("pause-button"));
    private final ImageIcon playButtonIcon = new ImageIcon(IMAGE_LOADER.getUrl("play-button"));

    private boolean paused = true;

    public ButtonPlay() {
        this.setToolTipText("Indítás");
        this.setFocusPainted(false);
        this.setMaximumSize(new Dimension(40, 40));
        this.setIcon(playButtonIcon);
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
        this.setIcon(playButtonIcon);
    }

    private void onClick(ActionEvent e) {
        if (paused) {
            this.setIcon(pauseButtonIcon);
            this.setToolTipText("Megállítás");
        } else {
            this.setIcon(playButtonIcon);
            this.setToolTipText("Indítás");
        }

        paused = !paused;
        Bridge.LISTENER_CONTAINER.playPressedListeners.forEach(PlayPressedListener::onPlayPressed);
    }
}
