package it.polimi.ingsw.observer;

import it.polimi.ingsw.network.messages.serverMessages.GenericServerMessage;

/**
 * This interface represents the observer interface.
 * It supports a generic method of update.
 */
public interface Observer {
    void update(GenericServerMessage message);
}