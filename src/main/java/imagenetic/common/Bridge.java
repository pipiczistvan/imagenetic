package imagenetic.common;

import imagenetic.common.api.FrameSide;
import imagenetic.common.api.SceneSide;
import imagenetic.gui.common.api.ListenerContainer;
import puppeteer.annotation.premade.Wire;

public class Bridge {

    public static SceneSide sceneSide;
    public static FrameSide frameSide;

    @Wire
    public static ListenerContainer LISTENER_CONTAINER;

    private Bridge() {
    }
}
