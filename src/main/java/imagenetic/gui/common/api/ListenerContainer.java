package imagenetic.gui.common.api;

import imagenetic.gui.common.api.buttons.FasterPressedListener;
import imagenetic.gui.common.api.buttons.PlayPressedListener;
import imagenetic.gui.common.api.buttons.ResetPressedListener;
import imagenetic.gui.common.api.buttons.SlowerPressedListener;
import imagenetic.gui.common.api.buttons.SyncPressedListener;
import imagenetic.gui.common.api.image.ImageSelectionListener;
import imagenetic.gui.common.api.image.ImageStageChangedListener;
import imagenetic.gui.common.api.settings.CriteriaRateChangedListener;
import imagenetic.gui.common.api.settings.CrossoverOperatorChangedListener;
import imagenetic.gui.common.api.settings.ElitismRateChangedListener;
import imagenetic.gui.common.api.settings.MultiCheckChangedListener;
import imagenetic.gui.common.api.settings.MutationOperatorChangedListener;
import imagenetic.gui.common.api.settings.MutationRateChangedListener;
import imagenetic.gui.common.api.settings.PopulationCountChangedListener;
import imagenetic.gui.common.api.settings.SelectionOperatorChangedListener;
import imagenetic.gui.common.api.settings.ShowAllChangedListener;
import puppeteer.annotation.premade.Component;
import puppeteer.annotation.premade.Wire;

import java.util.List;

@Component
public class ListenerContainer {

    public final List<ImageSelectionListener> imageSelectionListeners;
    public final List<PlayPressedListener> playPressedListeners;
    public final List<SyncPressedListener> syncPressedListeners;
    public final List<ResetPressedListener> resetPressedListeners;
    public final List<FasterPressedListener> fasterPressedListeners;
    public final List<SlowerPressedListener> slowerPressedListeners;
    public final List<ImageStageChangedListener> imageStageChangedListeners;
    public final List<MultiCheckChangedListener> multiCheckChangedListeners;
    public final List<ShowAllChangedListener> showAllChangedListeners;
    public final List<ViewChangedListener> viewChangedListeners;
    public final List<MutationRateChangedListener> mutationRateChangedListeners;
    public final List<ElitismRateChangedListener> elitismRateChangedListeners;
    public final List<CriteriaRateChangedListener> criteriaRateChangedListeners;
    public final List<PopulationCountChangedListener> populationCountChangedListeners;
    public final List<SelectionOperatorChangedListener> selectionOperatorChangedListeners;
    public final List<CrossoverOperatorChangedListener> crossoverOperatorChangedListeners;
    public final List<MutationOperatorChangedListener> mutationOperatorChangedListeners;

    @Wire
    public ListenerContainer(final List<ImageSelectionListener> imageSelectionListeners, final List<PlayPressedListener> playPressedListeners,
                             final List<SyncPressedListener> syncPressedListeners, final List<ShowAllChangedListener> showAllChangedListeners,
                             final List<ResetPressedListener> resetPressedListeners, final List<FasterPressedListener> fasterPressedListeners,
                             final List<SlowerPressedListener> slowerPressedListeners, final List<ImageStageChangedListener> imageStageChangedListeners,
                             final List<MultiCheckChangedListener> multiCheckChangedListeners, final List<ViewChangedListener> viewChangedListeners,
                             final List<MutationRateChangedListener> mutationRateChangedListeners, final List<ElitismRateChangedListener> elitismRateChangedListeners,
                             final List<CriteriaRateChangedListener> criteriaRateChangedListeners, final List<PopulationCountChangedListener> populationCountChangedListeners,
                             final List<SelectionOperatorChangedListener> selectionOperatorChangedListeners, final List<CrossoverOperatorChangedListener> crossoverOperatorChangedListeners,
                             final List<MutationOperatorChangedListener> mutationOperatorChangedListeners) {
        this.imageSelectionListeners = imageSelectionListeners;
        this.playPressedListeners = playPressedListeners;
        this.syncPressedListeners = syncPressedListeners;
        this.resetPressedListeners = resetPressedListeners;
        this.fasterPressedListeners = fasterPressedListeners;
        this.slowerPressedListeners = slowerPressedListeners;
        this.imageStageChangedListeners = imageStageChangedListeners;
        this.multiCheckChangedListeners = multiCheckChangedListeners;
        this.showAllChangedListeners = showAllChangedListeners;
        this.viewChangedListeners = viewChangedListeners;
        this.mutationRateChangedListeners = mutationRateChangedListeners;
        this.elitismRateChangedListeners = elitismRateChangedListeners;
        this.criteriaRateChangedListeners= criteriaRateChangedListeners;
        this.populationCountChangedListeners = populationCountChangedListeners;
        this.selectionOperatorChangedListeners = selectionOperatorChangedListeners;
        this.crossoverOperatorChangedListeners = crossoverOperatorChangedListeners;
        this.mutationOperatorChangedListeners = mutationOperatorChangedListeners;
    }
}
