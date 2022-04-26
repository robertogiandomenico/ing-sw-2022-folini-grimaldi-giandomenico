package it.polimi.ingsw.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This class represents the players of the game.
 */
public class Player {
    private final String nickname;
    private int coins;
    private List<Assistant> cards;
    private final TowerColor towerColor;
    private Assistant discardPile;
    private final Wizard selectedWizard;
    private boolean canChooseAssistant;
    private boolean canMoveStudents;
    private boolean canBuyCharacter;
    private int maxSteps;
    private int selectedSteps;


    /**
     * Class constructor.
     *
     * @param nickname          the nickname of the Player.
     * @param towerColor        the TowerColor of the Player.
     * @param selectedWizard    the Wizard selected by the Player.
     */
    public Player(String nickname, TowerColor towerColor, Wizard selectedWizard) {
        this.nickname = nickname;
        this.towerColor = towerColor;
        this.selectedWizard = selectedWizard;
        coins = 1;
        initializeCards();
    }

    /**
     * Initializes the assistant cards.
     */
    private void initializeCards(){
        cards = new ArrayList<>(
                Arrays.asList(
                Assistant.CAT,
                Assistant.CHEETAH,
                Assistant.DOG,
                Assistant.EAGLE,
                Assistant.ELEPHANT,
                Assistant.FOX,
                Assistant.LIZARD,
                Assistant.OCTOPUS,
                Assistant.OSTRICH,
                Assistant.TURTLE
                )
        );
    }

    /**
     * Returns this player's nickname.
     *
     * @return                  the nickname of this Player.
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * Returns this player's coins.
     *
     * @return                  the coins of this Player.
     */
    public int getCoins() {
        return coins;
    }

    /**
     * Returns the color of this player's towers.
     *
     * @return                  the TowerColor of this Player.
     */
    public TowerColor getTowerColor() {
        return towerColor;
    }

    /**
     * Returns the last assistant card this player chose.
     *
     * @return                  the Assistant lastly chosen by this Player.
     */
    public Assistant getDiscardPile() {
        return discardPile;
    }

    /**
     * Returns this player's wizard.
     *
     * @return                  the Wizard chosen by this Player.
     */
    public Wizard getSelectedWizard() {
        return selectedWizard;
    }

    /**
     * Sets the variable that states whether this player can choose an assistant.
     *
     * @param canChooseAssistant    a boolean whose value is:
     *                              <p>
     *                              -{@code true} if the Player can choose an Assistant;
     *                              </p> <p>
     *                              -{@code false} if the Player can't choose an Assistant.
     *                              </p>
     */
    public void setCanChooseAssistant(boolean canChooseAssistant) {
        this.canChooseAssistant = canChooseAssistant;
    }

    /**
     * Sets the variable that states whether this player can move students.
     *
     * @param canMoveStudents       a boolean whose value is:
     *                              <p>
     *                              -{@code true} if the Player can move a Student;
     *                              </p> <p>
     *                              -{@code false} if the Player can't move a Student.
     *                              </p>
     */
    public void setCanMoveStudents(boolean canMoveStudents) {
        this.canMoveStudents = canMoveStudents;
    }

    /**
     * Sets the variable that states whether this player can buy a character.
     *
     * @param canBuyCharacter       a boolean whose value is:
     *                              <p>
     *                              -{@code true} if the Player can buy a GameCharacter;
     *                              </p> <p>
     *                              -{@code false} if the Player can't buy a GameCharacter.
     *                              </p>
     */
    public void setCanBuyCharacter(boolean canBuyCharacter) {
        this.canBuyCharacter = canBuyCharacter;
    }

    /**
     * Gives a coin to this player.
     */
    public void addCoin(){
        coins++;
    }

    /**
     * Removes the specified amount of coins from this player.
     *
     * @param tot               the coins to be removed.
     * @return                  the coins removed.
     */
    public int removeCoins(int tot){
        coins = Math.max(coins - tot, 0);
        return tot;
    }

    /**
     * Uses an assistant card.
     * The card is put into the discard pile.
     *
     * @param assistant         the Assistant chosen by the Player.
     */
    public void chooseAssistant(Assistant assistant){
        cards.remove(assistant);
        discardPile = assistant;
        maxSteps = discardPile.getMaxMNSteps();
    }

    /**
     * Sets Mother Nature additional steps
     * ({@link it.polimi.ingsw.model.effects.MagicMailmanEffect MagicMailman Effect}).
     */
    public void setAdditionalSteps() {
        maxSteps += 2;
    }

    /**
     * States whether this player can choose an assistant or not.
     *
     * @return                  a boolean whose value is:
     *                          <p>
     *                          -{@code true} if this Player can choose an Assistant;
     *                          </p>
     *                          -{@code false} if this Player can't choose an Assistant.
     */
    public boolean getCanChooseAssistant() {
        return canChooseAssistant;
    }

    /**
     * States whether this player can move students or not.
     *
     * @return                  a boolean whose value is:
     *                          <p>
     *                          -{@code true} if this Player can move Students;
     *                          </p> <p>
     *                          -{@code false} if this Player can't move Students.
     *                          </p>
     */
    public boolean getCanMoveStudents() {
        return canMoveStudents;
    }

    /**
     * States whether this player can buy a character or not.
     *
     * @return                  a boolean whose value is:
     *                          <p>
     *                          -{@code true} if this Player can buy a GameCharacter;
     *                          </p> <p>
     *                          -{@code false} if this Player can't buy a GameCharacter.
     *                          </p>
     */
    public boolean getCanBuyCharacter() {
        return canBuyCharacter;
    }

    /**
     * Returns this player's assistant cards.
     *
     * @return                  the Assistant List of this Player's cards.
     */
    public List<Assistant> getCards() {
        return cards;
    }

    /**
     * Returns the maximum number of Mother Nature steps.
     *
     * @return                  the maximum number of Mother Nature steps.
     */
    public int getMaxSteps() {
        return maxSteps;
    }

    /**
     * Returns the number of Mother Nature steps selected by this player.
     *
     * @return                  the number of steps.
     */
    public int getSelectedSteps() {
        return selectedSteps;
    }

    /**
     * Sets the number of Mother Nature steps selected by this player.
     *
     * @param selectedSteps     the number of steps.
     */
    public void setSelectedSteps(int selectedSteps) {
        this.selectedSteps = selectedSteps;
    }
}