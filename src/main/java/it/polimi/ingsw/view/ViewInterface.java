package it.polimi.ingsw.view;

import it.polimi.ingsw.controller.actions.ActionType;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.network.client.Client;
import it.polimi.ingsw.view.utilities.lightclasses.LightBoard;

import java.util.List;

/**
 * This interface represents a generic view to be implemented
 * by each view type (CLI, GUI in JavaFX).
 */
public interface ViewInterface {

    Client askServerInfo();
    void askNickname();
    void askGameName();
    void askGameMode();
    void askPlayerNumber();
    void askWizard(List<Wizard> availableWizards);

    void askAssistant(List<Assistant> availableAssistants, List<Assistant> chosenAssistants);

    void askAction(List<ActionType> possibleActions);
    void askStudent(List<Color> availableColors);
    void askPlace(int maxArchis);
    int askArchipelago(int maxArchis);
    void askCharacter(LightBoard board);

    void askMNSteps(int maxMNSteps);

    void askCloud(List<Integer> availableClouds);

    void displayLoginResult(boolean nicknameAccepted, boolean connectionSuccessful, String nickname);
    void displayMessage(String message);
    void displayDisconnectionMessage(String disconnectedNickname, String message);
    void displayErrorAndExit(String message);
    void printBoard(LightBoard board);
    void displayEndgameResult(String winner);

}
