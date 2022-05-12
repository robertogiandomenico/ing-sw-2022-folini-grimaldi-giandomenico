package it.polimi.ingsw.network.messages.serverMessages;

import it.polimi.ingsw.model.GameCharacter;
import it.polimi.ingsw.network.messages.MessageType;

import java.util.List;

public class CharacterRequest extends GenericServerMessage {
    private List<GameCharacter> availableCharacters;


    public CharacterRequest(List<GameCharacter> availableCharacters) {
        super(MessageType.CHARACTER_REQUEST);
        this.availableCharacters = availableCharacters;
    }

    @Override
    public void show() {

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
}