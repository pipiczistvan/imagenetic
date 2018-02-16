package imagenetic.gui;

import imagenetic.common.Bridge;

import javax.swing.*;

public class MainWindow {

    public final MainFrame frame;

    public MainWindow() {
        this.frame = new MainFrame();
        Bridge.frameSide = frame;
    }

    public void initialize() throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        this.frame.setVisible(true);
        UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        SwingUtilities.updateComponentTreeUI(frame);
    }
}
