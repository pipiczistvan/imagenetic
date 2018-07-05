package imagenetic.gui.common.api;

public interface ViewChangedListener {
    void viewChanged(VIEW_TYPE view, boolean zoomReset);

    enum VIEW_TYPE {
        NONE, FRONT, SIDE
    }
}
