package algorithm;

public enum FunctionType {
    MATYAS("Matyas"),
    ACKLEY("Ackley"),
    BOOTH("Booth"),
    BEALE("Beale"),
    GOLDSTEIN_PRICE("Goldstein-Price");

    private String name;

    public String getName() {
        return name;
    }

    FunctionType(String name) {
        this.name = name;
    }

}