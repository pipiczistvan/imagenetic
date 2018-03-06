package imagenetic.scene.asset.voxel.genetic.function.crossover;

import imagenetic.common.algorithm.genetic.entity.Entity;
import imagenetic.common.algorithm.genetic.function.CrossoverOperator;
import imagenetic.gui.common.api.settings.CrossoverOperatorChangedListener;
import imagenetic.scene.asset.voxel.genetic.entity.LayerChromosome;
import javafx.util.Pair;
import puppeteer.annotation.premade.Component;
import puppeteer.annotation.premade.Wire;

import java.util.List;
import java.util.Optional;

@Component
public class LayerCrossoverOperator implements CrossoverOperator<LayerChromosome>, CrossoverOperatorChangedListener {

    private final List<LayerCrossoverOperatorMode> operatorModes;

    private LayerCrossoverOperatorMode operatorMode;

    @Wire
    public LayerCrossoverOperator(final List<LayerCrossoverOperatorMode> operatorModes) {
        this.operatorModes = operatorModes;
    }

    @Override
    public LayerChromosome crossover(final Pair<Entity<LayerChromosome>, Entity<LayerChromosome>> parents) {
        return operatorMode.crossover(parents);
    }

    @Override
    public void operatorChanged(final CrossoverOperatorType operatorType) {
        Optional<LayerCrossoverOperatorMode> operatorMode = operatorModes.stream()
                .filter(mode -> mode.getType().equals(operatorType))
                .findFirst();

        if (!operatorMode.isPresent()) {
            throw new RuntimeException("Could not find selection operator for this mode!");
        }

        this.operatorMode = operatorMode.get();
    }
}
