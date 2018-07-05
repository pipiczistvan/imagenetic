package imagenetic.scene.asset.voxel.genetic.function.crossover;

public enum CrossoverOperatorType {
    ONE_POINT("Egypontos"),
    MULTI_POINT("Többpontos"),
    UNIFORM("Egyenletes"),
    AVERAGE("Átlag alapú");

    private final String name;

    CrossoverOperatorType(final String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
