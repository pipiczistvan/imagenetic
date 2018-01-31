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

    protected final FitnessFunction<T> fitnessFunction;
    private final CriterionFunction<T> criterionFunction;
    private final SelectionOperator<T> selectionOperator;
    private final CrossoverOperator<T> crossoverOperator;
    private final MutationOperator<T> mutationOperator;
    private final Random random = new Random();

    private Entity<T> bestElement = null;

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
        List<Entity<T>> sortedPopulation = createSortedPopulation(genoTypes);

        while (!criterionFunction.matches(sortedPopulation)) {
            List<T> children = nextGeneration(sortedPopulation, mutationRate, elitismRate);
            sortedPopulation = createSortedPopulation(children);
            numberOfGenerations++;
        }

        return sortedPopulation.stream().map(Entity::getGenoType).collect(Collectors.toList());
    }

    public List<T> nextGeneration(List<Entity<T>> sortedPopulation, float mutationRate, float elitismRate) {
        if (bestElement == null || bestElement.getFitness() <= sortedPopulation.get(0).getFitness()) {
            bestElement = sortedPopulation.get(0);
        }

        List<T> newGeneration = new ArrayList<>(sortedPopulation.stream()
                .filter(parent -> parent.getFitness() >= elitismRate)
                .map(Entity::getGenoType)
                .collect(Collectors.toList()));


        newGeneration.add(bestElement.getGenoType());

        while (newGeneration.size() < sortedPopulation.size()) {
            Pair<Entity<T>, Entity<T>> parents = selectionOperator.select(sortedPopulation);
            T child = crossoverOperator.crossover(parents);

            newGeneration.add(child);
        }

        for (T element : newGeneration) {
            Entity<T> calculatedChild = new Entity<>(element, fitnessFunction);
            if (random.nextFloat() <= mutationRate) {
                mutationOperator.mutate(calculatedChild.getGenoType());
            }
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
