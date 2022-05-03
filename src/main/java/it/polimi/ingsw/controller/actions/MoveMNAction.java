package it.polimi.ingsw.controller.actions;

import it.polimi.ingsw.controller.TurnController;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.network.messages.serverMessages.MNStepsRequest;
import it.polimi.ingsw.network.server.ClientHandler;

public class MoveMNAction implements Action{
    private final ActionType type = ActionType.MOVE_MN_ACTION;
    private Player currentPlayer;
    private ClientHandler clientHandler;
    private TurnController turnController;
    private int maxMNSteps;

    public MoveMNAction(TurnController turnController) {
        this.turnController = turnController;
        currentPlayer = turnController.getCurrentPlayer();
        clientHandler = turnController.getClientHandler();
    }

    @Override
    public void execute() {
        maxMNSteps = turnController.getController().getGame().getCurrentPlayer().getMaxSteps();
        clientHandler.sendMsgToClient(new MNStepsRequest(maxMNSteps));
    }

    @Override
    public void resetAction(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
        clientHandler = turnController.getController().getHandlerByNickname(currentPlayer.getNickname());
    }

    @Override
    public ActionType getType() {
        return type;
    }
}
