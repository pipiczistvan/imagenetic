package imagenetic.gui.frame.main.menubar.control;

import imagenetic.common.Bridge;
import imagenetic.gui.common.api.buttons.PlayPressedListener;
import imagenetic.gui.common.api.buttons.ResetPressedListener;
import puppeteer.annotation.premade.Component;

import javax.swing.*;
import java.awt.event.ActionEvent;

@Component
public class MenuItemStop extends JMenuItem implements PlayPressedListener {

    public MenuItemStop() {
        super("Leállítás");
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
