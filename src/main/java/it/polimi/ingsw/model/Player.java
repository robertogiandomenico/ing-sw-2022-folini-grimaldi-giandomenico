package it.polimi.ingsw.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Player {
    private final String nickname;
    private int coins;
    private List<Assistant> cards;
    private final TeamColor playerColor;
    private Assistant discardPile;
    private final Wizard selectedWizard;
    private boolean canChooseAssistant;
    private boolean canMoveStudents;
    private boolean canBuyCharacter;
    private int maxSteps;
    private int selectedSteps;


    public Player(String nickname, TeamColor playerColor, Wizard selectedWizard) {
        this.nickname = nickname;
        this.playerColor = playerColor;
        this.selectedWizard = selectedWizard;
        coins = 1;
        initializeCards();
    }

    private void initializeCards(){
        cards = new ArrayList<Assistant>(
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

    public TeamColor getPlayerColor() {
        return playerColor;
    }

    public Assistant getDiscardPile() {
        return discardPile;
    }

    public Wizard getSelectedWizard() {
        return selectedWizard;
    }

    public boolean canChooseAssistant() {
        return canChooseAssistant;
    }

    public boolean canMoveStudents() {
        return canMoveStudents;
    }

    public boolean canBuyCharacter() {
        return canBuyCharacter;
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

    public void chooseAssistant(){
        //TODO: implement chooseAssistant function
        //maxSteps = assistant.maxMNSteps
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

    public TeamColor getPlayerTeam() {
        return playerColor;
    }
}