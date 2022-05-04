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

import java.util.Arrays;
import java.util.stream.Collectors;

public class BuyCharacterAction implements Action{
    private final ActionType type = ActionType.BUY_CHARACTER_ACTION;
    private Player currentPlayer;
    private ClientHandler clientHandler;
    private final TurnController turnController;

    public BuyCharacterAction(TurnController turnController) {
        this.turnController = turnController;
        currentPlayer = turnController.getCurrentPlayer();
        clientHandler = turnController.getClientHandler();
    }

    @Override
    public void execute() {
        GameCharacter[] characters = turnController.getController().getGame().getBoard().getSelectedCharacters();
        clientHandler.sendMsgToClient(new CharacterRequest(Arrays.stream(characters).collect(Collectors.toList())));
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
        if (!(msg.getType() == MessageType.CHARACTER_REPLY)){
            return;
        }

        GameCharacter character = ((CharacterReply) msg).getCharacter();
        int archiIndex = ((CharacterReply) msg).getArchiIndex();
        int studentNumber = ((CharacterReply) msg).getStudentNumber();
        Color[] studColors = ((CharacterReply) msg).getStudColor();
        turnController.getController().getGame().getBoard().playCharacter(character.getName(), archiIndex, studentNumber, studColors);
    }

}
