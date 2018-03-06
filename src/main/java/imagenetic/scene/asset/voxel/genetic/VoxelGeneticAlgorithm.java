package imagenetic.scene.asset.voxel.genetic;

import imagenetic.common.Config;
import imagenetic.common.algorithm.genetic.GeneticAlgorithm;
import imagenetic.gui.common.api.image.ImageSelectionListener;
import imagenetic.scene.asset.voxel.genetic.entity.LayerChromosome;
import imagenetic.scene.asset.voxel.genetic.function.LayerChromosomeCopier;
import imagenetic.scene.asset.voxel.genetic.function.LayerChromosomeCreator;
import imagenetic.scene.asset.voxel.genetic.function.LayerCriterionFunction;
import imagenetic.scene.asset.voxel.genetic.function.LayerFitnessFunction;
import imagenetic.scene.asset.voxel.genetic.function.crossover.LayerCrossoverOperator;
import imagenetic.scene.asset.voxel.genetic.function.mutation.LayerMutationOperator;
import imagenetic.scene.asset.voxel.genetic.function.selection.LayerSelectionOperator;
import puppeteer.annotation.premade.Component;
import puppeteer.annotation.premade.Wire;

import java.awt.image.BufferedImage;

@Component
public class VoxelGeneticAlgorithm extends GeneticAlgorithm<LayerChromosome> implements ImageSelectionListener {

    public static final AlgorithmParameters PARAMETERS = new AlgorithmParameters(
            100,
            Config.DEF_POPULATION_SIZE,
            Config.DEF_ENTITY_LENGTH,
            Config.DEF_ENTITY_THICKNESS
    );

    @Wire
    public VoxelGeneticAlgorithm(final LayerFitnessFunction layerFitnessFunction, final LayerSelectionOperator layerSelectionOperator,
                                 final LayerCrossoverOperator layerCrossoverOperator, final LayerMutationOperator layerMutationOperator) {
        super(
                layerFitnessFunction,
                new LayerCriterionFunction(),
                layerSelectionOperator,
                layerCrossoverOperator,
                layerMutationOperator,
                new LayerChromosomeCopier(),
                new LayerChromosomeCreator(PARAMETERS)
        );
    }

    @Override
    public void initialize() {
        ((LayerMutationOperator) mutationOperator).initialize();
        super.initialize();

        PARAMETERS.changed = false;
    }

    @Override
    public void onImageSelect(final BufferedImage image) {
        PARAMETERS.changed = true;
    }
}
