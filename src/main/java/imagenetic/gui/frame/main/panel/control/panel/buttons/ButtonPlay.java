package imagenetic.gui.frame.main.panel.control.panel.buttons;

import imagenetic.common.Bridge;
import imagenetic.gui.common.api.buttons.PlayPressedListener;
import imagenetic.gui.common.api.buttons.ResetPressedListener;
import imagenetic.gui.common.api.image.ImageSelectionListener;
import puppeteer.annotation.premade.Component;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;

import static imagenetic.common.Config.IMAGE_LOADER;

@Component
public class ButtonPlay extends JButton implements ImageSelectionListener, ResetPressedListener, PlayPressedListener {

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
        this.setToolTipText("Indítás");
    }

    @Override
    public void onPlayPressed(final boolean paused) {
        if (paused) {
            this.setIcon(playButtonIcon);
            this.setToolTipText("Indítás");
        } else {
            this.setIcon(pauseButtonIcon);
            this.setToolTipText("Megállítás");
        }

        this.paused = paused;
    }

    private void onClick(ActionEvent e) {
        boolean paused = this.paused;
        Bridge.LISTENER_CONTAINER.playPressedListeners.forEach(l -> l.onPlayPressed(!paused));
    }
}
