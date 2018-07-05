package imagenetic.scene.asset.voxel.genetic.function.mutation;

import imagenetic.common.algorithm.genetic.function.MutationOperator;
import imagenetic.gui.common.api.settings.MutationOperatorChangedListener;
import imagenetic.scene.asset.voxel.genetic.entity.LayerChromosome;
import piengine.core.base.api.Initializable;
import puppeteer.annotation.premade.Component;
import puppeteer.annotation.premade.Wire;

import java.util.List;
import java.util.Optional;

@Component
public class LayerMutationOperator implements MutationOperator<LayerChromosome>, Initializable, MutationOperatorChangedListener {

    private final List<LayerMutationOperatorMode> operatorModes;

    private LayerMutationOperatorMode operatorMode;

    @Wire
    public LayerMutationOperator(final List<LayerMutationOperatorMode> operatorModes) {
        this.operatorModes = operatorModes;
    }

    @Override
    public void initialize() {
        operatorMode.initialize();
    }

    @Override
    public void mutate(final LayerChromosome genotype) {
        operatorMode.mutate(genotype);
    }

    @Override
    public void operatorChanged(final MutationOperatorType operatorType) {
        Optional<LayerMutationOperatorMode> operatorMode = operatorModes.stream()
                .filter(mode -> mode.getType().equals(operatorType))
                .findFirst();

        if (!operatorMode.isPresent()) {
            throw new RuntimeException("Could not find selection operator for this mode!");
        }

        this.operatorMode = operatorMode.get();
        this.operatorMode.initialize();
    }
}
