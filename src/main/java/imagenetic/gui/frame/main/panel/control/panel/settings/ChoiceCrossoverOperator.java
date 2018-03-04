package imagenetic.gui.frame.main.panel.control.panel.settings;

import imagenetic.gui.common.PlayPressedListener;
import imagenetic.gui.common.ResetPressedListener;
import puppeteer.annotation.premade.Component;

import java.awt.*;

@Component
public class ChoiceCrossoverOperator extends Choice implements PlayPressedListener, ResetPressedListener {

    public ChoiceCrossoverOperator() {
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
