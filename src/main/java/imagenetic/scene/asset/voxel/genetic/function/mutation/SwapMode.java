package imagenetic.scene.asset.voxel.genetic.function.mutation;

import imagenetic.gui.common.api.settings.MultiCheckChangedListener;
import imagenetic.scene.asset.voxel.genetic.entity.LayerChromosome;
import imagenetic.scene.asset.voxel.genetic.entity.VoxelChromosome;
import puppeteer.annotation.premade.Component;

import java.util.Random;

import static imagenetic.scene.asset.voxel.genetic.function.mutation.MutationOperatorType.SWAP;

@Component
public class SwapMode implements LayerMutationOperatorMode, MultiCheckChangedListener {

    private final Random random = new Random();
    private boolean multiCheck = false;

    @Override
    public void initialize() {
        multiCheck = false;
    }

    @Override
    public void mutate(final LayerChromosome genotype) {
        for (VoxelChromosome voxelChromosome : genotype.voxelChromosomes) {
            if (voxelChromosome.fitness != 1) {
                int index;
                if (!multiCheck) {
                    index = random.nextInt(1);
                } else {
                    index = random.nextInt(3);
                }
                int x = voxelChromosome.position.x;
                int y = voxelChromosome.position.y;
                int z = voxelChromosome.position.z;

                switch (index) {
                    case 0:
                        voxelChromosome.position.set(y, x, z);
                        break;
                    case 1:
                        voxelChromosome.position.set(x, z, y);
                        break;
                    case 2:
                        voxelChromosome.position.set(z, y, x);
                        break;
                }
            }
        }
    }

    @Override
    public MutationOperatorType getType() {
        return SWAP;
    }

    @Override
    public void onMultiCheckChanged(final boolean checked) {
        multiCheck = checked;
    }
}
