package imagenetic.gui.frame.main.panel.control.panel.settings;

import imagenetic.gui.common.PlayPressedListener;
import imagenetic.gui.common.ResetPressedListener;
import puppeteer.annotation.premade.Component;

import javax.swing.*;

@Component
public class SpinnerPopulationCount extends JSpinner implements PlayPressedListener, ResetPressedListener {

    public SpinnerPopulationCount() {
        this.setModel(new SpinnerNumberModel(2, 2, 4, 1));
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
