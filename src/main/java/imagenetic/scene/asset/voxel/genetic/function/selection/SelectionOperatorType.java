package imagenetic.scene.asset.voxel.genetic.function.selection;

public enum SelectionOperatorType {
    RANKING("Rangsor"),
    TOURNAMENT("Versenyeztetés");

    private final String name;

    SelectionOperatorType(final String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
