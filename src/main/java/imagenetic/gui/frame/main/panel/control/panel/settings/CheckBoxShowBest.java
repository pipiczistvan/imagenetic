package imagenetic.gui.frame.main.panel.control.panel.settings;

import imagenetic.common.Bridge;
import puppeteer.annotation.premade.Component;

import javax.swing.*;
import java.awt.event.ItemEvent;

@Component
public class CheckBoxShowBest extends JCheckBox {

    public CheckBoxShowBest() {
        this.addItemListener(e ->
                Bridge.LISTENER_CONTAINER.showBestChangedListeners.forEach(l ->
                        l.onShowBestChanged(e.getStateChange() == ItemEvent.SELECTED))
        );
    }
}
