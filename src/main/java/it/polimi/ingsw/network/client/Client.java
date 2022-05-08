package it.polimi.ingsw.network.client;

import it.polimi.ingsw.network.messages.clientMessages.GenericClientMessage;
import it.polimi.ingsw.observer.Observable;

import java.util.logging.Logger;

public abstract class Client extends Observable {

    public static final Logger LOGGER = Logger.getLogger(Client.class.getName());

    /**
     * Sends a message to the server.
     *
     * @param message the message to be sent.
     */
    public abstract void sendMessage(GenericClientMessage message);

    /**
     * Asynchronously reads a message from the server and notifies the ClientController.
     */
    public abstract void readMessage();

    /**
     * Disconnects from the server.
     */
    public abstract void disconnect();

    //TODO: heartbeat/ping client message
}