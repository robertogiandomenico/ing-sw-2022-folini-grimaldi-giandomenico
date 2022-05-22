package it.polimi.ingsw.controller;

import it.polimi.ingsw.controller.actions.*;
import it.polimi.ingsw.controller.phases.turnPhases.MoveMNPhase;
import it.polimi.ingsw.controller.phases.turnPhases.SelectCloudPhase;
import it.polimi.ingsw.controller.phases.turnPhases.TurnPhase;
import it.polimi.ingsw.model.GameCharacter;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.network.messages.serverMessages.ActionRequest;
import it.polimi.ingsw.network.messages.serverMessages.BoardData;
import it.polimi.ingsw.network.server.ClientHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TurnController {
    private final Player currentPlayer;
    private final ClientHandler clientHandler;
    private TurnPhase turnPhase;
    private final Controller controller;
    private List<ActionType> availableActions;
    private final Map<Action, Boolean> possibleActions;
    private boolean alreadyBoughtCharacter = false;

    public TurnController(Player currentPlayer, Controller controller) {
        this.currentPlayer = currentPlayer;
        this.controller = controller;
        clientHandler = controller.getHandlerByNickname(currentPlayer.getNickname());
        availableActions = new ArrayList<>();
        possibleActions = new HashMap<>();
        fillPossibleActions();
    }

    public void setTurnPhase(TurnPhase turnPhase) {
        this.turnPhase = turnPhase;
        availableActions = turnPhase.getAvailableTurnActions();
        if(controller.getGame().isExpertMode() && !turnPhase.toString().equals("SelectCloudPhase") && !alreadyBoughtCharacter && canBuyCharacter()){
            availableActions.add(ActionType.BUY_CHARACTER_ACTION);
        }
        updatePossibleActions();
        clientHandler.sendMsgToClient(new ActionRequest(availableActions, controller.getGame().getBoard().getLightBoard()));
    }

    private boolean canBuyCharacter() {
        for (GameCharacter c : controller.getGame().getBoard().getSelectedCharacters()){
            if (currentPlayer.getCoins() >= c.getCost()) return true;
        }
        return false;
    }

    private void fillPossibleActions() {
        possibleActions.put(new MoveStudentsAction(this), false);
        possibleActions.put(new MoveMNAction(this), false);
        possibleActions.put(new SelectCloudAction(this), false);
        possibleActions.put(new BuyCharacterAction(this), false);
    }

    private void updatePossibleActions() {
        for (Action a : possibleActions.keySet()){
            if (availableActions.contains(a.getType())){
                possibleActions.replace(a, true);
            }
        }
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public ClientHandler getClientHandler() {
        return clientHandler;
    }

    public TurnPhase getTurnPhase() {
        return turnPhase;
    }

    public Controller getController() {
        return controller;
    }

    public List<ActionType> getAvailableActions() {
        return availableActions;
    }

    public Map<Action, Boolean> getPossibleActions() {
        return possibleActions;
    }

    public void resetAllActionsForCurrentPlayer(){
        for (Action a : possibleActions.keySet()){
            a.resetAction(currentPlayer);
        }
    }

    public void executeAction(int actionIndex) {
        if(availableActions.get(actionIndex) == ActionType.NEXT_PHASE_ACTION){
            setTurnPhase(calculateNextTurnPhase());
            return;
        }
        Action chosenAction = possibleActions.keySet().stream().filter(a -> a.getType() == availableActions.get(actionIndex)).findFirst().get();
        clientHandler.setCurrentAction(chosenAction);
        chosenAction.execute();
    }

    public void nextAction(Action endedAction) {
        availableActions.remove(endedAction.getType());

        boolean alreadyExistsAction = availableActions.stream().anyMatch(a -> a == ActionType.BUY_CHARACTER_ACTION);
        if(!alreadyExistsAction && controller.getGame().isExpertMode() && !alreadyBoughtCharacter && canBuyCharacter()){
            availableActions.add(ActionType.BUY_CHARACTER_ACTION);
        }

        updatePossibleActions();

        for (ClientHandler ch : controller.getHandlers()){
            if (!ch.equals(clientHandler)){
                ch.sendMsgToClient(new BoardData(controller.getGame().getBoard().getLightBoard()));
            }
        }

        if(!availableActions.isEmpty()) {
            if (availableActions.size() == 1 && availableActions.get(0) == ActionType.BUY_CHARACTER_ACTION){
                availableActions.add(ActionType.NEXT_PHASE_ACTION);
            }
            clientHandler.sendMsgToClient(new ActionRequest(availableActions, controller.getGame().getBoard().getLightBoard()));
        } else {
            setTurnPhase(calculateNextTurnPhase());
        }
    }

    private TurnPhase calculateNextTurnPhase() {
        if(turnPhase.toString().equals("MoveStudentsPhase")){
            return new MoveMNPhase();
        }
        return new SelectCloudPhase();
    }

    public void setAlreadyBoughtCharacter(boolean alreadyBoughtCharacter) {
        this.alreadyBoughtCharacter = alreadyBoughtCharacter;
    }
}
