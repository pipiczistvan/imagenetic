package imagenetic.gui.common.api.settings;

import imagenetic.scene.asset.voxel.genetic.function.crossover.CrossoverOperatorType;

public interface CrossoverOperatorChangedListener {
    void operatorChanged(final CrossoverOperatorType operatorType);
}
