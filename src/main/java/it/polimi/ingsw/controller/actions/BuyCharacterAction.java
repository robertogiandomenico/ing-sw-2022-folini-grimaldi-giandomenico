package it.polimi.ingsw.controller.actions;

import it.polimi.ingsw.controller.TurnController;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.network.server.ClientHandler;

public class BuyCharacterAction implements Action{
    private final ActionType type = ActionType.BUY_CHARACTER_ACTION;
    private Player currentPlayer;
    private ClientHandler clientHandler;
    private TurnController turnController;

    public BuyCharacterAction(TurnController turnController) {
        this.turnController = turnController;
        currentPlayer = turnController.getCurrentPlayer();
        clientHandler = turnController.getClientHandler();
    }

    @Override
    public void execute() {

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
