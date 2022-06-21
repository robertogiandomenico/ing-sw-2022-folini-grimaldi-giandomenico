package it.polimi.ingsw.controller;

import it.polimi.ingsw.controller.actions.*;
import it.polimi.ingsw.controller.phases.turnPhases.MoveMNPhase;
import it.polimi.ingsw.controller.phases.turnPhases.SelectCloudPhase;
import it.polimi.ingsw.controller.phases.turnPhases.TurnPhase;
import it.polimi.ingsw.model.GameCharacter;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.effects.GrannyGrassEffect;
import it.polimi.ingsw.network.messages.serverMessages.ActionRequest;
import it.polimi.ingsw.network.messages.serverMessages.BoardData;
import it.polimi.ingsw.network.server.ClientHandler;

import java.util.*;

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
        List<Integer> buyableCharacters = new ArrayList<>();
        GameCharacter[] selectedCharacters = controller.getGame().getBoard().getSelectedCharacters();

        for(int i=0; i < selectedCharacters.length; i++){
            if(currentPlayer.getCoins() >= selectedCharacters[i].getCost()) buyableCharacters.add(i);
        }

        return  buyableCharacters.size() != 0
                && ((buyableCharacters.size() != 1 || !selectedCharacters[buyableCharacters.get(0)].getName().equals("Minstrel") || canBuyMinstrel())
                || (buyableCharacters.size() != 1 || !selectedCharacters[buyableCharacters.get(0)].getName().equals("GrannyGrass") || canBuyGrannyGrass(selectedCharacters[buyableCharacters.get(0)])));
    }

    private boolean canBuyGrannyGrass(GameCharacter selectedCharacter) {
        return ((GrannyGrassEffect) selectedCharacter.getEffect()).getNoEntryTiles() > 0;
    }

    private boolean canBuyMinstrel() {
        return !Arrays.stream(controller.getGame().getBoard().getCurrentPlayerSchoolBoard().getDiningRoom()).allMatch(t -> t == 0);
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

    public Controller getController() {
        return controller;
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

        if(endedAction.getType() == ActionType.MOVE_MN_ACTION) {
            controller.checkTowerConditionsWin();
            controller.checkIslandConditionsWin();
        }

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
