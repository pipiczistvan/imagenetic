package imagenetic.gui.frame.main.panel.canvas;

import piengine.visual.display.domain.awt.AwtCanvas;
import puppeteer.annotation.premade.Component;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;

@Component
public class PanelCanvas extends JPanel {

    public AwtCanvas canvasOpenGL;

    public PanelCanvas() {
        this.setBorder(new EtchedBorder(EtchedBorder.RAISED, null, null));
        this.setLayout(new BorderLayout(0, 0));

        canvasOpenGL = new AwtCanvas();
        canvasOpenGL.setFocusable(true);
        canvasOpenGL.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        this.add(canvasOpenGL, BorderLayout.CENTER);
    }
}
