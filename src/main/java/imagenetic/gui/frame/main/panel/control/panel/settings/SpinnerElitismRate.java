package imagenetic.gui.frame.main.panel.control.panel.settings;

import imagenetic.common.Bridge;
import puppeteer.annotation.premade.Component;

import javax.swing.*;
import javax.swing.event.ChangeEvent;

import static imagenetic.common.Config.DEF_ELITISM_RATE;
import static imagenetic.common.Config.MAX_ELITISM_RATE;
import static imagenetic.common.Config.MIN_ELITISM_RATE;

@Component
public class SpinnerElitismRate extends JSpinner {

    public SpinnerElitismRate() {
        this.setModel(new SpinnerNumberModel(DEF_ELITISM_RATE, MIN_ELITISM_RATE, MAX_ELITISM_RATE, 0.1));
        this.addChangeListener(this::onChange);
    }

    private void onChange(ChangeEvent e) {
        JSpinner spinner = (JSpinner) e.getSource();
        Bridge.LISTENER_CONTAINER.elitismRateChangedListeners.forEach(l ->
                l.elitismRateChanged((double) spinner.getValue()));
    }
}
