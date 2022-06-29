package it.polimi.ingsw.controller.actions;

import it.polimi.ingsw.controller.TurnController;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.network.messages.MessageType;
import it.polimi.ingsw.network.messages.clientMessages.GenericClientMessage;
import it.polimi.ingsw.network.messages.clientMessages.MNStepsReply;
import it.polimi.ingsw.network.messages.serverMessages.MNStepsRequest;
import it.polimi.ingsw.network.server.ClientHandler;

/**
 * This class represents the action of moving Mother Nature during a turn.
 */
public class MoveMNAction implements Action{
    private final ActionType type = ActionType.MOVE_MN_ACTION;
    private final Player currentPlayer;
    private final ClientHandler clientHandler;
    private final TurnController turnController;

    /**
     * Class constructor.
     *
     * @param turnController a TurnController.
     */
    public MoveMNAction(TurnController turnController) {
        this.turnController = turnController;
        currentPlayer = turnController.getCurrentPlayer();
        clientHandler = turnController.getClientHandler();
    }

    /**
     * Executes the action sending a request to the client.
     */
    @Override
    public void execute() {
        int maxMNSteps = turnController.getController().getGame().getCurrentPlayer().getMaxSteps();
        clientHandler.sendMsgToClient(new MNStepsRequest(maxMNSteps));
    }

    /**
     * Returns the type of the action.
     *
     * @return               the ActionType.
     */
    @Override
    public ActionType getType() {
        return type;
    }

    /**
     * Handles a received message.
     *
     * @param msg            the received GenericClientMessage.
     */
    @Override
    public void receiveMessage(GenericClientMessage msg) {
        if (!(msg.getType() == MessageType.MNSTEPS_REPLY)){
            return;
        }

        int selectedSteps = ((MNStepsReply) msg).getMnSteps();
        currentPlayer.setSelectedSteps(selectedSteps);
        turnController.getController().getGame().getBoard().moveMotherNature(selectedSteps);
        turnController.nextAction(this);
    }

}
