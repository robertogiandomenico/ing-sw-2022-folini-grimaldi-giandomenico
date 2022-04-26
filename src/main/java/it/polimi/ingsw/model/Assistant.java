package it.polimi.ingsw.model;

/**
 * This enumeration lists the assistant cards of the game.
 * For each one, the maximum number of steps Mother Nature can take and the card
 * weight are specified.
 */
public enum Assistant {
    EAGLE(4, 2), DOG(8, 4), ELEPHANT(9, 5), CAT(3, 2),
    CHEETAH(1, 1), LIZARD(6, 3), OCTOPUS(7, 4), TURTLE(10, 5),
    FOX(5, 3), OSTRICH(2, 1);

    private final int weight;
    private final int maxMNSteps;

    /**
     * Constructs each assistant.
     *
     * @param weight           the weight of this card.
     * @param maxMNSteps       the maximum number of Mother Nature steps.
     */
    Assistant(int weight, int maxMNSteps) {
        this.weight = weight;
        this.maxMNSteps = maxMNSteps;
    }

    /**
     * Returns the weight of this assistant card.
     *
     * @return                 the weight of this card.
     */
    public int getWeight() {
        return weight;
    }

    /**
     * Returns the maximum number of steps Mother Nature can take among the islands.
     *
     * @return                 the maximum number of Mother Nature steps.
     */
    public int getMaxMNSteps() {
        return maxMNSteps;
    }
}
