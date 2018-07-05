package imagenetic.scene.asset.voxel.genetic.function.selection;

public enum SelectionOperatorType {
    RANKING("Rangsor"),
    APTITUDE("Arányos"),
    TOURNAMENT("Versenyeztetés"),
    RANDOM("Véletlenszerű");

    private final String name;

    SelectionOperatorType(final String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
