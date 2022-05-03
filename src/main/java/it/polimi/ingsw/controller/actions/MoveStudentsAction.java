package it.polimi.ingsw.controller.actions;

import it.polimi.ingsw.controller.TurnController;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.network.messages.serverMessages.StudentRequest;
import it.polimi.ingsw.network.server.ClientHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MoveStudentsAction implements Action {
    private final ActionType type = ActionType.MOVE_STUDENT_ACTION;
    private Player currentPlayer;
    private ClientHandler clientHandler;
    private TurnController turnController;
    private List<Color> availableColors;

    public MoveStudentsAction(TurnController turnController) {
        this.turnController = turnController;
        currentPlayer = turnController.getCurrentPlayer();
        clientHandler = turnController.getClientHandler();
    }

    @Override
    public void execute() {
        availableColors = new ArrayList<>();

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
}
