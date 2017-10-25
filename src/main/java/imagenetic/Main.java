package imagenetic;

import piengine.core.engine.domain.piEngine;

import java.util.Arrays;

public class Main {

    private static final String APPLICATION_PROPERTIES = "application";

    public static void main(String[] args) {
        piEngine engine = new piEngine(APPLICATION_PROPERTIES,
                Arrays.asList("^.*/pi-engine.*$", "^.*/target/.*$"),
                Arrays.asList("imagenetic.*.scene", "imagenetic.*.service"));
        engine.start();
    }

}
