package it.polimi.ingsw.observer;

import it.polimi.ingsw.controller.actions.ActionType;
import it.polimi.ingsw.model.*;

import java.util.Map;

/**
 * Custom observer interface for views. It supports different types of notification.
 */
public interface ViewObserver {

    /**
     * Create a new connection to the server with the updated info.
     *
     * @param serverInfo a map of server address and server port.
     */
    void onUpdateServerInfo(Map<String, String> serverInfo);

    /**
     * Sends a message to the server with the updated nickname.
     *
     * @param nickname the nickname to be sent.
     */
    void onUpdateNickname(String nickname);

    /**
     * Sends a message to the server with the game name chosen by the user.
     *
     * @param gameName the game name to be sent.
     */
    void onUpdateGameName(String gameName);

    /**
     * Sends a message to the server with the game mode chosen by the user.
     *
     * @param gameMode the game mode to be sent.
     */
    void onUpdateGameMode(String gameMode);

    /**
     * Sends a message to the server with the player number chosen by the user.
     *
     * @param playersNumber the number of players.
     */
    void onUpdatePlayersNumber(int playersNumber);

    /**
     * Sends a message to the server with the wizard chosen by the user.
     *
     * @param wizard the chosen wizard.
     */
    void onUpdateWizard(Wizard wizard);

    /**
     * Sends a message to the server with the assistant chosen by the user.
     *
     * @param assistant the chosen assistant.
     */
    void onUpdateAssistant(Assistant assistant);

    /**
     * Sends a message to the server with the action chosen by the user.
     *
     * @param action the chosen action.
     */
    void onUpdateAction(ActionType action);

    /**
     * Sends a message to the server with the color chosen by the user.
     *
     * @param color the chosen color.
     */
    void onUpdateColor(Color color);

    /**
     * Sends a message to the server with the place chosen by the user.
     *
     * @param place the chosen place.
     * @param archiIndex the possible chosen archipelago index
     */
    void onUpdatePlace(String place, int archiIndex);

    /**
     * Sends a message to the server with the character chosen by the user.
     *
     * @param character the chosen character.
     */
    void onUpdateCharacter(GameCharacter character, int archiIndex, int studentNumber, Color[] studColor);

    /**
     * Sends a message to the server with the steps selected by the user.
     *
     * @param mnSteps the number of the chosen steps.
     */
    void onUpdateMNSteps(int mnSteps);

    /**
     * Sends a message to the server with the cloud chosen by the user.
     *
     * @param cloudIndex the chosen cloud.
     */
    void onUpdateCloud(int cloudIndex);

    /**
     * Handles a disconnection wanted by the user.
     * (e.g. a click on the back button into the GUI).
     */
    void onDisconnection();
}
