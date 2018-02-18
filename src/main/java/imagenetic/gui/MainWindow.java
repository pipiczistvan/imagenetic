package imagenetic.gui;

import imagenetic.common.Bridge;

public class MainWindow {

    public final MainFrame frame;

    public MainWindow() {
        this.frame = new MainFrame();

        this.frame.setVisible(true);
        Bridge.frameSide = frame;
    }
}
