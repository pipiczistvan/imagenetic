package imagenetic.gui.frame.main.panel.control.panel.settings;

import imagenetic.common.Bridge;
import imagenetic.scene.asset.voxel.genetic.function.mutation.MutationOperatorType;
import puppeteer.annotation.premade.Component;

import javax.swing.*;
import java.awt.event.ActionEvent;

@Component
public class ComboBoxMutationOperator extends JComboBox<MutationOperatorType> {

    public ComboBoxMutationOperator() {
        super(MutationOperatorType.values());
        this.addActionListener(this::onChange);
        this.setSelectedItem(MutationOperatorType.RANDOM);
    }

    private void onChange(final ActionEvent e) {
        ComboBoxMutationOperator source = (ComboBoxMutationOperator) e.getSource();
        MutationOperatorType operatorType = (MutationOperatorType) source.getSelectedItem();

        Bridge.LISTENER_CONTAINER.mutationOperatorChangedListeners.forEach(l ->
                l.operatorChanged(operatorType));
    }
}
