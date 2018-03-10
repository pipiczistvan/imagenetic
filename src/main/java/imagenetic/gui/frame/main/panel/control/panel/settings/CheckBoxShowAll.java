package imagenetic.gui.frame.main.panel.control.panel.settings;

import imagenetic.common.Bridge;
import puppeteer.annotation.premade.Component;

import javax.swing.*;
import java.awt.event.ItemEvent;

@Component
public class CheckBoxShowAll extends JCheckBox {

    public CheckBoxShowAll() {
        this.addItemListener(e ->
                Bridge.LISTENER_CONTAINER.showAllChangedListeners.forEach(l ->
                        l.onShowAllChanged(e.getStateChange() == ItemEvent.SELECTED))
        );
    }
}
