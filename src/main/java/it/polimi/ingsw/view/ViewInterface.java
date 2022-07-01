package it.polimi.ingsw.view;

import it.polimi.ingsw.controller.actions.ActionType;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.network.client.Client;
import it.polimi.ingsw.view.utilities.lightclasses.LightBoard;

import java.util.List;

/**
 * This interface represents a generic view to be implemented
 * by each view type ({@link it.polimi.ingsw.view.cli.CLI CLI},
 * {@link it.polimi.ingsw.view.gui.GUI GUI} in JavaFX).
 */
public interface ViewInterface {

    /**
     * Asks server information (address and port) to the user and checks their
     * validity.
     *
     * @return                     the Client created.
     */
    Client askServerInfo();

    /**
     * Asks a nickname to the user.
     */
    void askNickname();

    /**
     * Asks the name of a game to the user.
     */
    void askGameName();

    /**
     * Asks the game mode (easy/expert) to the user.
     */
    void askGameMode();

    /**
     * Asks the number of players for the game to the user.
     */
    void askPlayerNumber();

    /**
     * Asks the user to choose a wizard among the available ones.
     *
     * @param availableWizards     a Wizard List of Wizards not taken yet.
     */
    void askWizard(List<Wizard> availableWizards);

    /**
     * Asks the user to choose the assistant they want to play.
     *
     * @param availableAssistants  an Assistant List of available cards for the Player.
     * @param discardedAssistants  an Assistant List containing the cards chosen by others.
     * @param numOfPlayers         the number of Players for the game.
     */
    void askAssistant(List<Assistant> availableAssistants, List<Assistant> discardedAssistants, int numOfPlayers);

    /**
     * Asks the user to declare which action they want to go for next.
     *
     * @param possibleActions      an ActionType List of possible actions for the Player.
     */
    void askAction(List<ActionType> possibleActions);

    /**
     * Asks the user which student they want to move.
     *
     * @param availableColors      a Color List representing the Students that can be moved.
     */
    void askStudent(List<Color> availableColors);

    /**
     * Asks the user where they want to move the student.
     *
     * @param maxArchis            the number of Archipelagos.
     */
    void askPlace(int maxArchis);


    /**
     * Asks the user the character they want to play. Then, based on the selected
     * one, asks the proper values in order to activate their effect.
     *
     * @param board                a LightBoard to access the available characters.
     */
    void askCharacter(LightBoard board);

    /**
     * Asks the user the number of steps they want Mother Nature to take.
     *
     * @param maxMNSteps           the limit of steps.
     */
    void askMNSteps(int maxMNSteps);

    /**
     * Asks the user the cloud they want to draw from.
     *
     * @param availableClouds      a List of available Clouds.
     */
    void askCloud(List<Integer> availableClouds);

    /**
     * Displays a message to the user.
     *
     * @param message              the message to be displayed.
     */
    void displayMessage(String message);

    /**
     * Displays a message stating that some player disconnected.
     *
     * @param disconnectedNickname the nickname of the disconnected Player.
     * @param message              the message to be displayed.
     */
    void displayDisconnectionMessage(String disconnectedNickname, String message);

    /**
     * Displays an error message and exits.
     *
     * @param message              the error message to be displayed.
     */
    void displayErrorAndExit(String message);

    /**
     * Prints the current board (archipelagos, clouds, characters, school boards).
     *
     * @param board                the LightBoard representing the current status.
     */
    void printBoard(LightBoard board);

    /**
     * Displays the results of the game.
     *
     * @param winner               the nickname of the winner.
     * @param condition            the condition to print.
     */
    void displayEndgameResult(String winner, String condition);

}