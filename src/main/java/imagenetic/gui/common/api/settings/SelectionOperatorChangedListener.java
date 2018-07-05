package imagenetic.gui.common.api.settings;

import imagenetic.scene.asset.voxel.genetic.function.selection.SelectionOperatorType;

public interface SelectionOperatorChangedListener {
    void operatorChanged(final SelectionOperatorType operatorType);
}
