package imagenetic.asset.stick;

import imagenetic.algorithm.entity.Entity;
import imagenetic.algorithm.function.CrossOverOperator;
import javafx.util.Pair;

public class StickCrossOverOperator implements CrossOverOperator<StickChromosome> {

    @Override
    public StickChromosome crossOver(Pair<Entity<StickChromosome>, Entity<StickChromosome>> parents) {
        return parents.getKey().getGenoType();
    }
}
