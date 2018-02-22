package imagenetic.common.algorithm.genetic;

import imagenetic.common.algorithm.genetic.entity.Entity;
import imagenetic.common.algorithm.genetic.function.ChromosomeCopier;
import imagenetic.common.algorithm.genetic.function.ChromosomeCreator;
import imagenetic.common.algorithm.genetic.function.CriterionFunction;
import imagenetic.common.algorithm.genetic.function.CrossoverOperator;
import imagenetic.common.algorithm.genetic.function.FitnessFunction;
import imagenetic.common.algorithm.genetic.function.MutationOperator;
import imagenetic.common.algorithm.genetic.function.SelectionOperator;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public abstract class GeneticAlgorithm<T> {

    protected final FitnessFunction<T> fitnessFunction;
    private final CriterionFunction<T> criterionFunction;
    private final SelectionOperator<T> selectionOperator;
    private final CrossoverOperator<T> crossoverOperator;
    protected final MutationOperator<T> mutationOperator;

    private final ChromosomeCopier<T> chromosomeCopier;
    private final ChromosomeCreator<T> chromosomeCreator;
    private final Random random = new Random();

    private final List<Generation<T>> generations = new ArrayList<>();
    private Generation<T> currentGeneration = null;
    private Entity<T> bestElement = null;

    private float averageFitness = 0;
    private float bestFitness = 0;

    public GeneticAlgorithm(final FitnessFunction<T> fitnessFunction, final CriterionFunction<T> criterionFunction,
                            final SelectionOperator<T> selectionOperator, final CrossoverOperator<T> crossoverOperator,
                            final MutationOperator<T> mutationOperator,
                            final ChromosomeCopier<T> chromosomeCopier, final ChromosomeCreator<T> chromosomeCreator) {
        this.fitnessFunction = fitnessFunction;
        this.criterionFunction = criterionFunction;
        this.selectionOperator = selectionOperator;
        this.crossoverOperator = crossoverOperator;
        this.mutationOperator = mutationOperator;

        this.chromosomeCopier = chromosomeCopier;
        this.chromosomeCreator = chromosomeCreator;
    }

    public void initialize(final int populationCount) {
        bestElement = null;
        averageFitness = 0;
        bestFitness = 0;

        generations.clear();
        addGeneration(new Generation<>(createSortedPopulation(createGenotypes(populationCount))));
    }

    public List<T> execute(final float mutationRate, final float elitismRate) {
        Generation<T> lastGeneration = generations.get(generations.size() - 1);

        while (!criterionFunction.matches(lastGeneration.population)) {
            nextGeneration(mutationRate, elitismRate);
        }

        return lastGeneration.population.stream().map(Entity::getGenoType).collect(Collectors.toList());
    }

    public void nextGeneration(final float mutationRate, final float elitismRate) {
        List<Entity<T>> currentPopulation = currentGeneration.population;

        if (bestElement == null || bestElement.getFitness() <= currentPopulation.get(0).getFitness()) {
            bestElement = currentPopulation.get(0);
        }

        // ELITISM
        List<T> chromosomes = currentPopulation.stream()
                .filter(parent -> parent.getFitness() >= elitismRate)
                .map(e -> chromosomeCopier.copy(e.getGenoType()))
                .collect(Collectors.toList());
        chromosomes.add(chromosomeCopier.copy(bestElement.getGenoType()));

        // CROSSOVER
        while (chromosomes.size() < currentPopulation.size()) {
            Pair<Entity<T>, Entity<T>> parents = selectionOperator.select(currentPopulation);
            T child = crossoverOperator.crossover(parents);

            chromosomes.add(child);
        }

        // MUTATION
        List<Entity<T>> newPopulation = createSortedPopulation(chromosomes);
        for (int i = 0; i < newPopulation.size(); i++) {
            if (random.nextFloat() <= mutationRate) {
                Entity<T> entity = newPopulation.get(i);

                mutationOperator.mutate(entity.getGenoType());
                newPopulation.set(i, new Entity<>(entity.getGenoType(), fitnessFunction));
            }
        }

        addGeneration(new Generation<>(newPopulation));
    }

    public int getNumberOfGenerations() {
        return generations.size() - 1;
    }

    public float getAverageFitness() {
        return averageFitness;
    }

    public float getBestFitness() {
        return bestFitness;
    }

    public List<Generation<T>> getGenerations() {
        return generations;
    }

    public Generation<T> getCurrentGeneration() {
        return currentGeneration;
    }

    private List<Entity<T>> createSortedPopulation(final Collection<T> genoTypes) {
        List<Entity<T>> sortedPopulation = genoTypes.stream()
                .map(g -> new Entity<>(g, fitnessFunction))
                .sorted()
                .collect(Collectors.toList());

        bestFitness = sortedPopulation.get(0).getFitness();
        averageFitness = (float) sortedPopulation.stream().mapToDouble(Entity::getFitness).average().getAsDouble();

        return sortedPopulation;
    }

    private List<T> createGenotypes(final int populationCount) {
        List<T> genotypes = new ArrayList<>();
        for (int i = 0; i < populationCount; i++) {
            genotypes.add(chromosomeCreator.create());
        }

        return genotypes;
    }

    private void addGeneration(final Generation<T> generation) {
        generations.add(generation);
        currentGeneration = generation;
    }
}
