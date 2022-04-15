package it.polimi.ingsw.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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


    public Player(String nickname, TowerColor towerColor, Wizard selectedWizard) {
        this.nickname = nickname;
        this.towerColor = towerColor;
        this.selectedWizard = selectedWizard;
        coins = 1;
        initializeCards();
    }

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

    public String getNickname() {
        return nickname;
    }

    public int getCoins() {
        return coins;
    }

    public TowerColor getTowerColor() {
        return towerColor;
    }

    public Assistant getDiscardPile() {
        return discardPile;
    }

    public Wizard getSelectedWizard() {
        return selectedWizard;
    }

    public void setCanChooseAssistant(boolean canChooseAssistant) {
        this.canChooseAssistant = canChooseAssistant;
    }

    public void setCanMoveStudents(boolean canMoveStudents) {
        this.canMoveStudents = canMoveStudents;
    }

    public void setCanBuyCharacter(boolean canBuyCharacter) {
        this.canBuyCharacter = canBuyCharacter;
    }

    public void addCoin(){
        coins++;
    }

    public int removeCoins(int tot){
        coins -= tot;
        return tot;
    }

    public void chooseAssistant(Assistant assistant){
        cards.remove(assistant);
        discardPile = assistant;
        maxSteps = discardPile.getMaxMNSteps();
    }

    public void endTurn(){
        //TODO: implement endTurn function
    }

    public void setAdditionalSteps() {
        maxSteps += 2;
    }

    public boolean getCanChooseAssistant() {
        return canChooseAssistant;
    }

    public boolean getCanMoveStudents() {
        return canMoveStudents;
    }

    public boolean getCanBuyCharacter() {
        return canBuyCharacter;
    }
}