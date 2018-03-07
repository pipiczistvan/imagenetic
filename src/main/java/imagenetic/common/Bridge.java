package imagenetic.common;

import imagenetic.common.api.FrameSide;
import imagenetic.gui.common.api.ListenerContainer;
import puppeteer.annotation.premade.Wire;

public class Bridge {

    public static FrameSide frameSide;

    @Wire
    public static ListenerContainer LISTENER_CONTAINER;

    private Bridge() {
    }
}
