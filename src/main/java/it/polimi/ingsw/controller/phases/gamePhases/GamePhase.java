package it.polimi.ingsw.controller.phases.gamePhases;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.network.messages.clientMessages.GenericClientMessage;

public interface GamePhase {
    void execute(Controller controller);

    void receiveMessage(GenericClientMessage msg);
}
