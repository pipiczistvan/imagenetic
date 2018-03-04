package imagenetic.gui.frame.main.panel.control.panel.settings;

import imagenetic.gui.common.PlayPressedListener;
import imagenetic.gui.common.ResetPressedListener;
import puppeteer.annotation.premade.Component;

import javax.swing.*;

@Component
public class SpinnerMutationRate extends JSpinner implements PlayPressedListener, ResetPressedListener {

    public SpinnerMutationRate() {
        this.setModel(new SpinnerNumberModel(0.0, 0.0, 1.0, 0.1));
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
