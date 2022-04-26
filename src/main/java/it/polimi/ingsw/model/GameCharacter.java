package it.polimi.ingsw.model;

import it.polimi.ingsw.model.effects.*;

/**
 * This class represents the character cards of the game.
 * For each one it's specified if it was already used, as well as its name, cost
 * and effect.
 */
public class GameCharacter {
    private int cost;
    private final String name;
    private final Effect effect;
    private boolean alreadyUsed;

    /**
     * Class constructor.
     *
     * @param cost          the cost of the card.
     * @param effect        the Effect of the character.
     * @param name          the name of the character.
     */
    public GameCharacter(int cost, Effect effect, String name) {
        this.cost = cost;
        this.effect = effect;
        this.name = name;
    }

    /**
     * Increases the cost of this character.
     */
    private void updateCost(){
        cost++;
        alreadyUsed = true;
    }

    /**
     * Returns the cost of this character.
     *
     * @return              the cost of this character.
     */
    public int getCost(){
        return cost;
    }

    /**
     * Returns the name of this character.
     *
     * @return              the name of this character.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the effect of this character.
     *
     * @return              the Effect of this character.
     */
    public Effect getEffect(){
        return effect;
    }

    /**
     * States whether this character has already been used or not.
     *
     * @return              a boolean whose value is:
     *                      <p>
     *                      -{@code true} if it was already used;
     *                      </p> <p>
     *                      -{@code false} if it wasn't already used.
     *                      </p>
     */
    public boolean isAlreadyUsed() {
        return alreadyUsed;
    }

    /**
     * Enables the use of this character's effect.
     *
     * @param board         the Board of the Game.
     * @param archiIndex    the selected Archipelago.
     * @param numOfStudents the number of Students.
     * @param studColors    the Colors of the Students.
     */
    public void useEffect(Board board, int archiIndex, int numOfStudents, Color...studColors) {
        if (!alreadyUsed) updateCost();
        effect.applyEffect(board, archiIndex, numOfStudents, studColors);
    }
}
