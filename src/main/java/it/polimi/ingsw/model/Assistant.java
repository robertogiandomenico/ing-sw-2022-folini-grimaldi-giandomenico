package it.polimi.ingsw.model;

public enum Assistant {
    EAGLE(4, 2), DOG(8, 4), ELEPHANT(9, 5), CAT(3, 2),
    CHEETAH(1, 1), LIZARD(6, 3), OCTOPUS(7, 4), TURTLE(10, 5),
    FOX(5, 3), OSTRICH(2, 1);

    private final int weight;
    private final int maxMNSteps;

    Assistant(int weight, int maxMNSteps) {
        this.weight = weight;
        this.maxMNSteps = maxMNSteps;
    }

    public int getWeight() {
        return weight;
    }

    public int getMaxMNSteps() {
        return maxMNSteps;
    }
}
