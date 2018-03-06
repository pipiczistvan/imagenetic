package imagenetic.gui.frame.main.panel.control.panel.settings;

import imagenetic.gui.common.api.buttons.PlayPressedListener;
import imagenetic.gui.common.api.buttons.ResetPressedListener;
import puppeteer.annotation.premade.Component;

import java.awt.*;

@Component
public class ChoiceSelectionOperator extends Choice implements PlayPressedListener, ResetPressedListener {

    public ChoiceSelectionOperator() {
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
