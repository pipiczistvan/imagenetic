package imagenetic.scene.asset.voxel.genetic.function.mutation;

import imagenetic.scene.asset.voxel.genetic.entity.LayerChromosome;
import imagenetic.scene.asset.voxel.genetic.entity.VoxelChromosome;
import puppeteer.annotation.premade.Component;

import java.util.Random;

import static imagenetic.scene.asset.voxel.genetic.function.mutation.MutationOperatorType.SCRAMBLE;

@Component
public class ScrambleMode implements LayerMutationOperatorMode {

    private final Random random = new Random();

    @Override
    public void initialize() {
    }

    @Override
    public void mutate(final LayerChromosome genotype) {
        for (VoxelChromosome voxelChromosome : genotype.voxelChromosomes) {
            if (voxelChromosome.fitness != 1) {
                int index = random.nextInt(6);
                int x = voxelChromosome.position.x;
                int y = voxelChromosome.position.y;
                int z = voxelChromosome.position.z;

                switch (index) {
                    case 0:
                        voxelChromosome.position.set(x, y, z);
                        break;
                    case 1:
                        voxelChromosome.position.set(x, z, y);
                        break;
                    case 2:
                        voxelChromosome.position.set(y, x, z);
                        break;
                    case 3:
                        voxelChromosome.position.set(y, z, x);
                        break;
                    case 4:
                        voxelChromosome.position.set(z, x, y);
                        break;
                    case 5:
                        voxelChromosome.position.set(z, y, x);
                        break;
                }
            }
        }
    }

    @Override
    public MutationOperatorType getType() {
        return SCRAMBLE;
    }
}
