package imagenetic.gui.frame.main.panel.control.panel.buttons;

import net.miginfocom.swing.MigLayout;
import puppeteer.annotation.premade.Component;
import puppeteer.annotation.premade.Wire;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;

@Component
public class PanelButtons extends JPanel {

    @Wire
    public PanelButtons(final ButtonPlay buttonPlay, final ButtonFaster buttonFaster,
                        final ButtonSlower buttonSlower, final ButtonReset buttonReset,
                        final ButtonSync buttonSync) {
        this.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Vezérlés", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
        this.setLayout(new MigLayout("", "[grow,center][grow,center][grow,center][grow,center][grow,center]", "[40px]"));

        this.add(buttonSlower, "cell 0 0,grow");
        this.add(buttonPlay, "cell 1 0,grow");
        this.add(buttonFaster, "cell 2 0,grow");
        this.add(buttonReset, "cell 3 0,grow");
        this.add(buttonSync, "cell 4 0,grow");
    }
}
