package imagenetic.gui.frame.main.panel.control;

import imagenetic.gui.frame.main.panel.control.panel.buttons.PanelButtons;
import imagenetic.gui.frame.main.panel.control.panel.filler.PanelFiller;
import imagenetic.gui.frame.main.panel.control.panel.image.PanelImage;
import imagenetic.gui.frame.main.panel.control.panel.settings.PanelSettings;
import puppeteer.annotation.premade.Component;
import puppeteer.annotation.premade.Wire;

import javax.swing.*;

@Component
public class PanelControl extends JPanel {

    @Wire
    public PanelControl(final PanelButtons panelButtons, final PanelSettings panelSettings,
                        final PanelImage panelImage, final PanelFiller panelFiller) {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        this.add(panelButtons);
        this.add(panelSettings);
        this.add(panelImage);
        this.add(panelFiller);
    }
}
