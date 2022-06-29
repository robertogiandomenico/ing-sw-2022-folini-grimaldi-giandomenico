package it.polimi.ingsw.controller.phases.gamePhases;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.controller.TurnController;
import it.polimi.ingsw.controller.phases.ClientHandlerPhases;
import it.polimi.ingsw.controller.phases.turnPhases.MoveStudentsPhase;
import it.polimi.ingsw.network.messages.clientMessages.ActionReply;
import it.polimi.ingsw.network.messages.clientMessages.GenericClientMessage;

/**
 * This class represents the action phase of a player's turn.
 */
public class ActionPhase implements GamePhase {
    TurnController turnController;
    Controller controller;

    /**
     * Executes the phase.
     *
     * @param controller a Controller.
     */
    @Override
    public void execute(Controller controller) {
        this.controller = controller;
        turnController = new TurnController(controller.getGame().getCurrentPlayer(), controller);
        controller.getHandlerByNickname(turnController.getCurrentPlayer().getNickname()).setClientHandlerPhase(ClientHandlerPhases.WAITING_ACTION);
        turnController.setTurnPhase(new MoveStudentsPhase());
    }

    /**
     * Handles a received message.
     *
     * @param msg        the received GenericClientMessage.
     */
    @Override
    public void receiveMessage(GenericClientMessage msg) {
        turnController.executeAction(((ActionReply) msg).getAction());
    }

    @Override
    public String toString() {
        return "ActionPhase";
    }

}
