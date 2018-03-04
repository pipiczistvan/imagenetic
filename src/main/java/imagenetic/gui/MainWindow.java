package imagenetic.gui;

import imagenetic.common.Bridge;
import imagenetic.gui.frame.main.MainFrame;
import puppeteer.Puppeteer;
import puppeteer.annotation.premade.Wire;

public class MainWindow {

    @Wire
    private static Puppeteer puppeteer;

    public final MainFrame frame;

    public MainWindow() {
        this.frame = puppeteer.getInstanceOf(MainFrame.class);

        this.frame.setVisible(true);
        Bridge.frameSide = frame;
    }
}
