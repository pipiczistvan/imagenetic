package imagenetic.asset.stick;

import imagenetic.algorithm.entity.Entity;
import imagenetic.algorithm.function.SelectionOperator;
import javafx.util.Pair;
import org.joml.Vector3f;

import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

public class StickSelectionOperator implements SelectionOperator<StickChromosome> {

    private final Random random = new Random();

    @Override
    public Pair<Entity<StickChromosome>, Entity<StickChromosome>> select(List<Entity<StickChromosome>> orderedPopulation) {
        int index = IntStream.range(0, 1).map(a -> random.nextInt(orderedPopulation.size())).min().getAsInt();
        Entity<StickChromosome> parent1 = orderedPopulation.get(index);
        Entity<StickChromosome> parent2 = parent1;

        if (orderedPopulation.size() > 1) {
            float minLength = 10000;//todo: ugly
            Vector3f sub = new Vector3f();

            for (Entity<StickChromosome> c : orderedPopulation) {
                if (c.equals(parent1)) {
                    continue;
                }

                c.getGenoType().position.sub(parent1.getGenoType().position, sub);

                float length = sub.length();

                if (length < minLength) {
                    minLength = length;
                    parent2 = c;
                }
            }
        }

        return new Pair<>(parent1, parent2);
    }

}
