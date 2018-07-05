package imagenetic.gui.frame.main.panel.control.panel.settings;

import imagenetic.common.Bridge;
import puppeteer.annotation.premade.Component;

import javax.swing.*;
import javax.swing.event.ChangeEvent;

import static imagenetic.common.Config.DEF_MUTATION_RATE;
import static imagenetic.common.Config.MAX_MUTATION_RATE;
import static imagenetic.common.Config.MIN_MUTATION_RATE;

@Component
public class SpinnerMutationRate extends JSpinner {

    public SpinnerMutationRate() {
        this.setModel(new SpinnerNumberModel(DEF_MUTATION_RATE, MIN_MUTATION_RATE, MAX_MUTATION_RATE, 0.1));
        this.addChangeListener(this::onChange);
    }

    private void onChange(ChangeEvent e) {
        JSpinner spinner = (JSpinner) e.getSource();
        Bridge.LISTENER_CONTAINER.mutationRateChangedListeners.forEach(l ->
                l.mutationRateChanged((double) spinner.getValue()));
    }
}
