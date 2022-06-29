package it.polimi.ingsw.controller.actions;

import it.polimi.ingsw.controller.TurnController;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.GameCharacter;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.network.messages.MessageType;
import it.polimi.ingsw.network.messages.clientMessages.CharacterReply;
import it.polimi.ingsw.network.messages.clientMessages.GenericClientMessage;
import it.polimi.ingsw.network.messages.serverMessages.CharacterRequest;
import it.polimi.ingsw.network.server.ClientHandler;
import it.polimi.ingsw.view.utilities.lightclasses.LightCharacter;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * This class represents the action of buying a character during a turn.
 */
public class BuyCharacterAction implements Action{
    private final ActionType type = ActionType.BUY_CHARACTER_ACTION;
    private final Player currentPlayer;
    private final ClientHandler clientHandler;
    private final TurnController turnController;

    /**
     * Class constructor.
     *
     * @param turnController a TurnController.
     */
    public BuyCharacterAction(TurnController turnController) {
        this.turnController = turnController;
        currentPlayer = turnController.getCurrentPlayer();
        clientHandler = turnController.getClientHandler();
    }

    /**
     * Executes the action sending a request to the client.
     */
    @Override
    public void execute() {
        clientHandler.sendMsgToClient(new CharacterRequest(turnController.getController().getGame().getBoard().getLightBoard()));
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
        if (!(msg.getType() == MessageType.CHARACTER_REPLY)){
            return;
        }

        LightCharacter character = ((CharacterReply) msg).getCharacter();
        int archiIndex = ((CharacterReply) msg).getArchiIndex();
        int studentNumber = ((CharacterReply) msg).getStudentNumber();
        Color[] studColors = ((CharacterReply) msg).getStudColor();
        turnController.getController().getGame().getBoard().playCharacter(character.getName(), archiIndex, studentNumber, studColors);
        turnController.setAlreadyBoughtCharacter(true);
        turnController.nextAction(this);
    }

}
