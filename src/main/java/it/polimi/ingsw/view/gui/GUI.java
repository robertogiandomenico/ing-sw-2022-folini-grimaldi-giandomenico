package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.controller.actions.ActionType;
import it.polimi.ingsw.model.Assistant;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Wizard;
import it.polimi.ingsw.network.client.Client;
import it.polimi.ingsw.view.ViewInterface;
import it.polimi.ingsw.view.utilities.lightclasses.LightBoard;

import java.util.List;

public class GUI implements ViewInterface {

    @Override
    public Client askServerInfo() {
        return null;
    }

    @Override
    public void askNickname() {

    }

    @Override
    public void askGameName() {

    }

    @Override
    public void askGameMode() {

    }

    @Override
    public void askPlayerNumber() {

    }

    @Override
    public void askWizard(List<Wizard> availableWizards) {

    }

    @Override
    public void askAssistant(List<Assistant> availableAssistants, List<Assistant> chosenAssistants) {

    }

    @Override
    public void askAction(List<ActionType> possibleActions) {

    }

    @Override
    public void askStudent(List<Color> availableColors) {

    }

    @Override
    public void askPlace(int maxArchis) {

    }

    @Override
    public int askArchipelago(int maxArchis) {
        return 0;
    }

    @Override
    public void askCharacter(LightBoard board) {

    }

    @Override
    public void askMNSteps(int maxMNSteps) {

    }

    @Override
    public void askCloud(List<Integer> availableClouds) {

    }

    @Override
    public void displayMessage(String message) {

    }

    @Override
    public void displayDisconnectionMessage(String disconnectedNickname, String message) {

    }

    @Override
    public void displayErrorAndExit(String message) {

    }

    @Override
    public void printBoard(LightBoard board) {

    }

    @Override
    public void displayEndgameResult(String winner) {

    }
}