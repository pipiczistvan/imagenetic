package imagenetic.gui.frame.main.panel.control.panel.settings;

import imagenetic.common.Bridge;
import puppeteer.annotation.premade.Component;

import javax.swing.*;
import java.awt.event.ItemEvent;

@Component
public class CheckBoxMultiCheck extends JCheckBox {

    public CheckBoxMultiCheck() {
        this.addItemListener(e ->
                Bridge.LISTENER_CONTAINER.multiCheckChangedListeners.forEach(l ->
                        l.onMultiCheckChanged(e.getStateChange() == ItemEvent.SELECTED))
        );
    }
}
