package imagenetic.gui.frame.main.panel.control.panel.settings;

import imagenetic.common.Bridge;
import imagenetic.scene.asset.voxel.genetic.function.crossover.CrossoverOperatorType;
import puppeteer.annotation.premade.Component;

import javax.swing.*;
import java.awt.event.ActionEvent;

@Component
public class ComboBoxCrossoverOperator extends JComboBox<CrossoverOperatorType> {

    public ComboBoxCrossoverOperator() {
        super(CrossoverOperatorType.values());
        this.addActionListener(this::onChange);
        this.setSelectedItem(CrossoverOperatorType.CONSISTENT);
    }

    private void onChange(final ActionEvent e) {
        ComboBoxCrossoverOperator source = (ComboBoxCrossoverOperator) e.getSource();
        CrossoverOperatorType operatorType = (CrossoverOperatorType) source.getSelectedItem();

        Bridge.LISTENER_CONTAINER.crossoverOperatorChangedListeners.forEach(l ->
                l.operatorChanged(operatorType));
    }
}
