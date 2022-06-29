package it.polimi.ingsw.controller.phases.gamePhases;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.network.messages.clientMessages.GenericClientMessage;

/**
 * This interface represents the phases of the game.
 */
public interface GamePhase {

    /**
     * Executes the phase.
     *
     * @param controller a Controller.
     */
    void execute(Controller controller);

    /**
     * Handles a received message.
     *
     * @param msg        the received GenericClientMessage.
     */
    void receiveMessage(GenericClientMessage msg);
}
