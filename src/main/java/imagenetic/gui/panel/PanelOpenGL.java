package imagenetic.gui.panel;

import piengine.visual.display.domain.awt.AwtCanvas;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;

public class PanelOpenGL extends JPanel {

    public AwtCanvas canvasOpenGL;

    public PanelOpenGL() {
        this.setBorder(new EtchedBorder(EtchedBorder.RAISED, null, null));
        this.setLayout(new BorderLayout(0, 0));

        canvasOpenGL = new AwtCanvas();
        canvasOpenGL.setFocusable(true);
        canvasOpenGL.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        this.add(canvasOpenGL, BorderLayout.CENTER);
    }
}
