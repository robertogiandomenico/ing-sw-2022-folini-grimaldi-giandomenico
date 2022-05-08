package it.polimi.ingsw.controller;

import it.polimi.ingsw.controller.actions.ActionType;
import it.polimi.ingsw.model.Assistant;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.GameCharacter;
import it.polimi.ingsw.model.Wizard;
import it.polimi.ingsw.network.client.Client;
import it.polimi.ingsw.network.messages.clientMessages.*;
import it.polimi.ingsw.network.messages.serverMessages.*;
import it.polimi.ingsw.observer.Observer;
import it.polimi.ingsw.observer.ViewObserver;
import it.polimi.ingsw.view.ViewInterface;

import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * This class is part of the client side.
 * It is an interpreter between the network and a generic view (which can be CLI or GUI).
 * It receives the messages, wraps/unwraps and pass them to the network/view.
 */
public class ClientController implements ViewObserver, Observer {

    private final ViewInterface view;
    private Client client;
    private final ExecutorService taskQueue;
    private String nickname;

    /**
     * Constructs Client Controller.
     *
     * @param view the view to be controlled.
     */
    public ClientController(ViewInterface view) {
        this.view = view;
        taskQueue = Executors.newSingleThreadExecutor();
    }

    /**
     * Create a new connection to the server.
     *
     * @param serverInfo a map of server address and server port.
     */
    @Override
    public void onUpdateServerInfo(Map<String, String> serverInfo) {

    }

    /**
     * Sends a message to the server with the updated nickname.
     *
     * @param nickname the nickname to be sent.
     */
    @Override
    public void onUpdateNickname(String nickname) {
        this.nickname = nickname;
        client.sendMessage(new NicknameReply(this.nickname));
    }

    /**
     * Sends a message to the server with the game name chosen by the user.
     *
     * @param gameName the game name to be sent.
     */
    @Override
    public void onUpdateGameName(String gameName) {
        client.sendMessage(new GameNameReply(gameName));
    }

    /**
     * Sends a message to the server with the game mode chosen by the user.
     *
     * @param gameMode the game mode to be sent.
     */
    @Override
    public void onUpdateGameMode(String gameMode) {
        client.sendMessage(new GameNameReply(gameMode));
    }

    /**
     * Sends a message to the server with the player number chosen by the user.
     *
     * @param playersNumber the number of players.
     */
    @Override
    public void onUpdatePlayersNumber(int playersNumber) {
        client.sendMessage(new PlayerNumberReply(playersNumber));
    }

    /**
     * Sends a message to the server with the wizard chosen by the user.
     *
     * @param wizard the chosen wizard.
     */
    @Override
    public void onUpdateWizard(Wizard wizard) {
        client.sendMessage(new WizardReply(wizard));
    }

    /**
     * Sends a message to the server with the assistant chosen by the user.
     *
     * @param assistant the chosen assistant.
     */
    @Override
    public void onUpdateAssistant(Assistant assistant) {
        client.sendMessage(new ChooseAssistantReply(assistant));
    }

    /**
     * Sends a message to the server with the action chosen by the user.
     *
     * @param action the chosen action.
     */
    @Override
    public void onUpdateAction(ActionType action) {
        client.sendMessage(new ActionReply(action));
    }

    /**
     * Sends a message to the server with the color chosen by the user.
     *
     * @param color the chosen color.
     */
    @Override
    public void onUpdateColor(Color color) {
        client.sendMessage(new StudentReply(color));
    }

    /**
     * Sends a message to the server with the place chosen by the user.
     *
     * @param place the chosen place.
     * @param archiIndex the possible chosen archipelago index
     */
    @Override
    public void onUpdatePlace(String place, int archiIndex) {

        if (archiIndex == -1)
            client.sendMessage(new PlaceReply(place));
        else
            client.sendMessage(new PlaceReply(place, archiIndex));
    }

    /**
     * Sends a message to the server with the character chosen by the user.
     *
     * @param character the chosen character.
     */
    @Override
    public void onUpdateCharacter(GameCharacter character, int archiIndex, int studentNumber, Color[] studColor) {
        client.sendMessage(new CharacterReply(character, archiIndex, studentNumber, studColor));
    }

    /**
     * Sends a message to the server with the steps selected by the user.
     *
     * @param mnSteps the number of the chosen steps.
     */
    @Override
    public void onUpdateMNSteps(int mnSteps) {
        client.sendMessage(new MNStepsReply(mnSteps));

    }

    /**
     * Sends a message to the server with the cloud chosen by the user.
     *
     * @param cloudIndex the index of the chosen cloud.
     */
    @Override
    public void onUpdateCloud(int cloudIndex) {
        client.sendMessage(new CloudReply(cloudIndex));

    }

    @Override
    public void onDisconnection() {
    //TODO: implement the method that disconnects the clien from the server
    }

    /**
     * Performs action based on the message type received from the server.
     *
     * @param message the message received from the server.
     */
    @Override
    public void update(GenericServerMessage message) {
        switch (message.getType()) {
            case GAMENAME_REQUEST:
                GameNameRequest gameNameRequest = (GameNameRequest) message;
                taskQueue.execute(view::askGameName);
                break;
            case GAMEMODE_REQUEST:
                GameModeRequest gameModeRequest = (GameModeRequest) message;
                taskQueue.execute(view::askGameMode);
                break;
            case PLAYERNUMBER_REQUEST:
                PlayerNumberRequest playerNumberRequest = (PlayerNumberRequest) message;
                taskQueue.execute(view::askPlayerNumber);
                break;
            case NICKNAME_REQUEST:
                NicknameRequest nicknameRequest = (NicknameRequest) message;
                taskQueue.execute(view::askNickname);
                break;

            //TODO: complete w/ all the messages

            default:
                break;
        }
    }
}
