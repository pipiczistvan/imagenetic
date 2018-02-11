package imagenetic.ui;

import imagenetic.common.Bridge;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class MainMenuBar extends JMenuBar {

    public MainMenuBar(final JFrame frame) {
        add(createFileMenu(frame));
    }

    private JMenu createFileMenu(final JFrame frame) {
        JMenu menu_file = new JMenu("Fájl");

        JFileChooser fileChooser_image = new JFileChooser();

        JMenuItem menuItem_open = new JMenuItem("Nyit");
        menuItem_open.addActionListener(e -> {
            int returnVal = fileChooser_image.showOpenDialog(frame);

            if (returnVal == JFileChooser.APPROVE_OPTION) {
                try {
                    BufferedImage image = ImageIO.read(fileChooser_image.getSelectedFile());

                    Bridge.mainFrame.updateImage(copyImage(image));
                    Bridge.mainScene.getGeneticAlgorithm().setImage(copyImage(image));
                } catch (IOException e1) {
                    throw new RuntimeException("Could not load image.");
                }
            }
        });

        menu_file.add(menuItem_open);

        return menu_file;
    }

    private static BufferedImage copyImage(final BufferedImage source){
        BufferedImage b = new BufferedImage(source.getWidth(), source.getHeight(), source.getType());
        Graphics g = b.getGraphics();
        g.drawImage(source, 0, 0, null);
        g.dispose();
        return b;
    }
}
