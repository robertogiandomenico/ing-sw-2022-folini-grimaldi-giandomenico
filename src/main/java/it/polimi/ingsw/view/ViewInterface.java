package it.polimi.ingsw.view;

import it.polimi.ingsw.controller.actions.ActionType;
import it.polimi.ingsw.model.*;

import java.util.List;

/**
 * This interface represents a generic view to be implemented
 * by each view type (CLI, GUI in JavaFX).
 */
public interface ViewInterface {

    void askServerInfo();
    void askNickname();
    void askGameName();
    void askGameMode();
    void askPlayerNumber();
    void askWizard(List<Wizard> availableWizards);

    void askAssistant(List<Assistant> availableAssistants, List<Assistant> discardedAssistants);

    void askAction(List<ActionType> possibleActions);
    void askStudent(List<Color> availableColors);
    void askPlace(int maxArchis);
    int askArchipelago(int maxArchis);
    void askCharacter(Board board);

    void askMNSteps(int maxMNSteps);

    void askCloud(List<Integer> availableClouds);

    void displayLoginResult(boolean nicknameAccepted, boolean connectionSuccessful, String nickname);
    void displayMessage(String message);
    void displayDisconnectionMessage(String disconnectedNickname, String message);
    void displayErrorAndExit(String message);
    void printBoard(Board board);
    void displayEndgameResult(String winner);

}
