package it.polimi.ingsw.network.messages.serverMessages;

import it.polimi.ingsw.model.GameCharacter;
import it.polimi.ingsw.network.messages.MessageType;
import it.polimi.ingsw.view.ViewInterface;
import it.polimi.ingsw.view.utilities.lightclasses.LightBoard;

import java.util.List;

public class CharacterRequest extends GenericServerMessage {
    private final List<GameCharacter> availableCharacters;
    private final int playerCoins;
    private final LightBoard lightBoard;


    public CharacterRequest(List<GameCharacter> availableCharacters, int playerCoins, LightBoard lightBoard) {
        super(MessageType.CHARACTER_REQUEST);
        this.availableCharacters = availableCharacters;
        this.playerCoins = playerCoins;
        this.lightBoard = lightBoard;
    }

    @Override
    public void show(ViewInterface view) {
        view.printBoard(lightBoard);
        view.askCharacter(lightBoard);
    }

    @Override
    public String toString() {
        return "ServerMessage {" +
                "type=" + getType() +
                '}';
    }
}