package imagenetic.gui.frame.main.panel.control.panel.image;

import net.miginfocom.swing.MigLayout;
import puppeteer.annotation.premade.Component;
import puppeteer.annotation.premade.Wire;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

@Component
public class PanelImage extends JPanel {

    @Wire
    public PanelImage(final SliderImage sliderImage, final LabelImage labelImage) {
        this.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Kép", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        this.setAlignmentX(LEFT_ALIGNMENT);
        this.setLayout(new MigLayout("", "[grow]", "[20px][grow]"));

        this.add(sliderImage, "cell 0 0,grow");
        this.add(labelImage, "cell 0 1,alignx center,aligny center");
    }
}
