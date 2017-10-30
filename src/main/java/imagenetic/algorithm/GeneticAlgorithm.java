package imagenetic.algorithm;

import imagenetic.algorithm.entity.Entity;
import imagenetic.algorithm.function.CriterionFunction;
import imagenetic.algorithm.function.CrossOverOperator;
import imagenetic.algorithm.function.FitnessFunction;
import imagenetic.algorithm.function.MutationOperator;
import imagenetic.algorithm.function.SelectionOperator;
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
    private final CrossOverOperator<T> crossOverOperator;
    private final MutationOperator<T> mutationOperator;
    private final Random random = new Random();

    private int numberOfGenerations;

    public GeneticAlgorithm(FitnessFunction<T> fitnessFunction, CriterionFunction<T> criterionFunction,
                            SelectionOperator<T> selectionOperator, CrossOverOperator<T> crossOverOperator,
                            MutationOperator<T> mutationOperator) {
        this.fitnessFunction = fitnessFunction;
        this.criterionFunction = criterionFunction;
        this.selectionOperator = selectionOperator;
        this.crossOverOperator = crossOverOperator;
        this.mutationOperator = mutationOperator;
    }

    public List<T> execute(Collection<T> genoTypes, float mutationRate) {
        numberOfGenerations = 0;
        List<Entity<T>> population = createSortedPopulation(genoTypes);

        while (!criterionFunction.matches(population)) {
            List<T> children = nextGeneration(population, mutationRate);
            population = createSortedPopulation(children);
            numberOfGenerations++;
        }

        return population.stream().map(Entity::getGenoType).collect(Collectors.toList());
    }

    private List<T> nextGeneration(List<Entity<T>> population, float mutationRate) {
        int populationSize = population.size();
        List<Entity<T>> bestParents = population.subList(0, populationSize / 2);

        List<T> children = new ArrayList<>(bestParents.stream()
                .map(Entity::getGenoType)
                .collect(Collectors.toList()));

        while (children.size() < population.size()) {
            Pair<Entity<T>, Entity<T>> parents = selectionOperator.select(bestParents);

            T child = crossOverOperator.crossOver(parents);
            if (random.nextFloat() >= mutationRate) {
                child = mutationOperator.mutate(child);
            }

            children.add(child);
        }

        return children;
    }

    public int getNumberOfGenerations() {
        return numberOfGenerations;
    }

    private List<Entity<T>> createSortedPopulation(Collection<T> genoTypes) {
        return genoTypes.stream()
                .map(g -> new Entity<>(g, fitnessFunction))
                .sorted()
                .collect(Collectors.toList());
    }

}
