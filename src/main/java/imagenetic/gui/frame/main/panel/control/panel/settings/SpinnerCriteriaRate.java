package imagenetic.gui.frame.main.panel.control.panel.settings;

import imagenetic.common.Bridge;
import puppeteer.annotation.premade.Component;

import javax.swing.*;
import javax.swing.event.ChangeEvent;

import static imagenetic.common.Config.DEF_CRITERIA_RATE;
import static imagenetic.common.Config.MAX_CRITERIA_RATE;
import static imagenetic.common.Config.MIN_CRITERIA_RATE;

@Component
public class SpinnerCriteriaRate extends JSpinner {

    public SpinnerCriteriaRate() {
        this.setModel(new SpinnerNumberModel(DEF_CRITERIA_RATE, MIN_CRITERIA_RATE, MAX_CRITERIA_RATE, 0.1));
        this.addChangeListener(this::onChange);
    }

    private void onChange(ChangeEvent e) {
        JSpinner spinner = (JSpinner) e.getSource();
        Bridge.LISTENER_CONTAINER.criteriaRateChangedListeners.forEach(l ->
                l.criteriaRateChanged((double) spinner.getValue()));
    }
}
