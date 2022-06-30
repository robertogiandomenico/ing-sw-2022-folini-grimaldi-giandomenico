package it.polimi.ingsw.controller.actions;

import it.polimi.ingsw.controller.TurnController;
import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.Cloud;
import it.polimi.ingsw.model.Student;
import it.polimi.ingsw.network.messages.MessageType;
import it.polimi.ingsw.network.messages.clientMessages.CloudReply;
import it.polimi.ingsw.network.messages.clientMessages.GenericClientMessage;
import it.polimi.ingsw.network.messages.serverMessages.CloudRequest;
import it.polimi.ingsw.network.server.ClientHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents the action of selecting a cloud from which draw
 * students during a turn.
 */
public class SelectCloudAction implements Action{
    private final ActionType type =  ActionType.SELECT_CLOUD_ACTION;
    private final ClientHandler clientHandler;
    private final TurnController turnController;

    /**
     * Class constructor.
     *
     * @param turnController a TurnController.
     */
    public SelectCloudAction(TurnController turnController) {
        this.turnController = turnController;
        clientHandler = turnController.getClientHandler();
    }

    /**
     * Executes the action sending a request to the client.
     */
    @Override
    public void execute() {
        List<Integer> indexesAvailableClouds = new ArrayList<>();
        Cloud[] clouds = turnController.getController().getGame().getBoard().getClouds();
        for (int i = 0; i < clouds.length; i++){
            if (!clouds[i].isEmpty()){
                indexesAvailableClouds.add(i);
            }
        }
        if(indexesAvailableClouds.size() != 0)
            clientHandler.sendMsgToClient(new CloudRequest(indexesAvailableClouds));
        else
            turnController.getController().nextTurn();
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
