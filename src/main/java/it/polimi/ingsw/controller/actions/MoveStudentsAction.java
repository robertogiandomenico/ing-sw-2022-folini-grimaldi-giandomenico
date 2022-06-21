package it.polimi.ingsw.controller.actions;

import it.polimi.ingsw.controller.TurnController;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.network.messages.MessageType;
import it.polimi.ingsw.network.messages.clientMessages.GenericClientMessage;
import it.polimi.ingsw.network.messages.clientMessages.PlaceReply;
import it.polimi.ingsw.network.messages.clientMessages.StudentReply;
import it.polimi.ingsw.network.messages.serverMessages.PlaceRequest;
import it.polimi.ingsw.network.messages.serverMessages.StudentRequest;
import it.polimi.ingsw.network.messages.serverMessages.TextMessage;
import it.polimi.ingsw.network.server.ClientHandler;
import it.polimi.ingsw.network.server.Server;

import java.util.*;
import java.util.logging.Level;
import java.util.stream.Collectors;

public class MoveStudentsAction implements Action {
    private final ActionType type = ActionType.MOVE_STUDENT_ACTION;
    private final Player currentPlayer;
    private final ClientHandler clientHandler;
    private final TurnController turnController;

    private Student studentToBeMoved;
    private Map<Color, Integer> availableColors;

    public MoveStudentsAction(TurnController turnController) {
        this.turnController = turnController;
        currentPlayer = turnController.getCurrentPlayer();
        clientHandler = turnController.getClientHandler();

    }

    @Override
    public void execute() {
        availableColors = new HashMap<>();
        for (Color c : Color.values()){
            int num = (int) Arrays.stream(turnController.getController().getGame().getBoard().getCurrentPlayerSchoolBoard().getEntrance()).filter(s -> s!= null && s.getColor() == c).count();
            availableColors.put(c, num);
        }
        clientHandler.sendMsgToClient(new StudentRequest(availableColors.keySet().stream().filter(c -> availableColors.get(c) > 0).sorted(Comparator.comparingInt(Enum::ordinal)).collect(Collectors.toList())));
    }

    @Override
    public ActionType getType() {
        return type;
    }

    @Override
    public void receiveMessage(GenericClientMessage msg) {
        Board b = turnController.getController().getGame().getBoard();
        boolean askAgain = false;
        if (!(msg.getType() == MessageType.STUDENT_REPLY || msg.getType() == MessageType.PLACE_REPLY)){
            return;
        }

        if (msg.getType() == MessageType.STUDENT_REPLY){
            studentToBeMoved = new Student(((StudentReply) msg).getStudColor());
            clientHandler.sendMsgToClient(new PlaceRequest(b.getArchipelagos().size()));
        }

        if (msg.getType() == MessageType.PLACE_REPLY){
            assert studentToBeMoved != null;

            String place = ((PlaceReply) msg).getPlace();
            if (place.equals("DININGROOM")){
                if(b.getCurrentPlayerSchoolBoard().getDiningRoom()[studentToBeMoved.getColor().ordinal()] < 10) {
                    b.moveFromEntranceToDiningRoom(studentToBeMoved);
                } else {
                    askAgain = true;
                }
            } else if (place.equals("ARCHIPELAGO")) {
                int archiIndex = ((PlaceReply) msg).getArchiIndex();
                assert archiIndex != -1;
                b.moveFromEntranceToArchipelago(studentToBeMoved, archiIndex);
            }
            if(!askAgain){
                turnController.nextAction(this);
            } else {
                clientHandler.sendMsgToClient(new PlaceRequest(b.getArchipelagos().size()));
            }
        }
    }
}
