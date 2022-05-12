package it.polimi.ingsw.controller;

import it.polimi.ingsw.controller.actions.*;
import it.polimi.ingsw.controller.phases.turnPhases.TurnPhase;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.network.server.ClientHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TurnController {
    private Player currentPlayer;
    private ClientHandler clientHandler;
    private TurnPhase turnPhase;
    private Controller controller;
    private List<ActionType> availableActions;
    private Map<Action, Boolean> possibleActions;

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
        updatePossibleActions();
        //turnPhase.execute();
    }

    private void fillPossibleActions() {
        possibleActions.put(new ChooseAssistantAction(this), false);
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
}
