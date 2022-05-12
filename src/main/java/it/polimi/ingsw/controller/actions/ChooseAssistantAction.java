package it.polimi.ingsw.controller.actions;

import it.polimi.ingsw.controller.TurnController;
import it.polimi.ingsw.model.Assistant;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.network.messages.clientMessages.GenericClientMessage;
import it.polimi.ingsw.network.messages.serverMessages.ChooseAssistantRequest;
import it.polimi.ingsw.network.server.ClientHandler;

import java.util.ArrayList;
import java.util.List;

public class ChooseAssistantAction implements Action {
    private final ActionType type = ActionType.CHOOSE_ASSISTANT_ACTION;
    private Player currentPlayer;
    private ClientHandler clientHandler;
    private TurnController turnController;
    private List<Assistant> availableAssistants;
    private List<Assistant> chosenAssistants;

    public ChooseAssistantAction(TurnController turnController) {
        this.turnController = turnController;
        currentPlayer = turnController.getCurrentPlayer();
        clientHandler = turnController.getClientHandler();
        availableAssistants = new ArrayList<>(currentPlayer.getCards());
    }

    @Override
    public void execute() {
    }

    @Override
    public void resetAction(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
        clientHandler = turnController.getController().getHandlerByNickname(currentPlayer.getNickname());
        availableAssistants.clear();
        availableAssistants.addAll(currentPlayer.getCards());
    }

    @Override
    public ActionType getType() {
        return type;
    }

    @Override
    public void receiveMessage(GenericClientMessage msg) {

    }
}
