package imagenetic.gui.frame.main.panel.control.panel.settings;

import imagenetic.common.Bridge;
import imagenetic.scene.asset.voxel.genetic.function.selection.SelectionOperatorType;
import puppeteer.annotation.premade.Component;

import javax.swing.*;
import java.awt.event.ActionEvent;

@Component
public class ComboBoxSelectionOperator extends JComboBox<SelectionOperatorType> {

    public ComboBoxSelectionOperator() {
        super(SelectionOperatorType.values());
        this.addActionListener(this::onChange);
        this.setSelectedItem(SelectionOperatorType.TOURNAMENT);
    }

    private void onChange(final ActionEvent e) {
        ComboBoxSelectionOperator source = (ComboBoxSelectionOperator) e.getSource();
        SelectionOperatorType operatorType = (SelectionOperatorType) source.getSelectedItem();

        Bridge.LISTENER_CONTAINER.selectionOperatorChangedListeners.forEach(l ->
                l.operatorChanged(operatorType));
    }
}
