package it.polimi.ingsw.network.messages.serverMessages;

import it.polimi.ingsw.model.GameCharacter;
import it.polimi.ingsw.network.messages.MessageType;
import it.polimi.ingsw.view.ViewInterface;

import java.util.List;

public class CharacterRequest extends GenericServerMessage {
    private List<GameCharacter> availableCharacters;
    int playerCoins;

    public CharacterRequest(List<GameCharacter> availableCharacters, int playerCoins) {
        super(MessageType.CHARACTER_REQUEST);
        this.availableCharacters = availableCharacters;
        this.playerCoins = playerCoins;
    }

    @Override
    public void show(ViewInterface view) {

    }

    @Override
    public String toString() {
        return "ServerMessage {" +
                "type=" + getType() +
                '}';
    }

    public List<GameCharacter> getAvailableCharacters() {
        return availableCharacters;
    }

    public int getPlayerCoins() {
        return playerCoins;
    }
}