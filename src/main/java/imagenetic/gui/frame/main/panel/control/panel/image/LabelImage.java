package imagenetic.gui.frame.main.panel.control.panel.image;

import imagenetic.common.Bridge;
import imagenetic.common.algorithm.image.ImageProcessor;
import imagenetic.gui.common.ImageChooser;
import imagenetic.gui.common.api.image.ImageSelectionListener;
import imagenetic.gui.common.api.image.ImageStageChangedListener;
import puppeteer.annotation.premade.Component;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import static imagenetic.common.util.ImageUtil.copyImage;


@Component
public class LabelImage extends JLabel implements ImageStageChangedListener, ImageSelectionListener {

    private final BufferedImage[] imageStages = new BufferedImage[3];
    private final ImageChooser imageChooser = new ImageChooser(this);
    private int imageStageIndex = 0;

    public LabelImage() {
        this.setText("Kép kiválasztása...");
        this.setMinimumSize(new Dimension(200, 200));
        this.setMaximumSize(new Dimension(200, 200));
        this.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        this.setBorder(BorderFactory.createDashedBorder(Color.BLACK, 2, 4, 3, true));
        this.setPreferredSize(new Dimension(121, 121));
        this.setAlignmentX(CENTER_ALIGNMENT);
        this.setHorizontalAlignment(SwingConstants.CENTER);
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                chooseImage();
            }
        });
    }

    @Override
    public void onImageStageChanged(final int stageIndex) {
        this.imageStageIndex = stageIndex;
        updateLabelImage();
    }

    @Override
    public void onImageSelect(final BufferedImage image) {
        int newImageWidth;
        int newImageHeight;
        if (image.getWidth() > image.getHeight()) {
            newImageWidth = this.getHeight();
            newImageHeight = (int) (newImageWidth / (float) image.getWidth() * image.getHeight());
        } else {
            newImageHeight = this.getHeight();
            newImageWidth = (int) (newImageHeight / (float) image.getHeight() * image.getWidth());
        }

        imageStages[0] = ImageProcessor.loadImage(copyImage(image)).resize(newImageWidth, newImageHeight).get();
        imageStages[1] = ImageProcessor.loadImage(copyImage(imageStages[0])).toGrayScale().get();
        imageStages[2] = ImageProcessor.loadImage(copyImage(imageStages[1])).toNegative().get();

        updateLabelImage();
    }

    private void chooseImage() {
        imageChooser.choose(this::updateImage);
    }

    private void updateImage(final BufferedImage image) {
        Bridge.LISTENER_CONTAINER.imageSelectionListeners.forEach(l -> l.onImageSelect(copyImage(image)));
    }

    private void updateLabelImage() {
        ImageIcon imageIcon = new ImageIcon(imageStages[imageStageIndex]);
        this.setIcon(imageIcon);
        this.setText("");
    }
}
