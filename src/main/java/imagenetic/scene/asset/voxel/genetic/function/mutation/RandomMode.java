package imagenetic.scene.asset.voxel.genetic.function.mutation;

import imagenetic.gui.common.api.settings.MultiCheckChangedListener;
import imagenetic.scene.asset.voxel.genetic.entity.LayerChromosome;
import imagenetic.scene.asset.voxel.genetic.entity.VoxelChromosome;
import puppeteer.annotation.premade.Component;

import java.util.Random;

import static imagenetic.common.Config.VOXEL_RESOLUTION;
import static imagenetic.scene.asset.voxel.genetic.function.mutation.MutationOperatorType.RANDOM;

@Component
public class RandomMode implements LayerMutationOperatorMode, MultiCheckChangedListener {

    private int maxSize;
    private int halfMaxSize;
    private final Random random = new Random();
    private boolean multiCheck = false;

    @Override
    public void initialize() {
        this.maxSize = VOXEL_RESOLUTION;
        this.halfMaxSize = maxSize / 2;
    }

    @Override
    public void mutate(final LayerChromosome genotype) {
        for (VoxelChromosome voxelChromosome : genotype.voxelChromosomes) {
            if (voxelChromosome.fitness != 1) {
                voxelChromosome.position.set(
                        (int) (random.nextFloat() * maxSize - halfMaxSize),
                        (int) (random.nextFloat() * maxSize - halfMaxSize),
                        multiCheck ? (int) (random.nextFloat() * maxSize - halfMaxSize) : 0
                );
            }
        }
    }

    @Override
    public void onMultiCheckChanged(boolean checked) {
        multiCheck = checked;
    }

    @Override
    public MutationOperatorType getType() {
        return RANDOM;
    }
}
