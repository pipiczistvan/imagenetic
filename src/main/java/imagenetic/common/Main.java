package imagenetic.common;

import imagenetic.gui.MainWindow;
import imagenetic.scene.MainScene;
import piengine.core.engine.domain.piEngine;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Objects;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;

public class Main {

    private static final String USER_DIR = Objects.requireNonNull(Main.class.getClassLoader().getResource("")).getPath();
    private static final String APPLICATION_PROPERTIES = "application";

    public static void main(final String[] args) throws IOException, ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException, URISyntaxException {

        URL coreLibrary = new File(USER_DIR + "lib/pi-engine-core-0.0.8.jar").toURI().toURL();
        URL frameLibrary = new File(USER_DIR + "lib/pi-engine-frame-0.0.8.jar").toURI().toURL();
        URL guiLibrary = new File(USER_DIR + "lib/pi-engine-gui-0.0.8.jar").toURI().toURL();

        /**
         * Comment out this line for testing, and add this line to program arguments!
         * -Dengine.resources.root=../resources
         */
//        FileSystems.newFileSystem(Main.class.getResource("/imagenetic/common/Main.class").toURI(), Collections.emptyMap());

        piEngine engine = new piEngine(
                APPLICATION_PROPERTIES,
                asList(coreLibrary, frameLibrary, guiLibrary),
                asList(".*pi-engine.*\\.jar", "^.*/target/.*$"),
//                asList(".*pi-engine.*\\.jar", ".*imagenetic.*\\.jar"),
                singletonList("imagenetic.*.manager")
        );

        UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        MainWindow window = new MainWindow();

        engine.createAwtDisplay(window.frame, window.frame.getCanvas());
        engine.start(MainScene.class);
    }
}

