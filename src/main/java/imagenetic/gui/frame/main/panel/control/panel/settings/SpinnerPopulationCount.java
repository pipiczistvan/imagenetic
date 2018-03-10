package imagenetic.gui.frame.main.panel.control.panel.settings;

import imagenetic.common.Bridge;
import imagenetic.gui.common.api.buttons.PlayPressedListener;
import imagenetic.gui.common.api.buttons.ResetPressedListener;
import puppeteer.annotation.premade.Component;

import javax.swing.*;
import javax.swing.event.ChangeEvent;

import static imagenetic.common.Config.DEF_POPULATION_COUNT;
import static imagenetic.common.Config.MAX_POPULATION_COUNT;
import static imagenetic.common.Config.MIN_POPULATION_COUNT;

@Component
public class SpinnerPopulationCount extends JSpinner implements PlayPressedListener, ResetPressedListener {

    public SpinnerPopulationCount() {
        this.setModel(new SpinnerNumberModel(DEF_POPULATION_COUNT, MIN_POPULATION_COUNT, MAX_POPULATION_COUNT, 1));
        this.addChangeListener(this::onChange);
    }

    @Override
    public void onPlayPressed(final boolean paused) {
        this.setEnabled(false);
    }

    @Override
    public void onResetPressed() {
        this.setEnabled(true);
    }

    private void onChange(ChangeEvent e) {
        JSpinner spinner = (JSpinner) e.getSource();
        Bridge.LISTENER_CONTAINER.populationCountChangedListeners.forEach(l ->
                l.populationCountChanged((int) spinner.getValue()));
    }
}
