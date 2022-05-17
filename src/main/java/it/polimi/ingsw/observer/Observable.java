package it.polimi.ingsw.observer;

import it.polimi.ingsw.network.messages.clientMessages.GenericClientMessage;
import it.polimi.ingsw.network.messages.serverMessages.GenericServerMessage;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents the observable class that can be observed
 * by implementing the {@link Observer} interface and registering as listener.
 */
public class Observable {
    private final List<Observer> observers = new ArrayList<>();

    /**
     * Adds an observer.
     *
     * @param obs       the observer to be added.
     */
    public void addObserver(Observer obs) {
        observers.add(obs);
    }

    /**
     * Removes an observer.
     *
     * @param obs       the observer to be removed.
     */
    public void removeObserver(Observer obs) {
        observers.remove(obs);
    }

    /**
     * Notifies all the current observers through the update method and passes to them a {@link GenericClientMessage}.
     *
     * @param message   the message to be passed to the observers.
     */
    protected void notifyObserver(GenericServerMessage message) {
        for (Observer observer : observers) {
            observer.update(message);
        }
    }
}
