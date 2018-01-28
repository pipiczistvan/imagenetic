package imagenetic.common.event;

import java.io.File;

public interface OnSelected {
    void invoke(final File[] files);
}
