package imagenetic.scene.asset.line;

import imagenetic.common.Bridge;
import imagenetic.common.Config;
import imagenetic.common.algorithm.genetic.entity.Entity;
import imagenetic.common.api.SceneSide;
import imagenetic.scene.asset.line.genetic.LineGeneticAlgorithm;
import imagenetic.scene.asset.line.genetic.entity.LayerChromosome;
import imagenetic.scene.asset.line.genetic.entity.LineChromosome;
import org.joml.Vector3f;
import piengine.object.asset.domain.WorldAsset;
import piengine.object.asset.manager.AssetManager;
import piengine.object.asset.plan.WorldRenderAssetContext;
import piengine.object.asset.plan.WorldRenderAssetContextBuilder;
import piengine.object.model.manager.ModelManager;
import piengine.visual.image.manager.ImageManager;
import puppeteer.annotation.premade.Wire;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class LineAsset extends WorldAsset<LineAssetArgument> implements SceneSide {

    private static final float STICK_SCALE = 2.5f;

    private List<LayerChromosome> chromosomes = new ArrayList<>();
    private List<Entity<LayerChromosome>> population = new ArrayList<>();

    private final LineModelManager lineModelManager;
    private LineGeneticAlgorithm geneticAlgorithm;

    private float elapsedTime = 0;

    private boolean populationConfigChanged = false;
    private int populationCount = Config.DEF_POPULATION_COUNT;
    private int populationSize = Config.DEF_POPULATION_SIZE;
    private float lineLength = Config.DEF_ENTITY_LENGTH;
    private float lineThickness = Config.DEF_ENTITY_THICKNESS;
    private boolean imageChanged = false;
    private BufferedImage image;

    private boolean interpolated = false;

    private boolean paused = true;
    private int speed = Config.DEF_SPEED;

    @Wire
    public LineAsset(final AssetManager assetManager, final ModelManager modelManager, final ImageManager imageManager) {
        super(assetManager);

        this.lineModelManager = new LineModelManager(this, modelManager, imageManager);
    }

    @Override
    public void initialize() {
        geneticAlgorithm = new LineGeneticAlgorithm(arguments.geneticAlgorithmSize);

        chromosomes.clear();
        setupChromosomePopulation();
        geneticAlgorithm.initialize();

        lineModelManager.initialize();
    }

    private void setupChromosomePopulation() {
        if (chromosomes.size() > populationCount) {
            chromosomes.subList(populationCount, chromosomes.size()).clear();
        } else if (chromosomes.size() < populationCount) {
            while (chromosomes.size() < populationCount) {
                chromosomes.add(new LayerChromosome(new ArrayList<>()));
            }
        }

        for (LayerChromosome layer : chromosomes) {
            setupChromosomeLayer(layer.lineChromosomes);
        }

        population = geneticAlgorithm.createSortedPopulation(chromosomes);
        lineModelManager.setActualPopulation(population);
    }

    private void setupChromosomeLayer(final List<LineChromosome> lineChromosomes) {
        if (lineChromosomes.size() > populationSize) {
            lineChromosomes.subList(populationSize, lineChromosomes.size()).clear();
        } else if (lineChromosomes.size() < populationSize) {
            while (lineChromosomes.size() < populationSize) {
                lineChromosomes.add(new LineChromosome(
                        new Vector3f(),
                        new Vector3f(),
                        new Vector3f()
                ));
            }
        }

        for (LineChromosome lineChromosome : lineChromosomes) {
            lineChromosome.scale.set(
                    lineThickness * STICK_SCALE / 10f,
                    lineLength * STICK_SCALE,
                    lineThickness * STICK_SCALE / 10f);
        }
    }

    @Override
    public void update(final float delta) {
        if (imageChanged) {
            imageChanged = false;
            geneticAlgorithm.setImage(image);
        }

        if (populationConfigChanged) {
            populationConfigChanged = false;
            setupChromosomePopulation();
            if (paused) {
                lineModelManager.synchronize();
            }
        }

        if (!paused) {
            if (elapsedTime > 1) {
                elapsedTime = 0;
                evaluateGeneticAlgorithm();
                Bridge.frameSide.updateLabels();
            } else {
                elapsedTime += delta * speed;
            }

            if (interpolated) {
                lineModelManager.interpolate(delta);
            } else {
                lineModelManager.synchronize();
            }
        }
    }

    public void setViewScale(final float viewScale) {
        this.lineModelManager.setViewScale(viewScale);
        this.populationConfigChanged = true;
    }

    @Override
    public void showAll(final boolean showAll) {
        this.lineModelManager.setShowAll(showAll);
        this.populationConfigChanged = true;
    }

    @Override
    public void setThreshold(final float threshold) {
        this.lineModelManager.setThreshold(threshold);
        this.populationConfigChanged = true;
    }

    @Override
    public void setPopulationCount(final int populationCount) {
        this.populationCount = populationCount;
        this.populationConfigChanged = true;
    }

    @Override
    public void setPopulationSize(final int populationSize) {
        this.populationSize = populationSize;
        this.populationConfigChanged = true;
    }

    @Override
    public void setImage(final BufferedImage image) {
        this.image = image;
        this.imageChanged = true;
    }

    @Override
    public int getNumberOfGenerations() {
        return geneticAlgorithm.getNumberOfGenerations();
    }

    @Override
    public float getAverageFitness() {
        return geneticAlgorithm.getAverageFitness();
    }

    @Override
    public float getBestFitness() {
        return geneticAlgorithm.getBestFitness();
    }

    @Override
    public boolean isAlgorithmPaused() {
        return paused;
    }

    @Override
    public void setAlgorithmStatus(final boolean paused) {
        this.paused = paused;
    }

    @Override
    public void setAlgorithmSpeed(final int speed) {
        this.speed = speed;
    }

    @Override
    public void setThickness(final float thickness) {
        this.lineThickness = thickness;
        this.populationConfigChanged = true;
    }

    @Override
    public void setLength(final float length) {
        this.lineLength = length;
        this.populationConfigChanged = true;
    }

    @Override
    public void setInterpolated(final boolean interpolated) {
        this.interpolated = interpolated;
    }

    private void evaluateGeneticAlgorithm() {
        chromosomes = geneticAlgorithm.nextGeneration(population, 1f, 2f);
        population = geneticAlgorithm.createSortedPopulation(chromosomes);
        lineModelManager.setActualPopulation(population);
    }

    @Override
    public WorldRenderAssetContext getAssetContext() {
        return WorldRenderAssetContextBuilder.create()
                .loadModels(lineModelManager.getLineModels())
                .build();
    }
}
