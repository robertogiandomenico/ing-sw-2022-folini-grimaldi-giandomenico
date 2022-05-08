package it.polimi.ingsw.controller.actions;

import it.polimi.ingsw.controller.TurnController;
import it.polimi.ingsw.model.Assistant;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.network.messages.MessageType;
import it.polimi.ingsw.network.messages.clientMessages.ChooseAssistantReply;
import it.polimi.ingsw.network.messages.clientMessages.GenericClientMessage;
import it.polimi.ingsw.network.messages.serverMessages.ChooseAssistantRequest;
import it.polimi.ingsw.network.server.ClientHandler;

import java.util.ArrayList;
import java.util.List;

public class ChooseAssistantAction implements Action {
    private final ActionType type = ActionType.CHOOSE_ASSISTANT_ACTION;
    private Player currentPlayer;
    private ClientHandler clientHandler;
    private final TurnController turnController;

    public ChooseAssistantAction(TurnController turnController) {
        this.turnController = turnController;
        currentPlayer = turnController.getCurrentPlayer();
        clientHandler = turnController.getClientHandler();
    }

    @Override
    public void execute() {
        List<Assistant> availableAssistants = new ArrayList<>(currentPlayer.getCards());
        List<Assistant> discardedAssistants = new ArrayList<>();
        List<Player> players = turnController.getController().getGame().getPlayerOrder();

        for (int i = 0; i < players.indexOf(currentPlayer); i++){
            availableAssistants.remove(players.get(i).getDiscardPile());
            discardedAssistants.add(players.get(i).getDiscardPile());
        }

        clientHandler.sendMsgToClient(new ChooseAssistantRequest(availableAssistants, discardedAssistants));
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

    @Override
    public void receiveMessage(GenericClientMessage msg) {
        if (!(msg.getType() == MessageType.CHOOSE_ASSISTANT_REPLY)){
            return;
        }
        Assistant assistant = ((ChooseAssistantReply) msg).getAssistant();

        currentPlayer.chooseAssistant(assistant);
    }
}
