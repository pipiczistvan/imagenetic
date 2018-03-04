package imagenetic.gui.common;

import puppeteer.annotation.premade.Component;
import puppeteer.annotation.premade.Wire;

import java.util.List;

@Component
public class ListenerContainer {

    public final List<ImageSelectionListener> imageSelectionListeners;
    public final List<PlayPressedListener> playPressedListeners;
    public final List<ResetPressedListener> resetPressedListeners;
    public final List<FasterPressedListener> fasterPressedListeners;
    public final List<SlowerPressedListener> slowerPressedListeners;
    public final List<ImageStageChangedListener> imageStageChangedListeners;
    public final List<MultiCheckChangedListener> multiCheckChangedListeners;

    @Wire
    public ListenerContainer(final List<ImageSelectionListener> imageSelectionListeners, final List<PlayPressedListener> playPressedListeners,
                             final List<ResetPressedListener> resetPressedListeners, final List<FasterPressedListener> fasterPressedListeners,
                             final List<SlowerPressedListener> slowerPressedListeners, final List<ImageStageChangedListener> imageStageChangedListeners,
                             final List<MultiCheckChangedListener> multiCheckChangedListeners) {
        this.imageSelectionListeners = imageSelectionListeners;
        this.playPressedListeners = playPressedListeners;
        this.resetPressedListeners = resetPressedListeners;
        this.fasterPressedListeners = fasterPressedListeners;
        this.slowerPressedListeners = slowerPressedListeners;
        this.imageStageChangedListeners = imageStageChangedListeners;
        this.multiCheckChangedListeners = multiCheckChangedListeners;
    }
}
