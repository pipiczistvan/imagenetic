package imagenetic.common;

import imagenetic.gui.MainWindow;
import imagenetic.scene.MainScene;
import piengine.core.engine.domain.piEngine;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;

import static java.util.Arrays.asList;

public class Main {

    static {
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (ClassNotFoundException | InstantiationException | UnsupportedLookAndFeelException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private static final String USER_DIR = Objects.requireNonNull(Main.class.getClassLoader().getResource("")).getPath();
    private static final String APPLICATION_PROPERTIES = "application";

    public static void main(final String[] args) throws IOException {

        URL coreLibrary = new File(USER_DIR + "lib/pi-engine-core-0.0.8.jar").toURI().toURL();
        URL frameLibrary = new File(USER_DIR + "lib/pi-engine-frame-0.0.8.jar").toURI().toURL();
        URL guiLibrary = new File(USER_DIR + "lib/pi-engine-gui-0.0.8.jar").toURI().toURL();

        /**
         * Comment out this voxel for testing, and add this voxel to program arguments!
         * -Dengine.resources.root=../resources
         */
//        FileSystems.newFileSystem(Main.class.getResource("/imagenetic/common/Main.class").toURI(), Collections.emptyMap());

        piEngine engine = new piEngine(
                APPLICATION_PROPERTIES,
                asList(coreLibrary, frameLibrary, guiLibrary),
                asList(".*pi-engine.*\\.jar", "^.*/target/.*$"),
//                asList(".*pi-engine.*\\.jar", ".*imagenetic.*\\.jar"),
                asList("imagenetic.*.manager", "imagenetic.gui.*", "imagenetic.common.*", "imagenetic.scene.asset.*")
        );
        MainWindow window = new MainWindow();

        engine.createAwtDisplay(window.frame, window.frame.getCanvas());
        engine.start(MainScene.class);
    }
}

