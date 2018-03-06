package imagenetic.scene.asset.voxel.genetic.function.crossover;

public enum CrossoverOperatorType {
    CONSISTENT("Egyenletes");

    private final String name;

    CrossoverOperatorType(final String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
