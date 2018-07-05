package imagenetic.scene.asset.voxel.genetic.function.mutation;

import imagenetic.scene.asset.voxel.genetic.entity.LayerChromosome;
import imagenetic.scene.asset.voxel.genetic.entity.VoxelChromosome;
import org.joml.Vector3i;
import puppeteer.annotation.premade.Component;

import static imagenetic.scene.asset.voxel.genetic.function.mutation.MutationOperatorType.INVERSION;

@Component
public class InversionMode implements LayerMutationOperatorMode {

    @Override
    public void initialize() {
    }

    @Override
    public void mutate(final LayerChromosome genotype) {
        for (VoxelChromosome voxelChromosome : genotype.voxelChromosomes) {
            if (voxelChromosome.fitness != 1) {
                voxelChromosome.position.set(new Vector3i(voxelChromosome.position).negate());
            }
        }
    }

    @Override
    public MutationOperatorType getType() {
        return INVERSION;
    }
}
