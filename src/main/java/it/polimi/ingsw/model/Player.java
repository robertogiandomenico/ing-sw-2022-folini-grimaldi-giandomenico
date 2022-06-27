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
    private boolean currentPlayer;
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
     * Sets the variable that states whether this player can move students.
     *
     * @param currentPlayer       a boolean whose value is:
     *                              <p>
     *                              -{@code true} if the Player is the current player;
     *                              </p> <p>
     *                              -{@code false} otherwise.
     *                              </p>
     */
    public void setCurrentPlayer(boolean currentPlayer) {
        this.currentPlayer = currentPlayer;
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
     * States whether this player can move students or not.
     *
     * @return                  a boolean whose value is:
     *                          <p>
     *                          -{@code true} if this Player can move Students;
     *                          </p> <p>
     *                          -{@code false} otherwise.
     *                          </p>
     */
    public boolean isCurrentPlayer() {
        return currentPlayer;
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