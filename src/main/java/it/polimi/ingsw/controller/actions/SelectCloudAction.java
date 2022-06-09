package it.polimi.ingsw.controller.actions;

import it.polimi.ingsw.controller.TurnController;
import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.Cloud;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Student;
import it.polimi.ingsw.network.messages.MessageType;
import it.polimi.ingsw.network.messages.clientMessages.CloudReply;
import it.polimi.ingsw.network.messages.clientMessages.GenericClientMessage;
import it.polimi.ingsw.network.messages.serverMessages.CloudRequest;
import it.polimi.ingsw.network.server.ClientHandler;

import java.util.ArrayList;
import java.util.List;

public class SelectCloudAction implements Action{
    private final ActionType type =  ActionType.SELECT_CLOUD_ACTION;
    private final Player currentPlayer;
    private final ClientHandler clientHandler;
    private final TurnController turnController;

    public SelectCloudAction(TurnController turnController) {
        this.turnController = turnController;
        currentPlayer = turnController.getCurrentPlayer();
        clientHandler = turnController.getClientHandler();
    }

    @Override
    public void execute() {
        List<Integer> indexesAvailableClouds = new ArrayList<>();
        Cloud[] clouds = turnController.getController().getGame().getBoard().getClouds();
        for (int i = 0; i < clouds.length; i++){
            if (!clouds[i].isEmpty()){
                indexesAvailableClouds.add(i);
            }
        }

        clientHandler.sendMsgToClient(new CloudRequest(indexesAvailableClouds));
    }

    @Override
    public ActionType getType() {
        return type;
    }

    @Override
    public void receiveMessage(GenericClientMessage msg) {
        if (!(msg.getType() == MessageType.CLOUD_REPLY)){
            return;
        }
        Board b = turnController.getController().getGame().getBoard();
        int cloudIndex = ((CloudReply) msg).getCloudIndex();
        Student[] cloudContent = b.getClouds()[cloudIndex].get();

        for (Student s : cloudContent){
            b.getCurrentPlayerSchoolBoard().addToEntrance(s);
        }

        turnController.getController().nextTurn();
    }
}
