package imagenetic.gui.frame.main;

import imagenetic.common.api.FrameSide;
import imagenetic.gui.frame.main.menubar.MainMenuBar;
import imagenetic.gui.frame.main.panel.canvas.PanelCanvas;
import imagenetic.gui.frame.main.panel.control.PanelControl;
import imagenetic.gui.frame.main.panel.label.PanelLabel;
import piengine.core.base.resource.ResourceLoader;
import piengine.visual.display.domain.awt.AwtCanvas;
import puppeteer.annotation.premade.Component;
import puppeteer.annotation.premade.Wire;

import javax.swing.*;
import java.awt.*;

import static piengine.core.base.type.property.ApplicationProperties.get;
import static piengine.core.base.type.property.PropertyKeys.IMAGES_LOCATION;
import static piengine.core.base.type.property.PropertyKeys.WINDOW_HEIGHT;
import static piengine.core.base.type.property.PropertyKeys.WINDOW_MIN_HEIGHT;
import static piengine.core.base.type.property.PropertyKeys.WINDOW_MIN_WIDTH;
import static piengine.core.base.type.property.PropertyKeys.WINDOW_TITLE;
import static piengine.core.base.type.property.PropertyKeys.WINDOW_WIDTH;

@Component
public class MainFrame extends JFrame implements FrameSide {

    private final ResourceLoader imageLoader = new ResourceLoader(get(IMAGES_LOCATION), "png");

    private final PanelCanvas panelCanvas;
    private final PanelLabel panelLabel;

    @Wire
    public MainFrame(final MainMenuBar mainMenuBar, final PanelControl panelControl,
                     final PanelCanvas panelCanvas, final PanelLabel panelLabel) {
        this.panelCanvas = panelCanvas;
        this.panelLabel = panelLabel;

        this.setBounds(0, 0, get(WINDOW_WIDTH), get(WINDOW_HEIGHT));
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.getContentPane().setLayout(new BorderLayout(0, 0));
        this.setTitle(get(WINDOW_TITLE));
        this.setPreferredSize(new Dimension(get(WINDOW_WIDTH), get(WINDOW_HEIGHT)));
        this.setMinimumSize(new Dimension(get(WINDOW_MIN_WIDTH), get(WINDOW_MIN_HEIGHT)));
        this.setLocationRelativeTo(null);
        this.setIconImage(new ImageIcon(imageLoader.getUrl("icon")).getImage());

        this.setJMenuBar(mainMenuBar);
        this.getContentPane().add(panelCanvas, BorderLayout.CENTER);
        this.getContentPane().add(panelControl, BorderLayout.EAST);
        this.getContentPane().add(panelLabel, BorderLayout.SOUTH);
    }

    public AwtCanvas getCanvas() {
        return panelCanvas.canvasOpenGL;
    }

    @Override
    public void updateLabels(final int numberOfGenerations, final float averageFitness, final float bestFitness, final int generationsPerSec, final int entityCount) {
        panelLabel.updateLabels(numberOfGenerations, averageFitness, bestFitness, generationsPerSec, entityCount);
    }

    @Override
    public int getCanvasX() {
        return panelCanvas.canvasOpenGL.getX();
    }

    @Override
    public int getCanvasY() {
        return panelCanvas.canvasOpenGL.getY();
    }

    @Override
    public int getCanvasWidth() {
        return panelCanvas.canvasOpenGL.getWidth();
    }

    @Override
    public int getCanvasHeight() {
        return panelCanvas.canvasOpenGL.getHeight();
    }
}
