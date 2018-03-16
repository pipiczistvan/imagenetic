package imagenetic.scene.asset.voxel.genetic.function.mutation;

public enum MutationOperatorType {
    RANDOM("Véletlenszerű"),
    SWAP("Géncsere"),
    SCRAMBLE("Összekeverés"),
    INVERSION("Inverzió");

    private final String name;

    MutationOperatorType(final String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
