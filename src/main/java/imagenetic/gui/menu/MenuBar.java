package imagenetic.gui.menu;

import imagenetic.gui.panel.PanelControl;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class MenuBar extends JMenuBar {

    public MenuBar(final PanelControl panelControl) {
        JMenu mnFile = new JMenu("Fájl");
        this.add(mnFile);

        JFileChooser fileChooser_image = new JFileChooser();
        fileChooser_image.addChoosableFileFilter(new FileNameExtensionFilter("Image Files", "png"));
        fileChooser_image.setAcceptAllFileFilterUsed(false);
        JMenuItem mntmSelect = new JMenuItem("Kép kiválasztása...");
        mntmSelect.setMnemonic('K');
        mntmSelect.addActionListener(e -> {
            int returnVal = fileChooser_image.showOpenDialog(this);

            if (returnVal == JFileChooser.APPROVE_OPTION) {
                try {
                    BufferedImage image = ImageIO.read(fileChooser_image.getSelectedFile());
                    panelControl.updateImage(image);
                } catch (IOException e1) {
                    throw new RuntimeException("Could not load image.");
                }
            }
        });
        mnFile.add(mntmSelect);
    }

}
