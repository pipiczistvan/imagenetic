package imagenetic.gui.frame.main.panel.control.panel.buttons;

import imagenetic.common.Bridge;
import imagenetic.gui.common.api.buttons.PlayPressedListener;
import imagenetic.gui.common.api.buttons.ResetPressedListener;
import puppeteer.annotation.premade.Component;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

import static imagenetic.common.Config.IMAGE_LOADER;

@Component
public class ButtonReset extends JButton implements PlayPressedListener {

    private final ImageIcon stopButtonIcon = new ImageIcon(IMAGE_LOADER.getUrl("stop-button"));

    public ButtonReset() {
        this.setToolTipText("Leállítás");
        this.setFocusPainted(false);
        this.setMaximumSize(new Dimension(40, 40));
        this.setIcon(stopButtonIcon);
        this.addActionListener(this::onClick);
        this.setEnabled(false);
    }

    @Override
    public void onPlayPressed(final boolean paused) {
        this.setEnabled(true);
    }

    private void onClick(ActionEvent e) {
        this.setEnabled(false);
        Bridge.LISTENER_CONTAINER.resetPressedListeners.forEach(ResetPressedListener::onResetPressed);
    }
}
