package imagenetic.gui.frame.main.panel.control.panel.image;

import imagenetic.common.Bridge;
import imagenetic.gui.common.api.image.ImageSelectionListener;
import puppeteer.annotation.premade.Component;

import javax.swing.*;
import java.awt.image.BufferedImage;

@Component
public class SliderImage extends JSlider implements ImageSelectionListener {

    public SliderImage() {
        this.setPaintTicks(true);
        this.setPaintLabels(true);
        this.setValue(0);
        this.setSnapToTicks(true);
        this.setMaximum(2);
        this.addChangeListener(e -> {
            JSlider source = (JSlider) e.getSource();
            Bridge.LISTENER_CONTAINER.imageStageChangedListeners.forEach(l -> l.onImageStageChanged(source.getValue()));
        });
        this.setEnabled(false);
    }

    @Override
    public void onImageSelect(final BufferedImage image) {
        this.setEnabled(true);
    }
}
