package imagenetic.common;

import imagenetic.gui.MainFrame;
import imagenetic.scene.MainScene;
import piengine.core.engine.domain.piEngine;

import javax.swing.*;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;

public class Main {

    private static final String USER_DIR = Objects.requireNonNull(Main.class.getClassLoader().getResource("")).getPath();
    private static final String APPLICATION_PROPERTIES = "application";

    private MainFrame frame;

    public static void main(String[] args) throws MalformedURLException, ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {

        URL coreLibrary = new File(USER_DIR + "lib/pi-engine-core-0.0.6.jar").toURI().toURL();
        URL frameLibrary = new File(USER_DIR + "lib/pi-engine-frame-0.0.6.jar").toURI().toURL();
        URL guiLibrary = new File(USER_DIR + "lib/pi-engine-gui-0.0.6.jar").toURI().toURL();

        /**
         * Comment out this line for testing, and add this line to program arguments!
         * -Dengine.resources.root=../resources
         */
//        FileSystems.newFileSystem(Main.class.getResource("/imagenetic/common/Main.class").toURI(), Collections.emptyMap());

        piEngine engine = new piEngine(
                APPLICATION_PROPERTIES,
                asList(coreLibrary, frameLibrary, guiLibrary),
                singletonList(".*pi-engine.*\\.jar"),
                emptyList()
        );

        Main window = new Main();
        window.frame.setVisible(true);
        Bridge.frameSide = window.frame;

        engine.createAwtDisplay(window.frame, window.frame.getCanvas());
        engine.start(MainScene.class);
    }

    private Main() throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        initialize();
        UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        SwingUtilities.updateComponentTreeUI(frame);
    }

    private void initialize() {
        frame = new MainFrame();
    }
}

