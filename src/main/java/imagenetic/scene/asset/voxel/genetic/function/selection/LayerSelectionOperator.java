package imagenetic.scene.asset.voxel.genetic.function.selection;

import imagenetic.common.algorithm.genetic.entity.Entity;
import imagenetic.common.algorithm.genetic.function.SelectionOperator;
import imagenetic.gui.common.api.settings.SelectionOperatorChangedListener;
import imagenetic.scene.asset.voxel.genetic.entity.LayerChromosome;
import javafx.util.Pair;
import puppeteer.annotation.premade.Component;
import puppeteer.annotation.premade.Wire;

import java.util.List;
import java.util.Optional;

@Component
public class LayerSelectionOperator implements SelectionOperator<LayerChromosome>, SelectionOperatorChangedListener {

    private final List<LayerSelectionOperatorMode> operatorModes;

    private LayerSelectionOperatorMode operatorMode;

    @Wire
    public LayerSelectionOperator(final List<LayerSelectionOperatorMode> operatorModes) {
        this.operatorModes = operatorModes;
    }

    @Override
    public Pair<Entity<LayerChromosome>, Entity<LayerChromosome>> select(final List<Entity<LayerChromosome>> orderedPopulation) {
        return operatorMode.select(orderedPopulation);
    }

    @Override
    public void operatorChanged(final SelectionOperatorType operatorType) {
        Optional<LayerSelectionOperatorMode> operatorMode = operatorModes.stream()
                .filter(mode -> mode.getType().equals(operatorType))
                .findFirst();

        if (!operatorMode.isPresent()) {
            throw new RuntimeException("Could not find selection operator for this mode!");
        }

        this.operatorMode = operatorMode.get();
    }
}
