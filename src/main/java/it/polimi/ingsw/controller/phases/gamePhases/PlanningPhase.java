package it.polimi.ingsw.controller.phases.gamePhases;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.controller.phases.ClientHandlerPhases;
import it.polimi.ingsw.model.Assistant;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.network.messages.MessageType;
import it.polimi.ingsw.network.messages.clientMessages.ChooseAssistantReply;
import it.polimi.ingsw.network.messages.clientMessages.GenericClientMessage;
import it.polimi.ingsw.network.messages.serverMessages.ChooseAssistantRequest;
import it.polimi.ingsw.network.server.ClientHandler;
import it.polimi.ingsw.view.utilities.lightclasses.LightBoard;

import java.util.ArrayList;
import java.util.List;

public class PlanningPhase implements GamePhase {

    private Controller controller;
    private Player currentPlayer;
    private int playerIndex = 0;
    private List<Assistant> chosenAssistant;

    private LightBoard lightBoard;

    @Override
    public void execute(Controller controller) {
        this.controller = controller;
        lightBoard = controller.getGame().getBoard().getLightBoard();
        chosenAssistant = new ArrayList<>();
        playerIndex = controller.getGame().getPlayerOrder().indexOf(controller.getGame().getCurrentPlayer());
        currentPlayer = controller.getGame().getCurrentPlayer();

        for(ClientHandler c : controller.getHandlers()){
            c.setClientHandlerPhase(ClientHandlerPhases.WAITING_ASSISTANT);
        }
        controller.getHandlers().get(playerIndex).sendMsgToClient(new ChooseAssistantRequest(currentPlayer.getCards(), chosenAssistant, lightBoard));
    }

    @Override
    public void receiveMessage(GenericClientMessage msg) {
        if(msg == null || msg.getType() != MessageType.CHOOSE_ASSISTANT_REPLY){
            return;
        }
        Assistant assistant = ((ChooseAssistantReply) msg).getAssistant();
        currentPlayer.chooseAssistant(assistant);
        chosenAssistant.add(assistant);
        controller.getHandlerByNickname(currentPlayer.getNickname()).setClientHandlerPhase(ClientHandlerPhases.WAITING_PHASE_CHANGE);
        if (controller.getHandlers().stream().filter(c -> c.getClientHandlerPhase() == ClientHandlerPhases.WAITING_PHASE_CHANGE).count() == controller.getHandlers().size()){
            controller.getGame().updatePlayersOrder();
            controller.setGamePhase(new ActionPhase());
        } else {
            playerIndex = (playerIndex + 1) % controller.getGame().getNumberOfPlayers();
            currentPlayer = controller.getGame().getPlayerOrder().get(playerIndex);
            controller.getHandlers().get(playerIndex).sendMsgToClient(new ChooseAssistantRequest(currentPlayer.getCards(), chosenAssistant, lightBoard));
        }
    }

    @Override
    public String toString() {
        return "PlanningPhase";
    }
}
