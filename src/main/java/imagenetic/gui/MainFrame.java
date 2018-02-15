package imagenetic.gui;

import imagenetic.common.api.FrameSide;
import imagenetic.gui.menu.MenuBar;
import imagenetic.gui.panel.PanelControl;
import imagenetic.gui.panel.PanelLabel;
import imagenetic.gui.panel.PanelOpenGL;
import piengine.core.base.resource.ResourceLoader;
import piengine.visual.display.domain.awt.AwtCanvas;

import javax.swing.*;
import java.awt.*;

import static piengine.core.base.type.property.ApplicationProperties.get;
import static piengine.core.base.type.property.PropertyKeys.IMAGES_LOCATION;
import static piengine.core.base.type.property.PropertyKeys.WINDOW_HEIGHT;
import static piengine.core.base.type.property.PropertyKeys.WINDOW_MIN_HEIGHT;
import static piengine.core.base.type.property.PropertyKeys.WINDOW_MIN_WIDTH;
import static piengine.core.base.type.property.PropertyKeys.WINDOW_TITLE;
import static piengine.core.base.type.property.PropertyKeys.WINDOW_WIDTH;

public class MainFrame extends JFrame implements FrameSide {

    private final ResourceLoader imageLoader = new ResourceLoader(get(IMAGES_LOCATION), "png");

    private PanelOpenGL panelOpenGL;
    private PanelControl panelControl;
    private PanelLabel panelLabel;
    private MenuBar menuBar;

    public MainFrame() {
        this.setBounds(0, 0, get(WINDOW_WIDTH), get(WINDOW_HEIGHT));
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.getContentPane().setLayout(new BorderLayout(0, 0));
        this.setTitle(get(WINDOW_TITLE));
        this.setPreferredSize(new Dimension(get(WINDOW_WIDTH), get(WINDOW_HEIGHT)));
        this.setMinimumSize(new Dimension(get(WINDOW_MIN_WIDTH), get(WINDOW_MIN_HEIGHT)));
        this.setLocationRelativeTo(null);
        this.setIconImage(new ImageIcon(imageLoader.getUrl("gene")).getImage());

        panelOpenGL = new PanelOpenGL();
        this.getContentPane().add(panelOpenGL, BorderLayout.CENTER);

        panelControl = new PanelControl();
        this.getContentPane().add(panelControl, BorderLayout.EAST);

        panelLabel = new PanelLabel();
        this.getContentPane().add(panelLabel, BorderLayout.SOUTH);

        menuBar = new MenuBar(panelControl);
        this.setJMenuBar(menuBar);
    }

    public AwtCanvas getCanvas() {
        return panelOpenGL.canvasOpenGL;
    }

    @Override
    public void updateLabels() {
        panelLabel.updateLabels();
    }

    @Override
    public int getCanvasX() {
        return panelOpenGL.canvasOpenGL.getX();
    }

    @Override
    public int getCanvasY() {
        return panelOpenGL.canvasOpenGL.getY();
    }

    @Override
    public int getCanvasWidth() {
        return panelOpenGL.canvasOpenGL.getWidth();
    }

    @Override
    public int getCanvasHeight() {
        return panelOpenGL.canvasOpenGL.getHeight();
    }
}
