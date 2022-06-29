package it.polimi.ingsw.controller.phases.gamePhases;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.network.messages.clientMessages.GenericClientMessage;

/**
 * This class represents the ending of the game.
 */
public class EndPhase implements GamePhase {

    @Override
    public void execute(Controller controller) {

    }

    @Override
    public void receiveMessage(GenericClientMessage msg) {

    }

    @Override
    public String toString() {
        return "EndPhase";
    }

}
