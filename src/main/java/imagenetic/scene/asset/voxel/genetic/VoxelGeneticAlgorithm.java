package imagenetic.scene.asset.voxel.genetic;

import imagenetic.common.Config;
import imagenetic.common.algorithm.genetic.GeneticAlgorithm;
import imagenetic.gui.common.ImageSelectionListener;
import imagenetic.scene.asset.voxel.genetic.entity.LayerChromosome;
import imagenetic.scene.asset.voxel.genetic.function.LayerChromosomeCopier;
import imagenetic.scene.asset.voxel.genetic.function.LayerChromosomeCreator;
import imagenetic.scene.asset.voxel.genetic.function.LayerCriterionFunction;
import imagenetic.scene.asset.voxel.genetic.function.LayerCrossoverOperator;
import imagenetic.scene.asset.voxel.genetic.function.LayerFitnessFunction;
import imagenetic.scene.asset.voxel.genetic.function.LayerMutationOperator;
import imagenetic.scene.asset.voxel.genetic.function.LayerSelectionOperator;
import puppeteer.annotation.premade.Component;
import puppeteer.annotation.premade.Wire;

import java.awt.image.BufferedImage;

@Component
public class VoxelGeneticAlgorithm extends GeneticAlgorithm<LayerChromosome> implements ImageSelectionListener {

    public static final AlgorithmParameters PARAMETERS = new AlgorithmParameters(
            100,
            Config.DEF_POPULATION_COUNT,
            Config.DEF_POPULATION_SIZE,
            Config.DEF_ENTITY_LENGTH,
            Config.DEF_ENTITY_THICKNESS
    );

    @Wire
    public VoxelGeneticAlgorithm(final LayerFitnessFunction layerFitnessFunction) {
        super(
                layerFitnessFunction,
                new LayerCriterionFunction(),
                new LayerSelectionOperator(),
                new LayerCrossoverOperator(),
                new LayerMutationOperator(PARAMETERS),
                new LayerChromosomeCopier(),
                new LayerChromosomeCreator(PARAMETERS)
        );
    }

    @Override
    public void initialize(int populationCount) {
        ((LayerMutationOperator) mutationOperator).initialize();
        super.initialize(populationCount);

        PARAMETERS.changed = false;
    }

    public void initializePopulation() {
        initialize(PARAMETERS.populationCount);
    }


    @Override
    public void onImageSelect(final BufferedImage image) {
        PARAMETERS.changed = true;
    }
}