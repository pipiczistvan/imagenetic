package imagenetic.ui;

import imagenetic.common.Bridge;
import piengine.core.base.resource.ResourceLoader;
import piengine.visual.display.domain.awt.AwtCanvas;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

import static piengine.core.base.type.property.ApplicationProperties.get;
import static piengine.core.base.type.property.PropertyKeys.IMAGES_LOCATION;
import static piengine.core.base.type.property.PropertyKeys.WINDOW_HEIGHT;
import static piengine.core.base.type.property.PropertyKeys.WINDOW_MIN_HEIGHT;
import static piengine.core.base.type.property.PropertyKeys.WINDOW_MIN_WIDTH;
import static piengine.core.base.type.property.PropertyKeys.WINDOW_TITLE;
import static piengine.core.base.type.property.PropertyKeys.WINDOW_WIDTH;

public class MainFrame extends JFrame {

    public final AwtCanvas canvas_main = new AwtCanvas();
    private final MainApp app_main = new MainApp();
    private final ResourceLoader imageLoader = new ResourceLoader(get(IMAGES_LOCATION), "png");

    public MainFrame() throws HeadlessException {
        canvas_main.setFocusable(true);
        canvas_main.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        app_main.panel_opengl.add(canvas_main);

        MainMenuBar mainMenuBar = new MainMenuBar(this);

        setContentPane(app_main.panel_main);
        setJMenuBar(mainMenuBar);
        setTitle(get(WINDOW_TITLE));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setPreferredSize(new Dimension(get(WINDOW_WIDTH), get(WINDOW_HEIGHT)));
        setMinimumSize(new Dimension(get(WINDOW_MIN_WIDTH), get(WINDOW_MIN_HEIGHT)));
        setLocationRelativeTo(null);
        setIconImage(new ImageIcon(imageLoader.getUrl("gene")).getImage());

        Bridge.mainFrame = this;
    }

    public void updateLabels() {
        app_main.updateLabels();
    }

    public void updateImage(final BufferedImage image) {
        app_main.updateImage(image);
    }
}
