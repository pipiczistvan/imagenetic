package imagenetic.gui.frame.main.panel.control.panel.settings;

import imagenetic.gui.common.PlayPressedListener;
import imagenetic.gui.common.ResetPressedListener;
import puppeteer.annotation.premade.Component;

import javax.swing.*;

@Component
public class SpinnerCriteria extends JSpinner implements PlayPressedListener, ResetPressedListener {

    public SpinnerCriteria() {
    }

    @Override
    public void onPlayPressed() {
        this.setEnabled(false);
    }

    @Override
    public void onResetPressed() {
        this.setEnabled(true);
    }
}