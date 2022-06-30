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
import java.util.function.Predicate;

/**
 * This class is useful to control a single turn.
 */
public class TurnController {
    private final Player currentPlayer;
    private final ClientHandler clientHandler;
    private TurnPhase turnPhase;
    private final Controller controller;
    private List<ActionType> availableActions;
    private final List<Action> possibleActions;
    private boolean alreadyBoughtCharacter = false;

    /**
     * Class constructor.
     * Initializes the structures that contain the possible/available actions a player can do during
     * their turn.
     *
     * @param currentPlayer     the current Player.
     * @param controller        the Controller to access the corresponding ClientHandler.
     */
    public TurnController(Player currentPlayer, Controller controller) {
        this.currentPlayer = currentPlayer;
        this.controller = controller;
        clientHandler = controller.getHandlerByNickname(currentPlayer.getNickname());
        availableActions = new ArrayList<>();
        possibleActions = new ArrayList<>();
        fillPossibleActions();
    }

    /**
     * Sets the given turn phase and the actions associated to it. Asks the client
     * to choose an action among the available ones.
     *
     * @param turnPhase         a TurnPhase.
     */
    public void setTurnPhase(TurnPhase turnPhase) {
        this.turnPhase = turnPhase;
        availableActions = turnPhase.getAvailableTurnActions();
        if(controller.getGame().isExpertMode() && !turnPhase.toString().equals("SelectCloudPhase") && !alreadyBoughtCharacter && canBuyCharacter()){
            availableActions.add(ActionType.BUY_CHARACTER_ACTION);
        }
        clientHandler.sendMsgToClient(new ActionRequest(availableActions, controller.getGame().getBoard().getLightBoard()));
    }

    /**
     * States whether the player can buy a character in that specific moment.
     *
     * @return                  a boolean whose value is:
     *                          <p>
     *                          -{@code true} if the Player can buy a GameCharacter;
     *                          </p> <p>
     *                          -{@code false} otherwise.
     *                          </p>
     */
    private boolean canBuyCharacter() {
        List<GameCharacter> buyableCharacters = new ArrayList<>();
        Predicate<GameCharacter> minstrelPredicate = c -> c.getName().equals("Minstrel");
        Predicate<GameCharacter> grannyGrassPredicate = c -> c.getName().equals("GrannyGrass");
        GameCharacter[] selectedCharacters = controller.getGame().getBoard().getSelectedCharacters();

        for (GameCharacter selectedCharacter : selectedCharacters) {
            if (currentPlayer.getCoins() >= selectedCharacter.getCost()) buyableCharacters.add(selectedCharacter);
        }

        if(buyableCharacters.stream().anyMatch(minstrelPredicate) && !canBuyMinstrel()){
            buyableCharacters.remove(buyableCharacters.stream().filter(minstrelPredicate).findFirst().get());
        }

        if(buyableCharacters.stream().anyMatch(grannyGrassPredicate) && !canBuyGrannyGrass(buyableCharacters.stream().filter(grannyGrassPredicate).findFirst().get())){
            buyableCharacters.remove(buyableCharacters.stream().filter(grannyGrassPredicate).findFirst().get());
        }

        return buyableCharacters.size() > 0;
    }

    /**
     * States whether the {@link GrannyGrassEffect GrannyGrass} character can be
     * bought that moment (at least 1 No Entry tile needs to be present on the card).
     *
     * @param selectedCharacter the GameCharacter.
     * @return                  a boolean whose value is:
     *                          <p>
     *                          -{@code true} if the Granny Grass character can be used;
     *                          </p> <p>
     *                          -{@code false} otherwise.
     *                          </p>
     */
    private boolean canBuyGrannyGrass(GameCharacter selectedCharacter) {
        return ((GrannyGrassEffect) selectedCharacter.getEffect()).getNoEntryTiles() > 0;
    }

    /**
     * States whether the {@link it.polimi.ingsw.model.effects.MinstrelEffect Minstrel}
     * character can be bought that moment (current player's dining room can't
     * be empty).
     *
     * @return                  a boolean whose value is:
     *                          <p>
     *                          -{@code true} if the Minstrel character can be used;
     *                          </p> <p>
     *                          -{@code false} otherwise.
     *                          </p>
     */
    private boolean canBuyMinstrel() {
        return !Arrays.stream(controller.getGame().getBoard().getCurrentPlayerSchoolBoard().getDiningRoom()).allMatch(t -> t == 0);
    }

    /**
     * Fills the possibleActions HashMap with all the actions that a player can
     * possibly do in a turn (move students, buy a character, move mother nature,
     * select a cloud). These are all initialized as false, then they are set as
     * true if they are available indeed.
     */
    private void fillPossibleActions() {
        possibleActions.add(new MoveStudentsAction(this));
        possibleActions.add(new MoveMNAction(this));
        possibleActions.add(new SelectCloudAction(this));
        possibleActions.add(new BuyCharacterAction(this));
    }

    /**
     * Returns the current player.
     *
     * @return                  the current Player.
     */
    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    /**
     * Returns the client handler corresponding to the current player.
     *
     * @return                  the ClientHandler.
     */
    public ClientHandler getClientHandler() {
        return clientHandler;
    }

    /**
     * Returns the controller.
     *
     * @return                  the Controller.
     */
    public Controller getController() {
        return controller;
    }

    /**
     * Executes the given action.
     *
     * @param actionIndex       the index of the Action.
     */
    public void executeAction(int actionIndex) {
        if(availableActions.get(actionIndex) == ActionType.NEXT_PHASE_ACTION){
            setTurnPhase(calculateNextTurnPhase());
            return;
        }
        Action chosenAction = possibleActions.stream().filter(a -> a.getType() == availableActions.get(actionIndex)).findFirst().get();
        clientHandler.setCurrentAction(chosenAction);
        chosenAction.execute();
    }

    /**
     * Switches to the next action after checking if the end game conditions are met.
     * If there aren't any available actions left in this turn phase, it calculates the next phase.
     *
     * @param endedAction       the Action that just ended.
     */
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

        for (ClientHandler ch : controller.getHandlers()){
            if (!ch.equals(clientHandler)){
                ch.sendMsgToClient(new BoardData(controller.getGame().getBoard().getLightBoard()));
            }
        }

        if(!availableActions.isEmpty() && !(availableActions.size() == 1 && availableActions.get(0) == ActionType.NEXT_PHASE_ACTION)) {
            if (availableActions.size() == 1 && availableActions.get(0) == ActionType.BUY_CHARACTER_ACTION){
                availableActions.add(0, ActionType.NEXT_PHASE_ACTION);
            }
            clientHandler.sendMsgToClient(new ActionRequest(availableActions, controller.getGame().getBoard().getLightBoard()));
        } else {
            setTurnPhase(calculateNextTurnPhase());
        }
    }

    /**
     * Determines which turn phase is next, based on the current one.
     *
     * @return                  the next TurnPhase.
     */
    private TurnPhase calculateNextTurnPhase() {
        if(turnPhase.toString().equals("MoveStudentsPhase")){
            return new MoveMNPhase();
        }
        return new SelectCloudPhase();
    }

    /**
     * Sets the value of the attribute that states whether a character was already
     * bought in this turn.
     *
     * @param alreadyBoughtCharacter a boolean whose value is:
     *                               <p>
     *                               -{@code true} if a character was already bought;
     *                               </p> <p>
     *                               -{@code false} otherwise.
     *                               </p>
     */
    public void setAlreadyBoughtCharacter(boolean alreadyBoughtCharacter) {
        this.alreadyBoughtCharacter = alreadyBoughtCharacter;
    }

}
