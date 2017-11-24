package imagenetic.common.algorithm.genetic;

import imagenetic.common.algorithm.genetic.entity.Entity;
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

    private final FitnessFunction<T> fitnessFunction;
    private final CriterionFunction<T> criterionFunction;
    private final SelectionOperator<T> selectionOperator;
    private final CrossoverOperator<T> crossoverOperator;
    private final MutationOperator<T> mutationOperator;
    private final Random random = new Random();

    private int numberOfGenerations;

    public GeneticAlgorithm(FitnessFunction<T> fitnessFunction, CriterionFunction<T> criterionFunction,
                            SelectionOperator<T> selectionOperator, CrossoverOperator<T> crossoverOperator,
                            MutationOperator<T> mutationOperator) {
        this.fitnessFunction = fitnessFunction;
        this.criterionFunction = criterionFunction;
        this.selectionOperator = selectionOperator;
        this.crossoverOperator = crossoverOperator;
        this.mutationOperator = mutationOperator;
    }

    public List<T> execute(Collection<T> genoTypes, float mutationRate, float elitismRate) {
        numberOfGenerations = 0;
        List<Entity<T>> population = createSortedPopulation(genoTypes);

        while (!criterionFunction.matches(population)) {
            List<T> children = nextGeneration(population, mutationRate, elitismRate);
            population = createSortedPopulation(children);
            numberOfGenerations++;
        }

        return population.stream().map(Entity::getGenoType).collect(Collectors.toList());
    }

    public List<T> nextGeneration(List<Entity<T>> population, float mutationRate, float elitismRate) {
        int populationSize = population.size();
        List<Entity<T>> bestParents = population.subList(0, populationSize / 2);

        List<T> newGeneration = new ArrayList<>(bestParents.stream()
                .filter(parent -> parent.getFitness() >= elitismRate)
                .map(Entity::getGenoType)
                .collect(Collectors.toList()));

        while (newGeneration.size() < population.size()) {
            Pair<Entity<T>, Entity<T>> parents = selectionOperator.select(bestParents);

            T child = crossoverOperator.crossover(parents);
            if (random.nextFloat() >= mutationRate) {
                child = mutationOperator.mutate(child);
            }

            newGeneration.add(child);
        }

        return newGeneration;
    }

    public int getNumberOfGenerations() {
        return numberOfGenerations;
    }

    public List<Entity<T>> createSortedPopulation(Collection<T> genoTypes) {
        return genoTypes.stream()
                .map(g -> new Entity<>(g, fitnessFunction))
                .sorted()
                .collect(Collectors.toList());
    }

}
