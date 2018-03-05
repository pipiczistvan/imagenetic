package imagenetic.gui.common;

public interface ViewChangedListener {
    void viewChanged(VIEW_TYPE view, boolean zoomReset);

    enum VIEW_TYPE {
        NONE, FRONT, SIDE
    }
}
