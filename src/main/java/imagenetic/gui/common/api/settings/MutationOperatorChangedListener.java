package imagenetic.gui.common.api.settings;

import imagenetic.scene.asset.voxel.genetic.function.mutation.MutationOperatorType;

public interface MutationOperatorChangedListener {
    void operatorChanged(final MutationOperatorType operatorType);
}
