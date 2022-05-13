package it.polimi.ingsw.controller.actions;

import it.polimi.ingsw.controller.TurnController;
import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Student;
import it.polimi.ingsw.network.messages.MessageType;
import it.polimi.ingsw.network.messages.clientMessages.GenericClientMessage;
import it.polimi.ingsw.network.messages.clientMessages.PlaceReply;
import it.polimi.ingsw.network.messages.clientMessages.StudentReply;
import it.polimi.ingsw.network.messages.serverMessages.PlaceRequest;
import it.polimi.ingsw.network.messages.serverMessages.StudentRequest;
import it.polimi.ingsw.network.server.ClientHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MoveStudentsAction implements Action {
    private final ActionType type = ActionType.MOVE_STUDENT_ACTION;
    private Player currentPlayer;
    private ClientHandler clientHandler;
    private final TurnController turnController;

    private Student studentToBeMoved;

    public MoveStudentsAction(TurnController turnController) {
        this.turnController = turnController;
        currentPlayer = turnController.getCurrentPlayer();
        clientHandler = turnController.getClientHandler();
    }

    @Override
    public void execute() {
        List<Color> availableColors = new ArrayList<>();

        for (Color c : Color.values()){
            if(Arrays.stream(turnController.getController().getGame().getBoard().getCurrentPlayerSchoolBoard().getEntrance()).anyMatch(s -> s.getColor() == c)) {
                availableColors.add(c);
            }
        }
        clientHandler.sendMsgToClient(new StudentRequest(availableColors));
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
        Board b = turnController.getController().getGame().getBoard();
        if (!(msg.getType() == MessageType.STUDENT_REPLY || msg.getType() == MessageType.PLACE_REPLY)){
            return;
        }

        if (msg.getType() == MessageType.STUDENT_REPLY){
            studentToBeMoved = b.getCurrentPlayerSchoolBoard().removeFromEntrance(((StudentReply) msg).getStudColor());
            clientHandler.sendMsgToClient(new PlaceRequest(b.getArchipelagos().size()));
        }

        if (msg.getType() == MessageType.PLACE_REPLY){
            assert studentToBeMoved != null;

            String place = ((PlaceReply) msg).getPlace().toUpperCase().replace(" ", "");
            if (place.equals("DININGROOM")){
                int diningRoomIndex = b.mapToIndex(studentToBeMoved.getColor());
                b.getCurrentPlayerSchoolBoard().addToDiningRoom(diningRoomIndex);
            } else if (place.equals("ISLAND") || place.equals("ARCHIPELAGO")) {
                int archiIndex = ((PlaceReply) msg).getArchiIndex();
                assert archiIndex != -1;
                b.getArchipelago(archiIndex).getIslands().get(0).addStudent(studentToBeMoved);
            }
        }
    }
}
