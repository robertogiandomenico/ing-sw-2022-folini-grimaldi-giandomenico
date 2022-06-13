package it.polimi.ingsw.network.messages.serverMessages;

import it.polimi.ingsw.model.GameCharacter;
import it.polimi.ingsw.network.messages.MessageType;
import it.polimi.ingsw.view.ViewInterface;
import it.polimi.ingsw.view.utilities.lightclasses.LightBoard;

import java.util.List;

/**
 * This class represents a message that the server sends to the client,
 * requesting information regarding the activation of a character.
 */
public class CharacterRequest extends GenericServerMessage {
    private final LightBoard lightBoard;

    /**
     * Class constructor.
     *
     * @param lightBoard    a LightBoard.
     */
    public CharacterRequest(LightBoard lightBoard) {
        super(MessageType.CHARACTER_REQUEST);
        this.lightBoard = lightBoard;
    }

    /**
     * Shows the request (CLI or GUI).
     *
     * @param view          a ViewInterface.
     */
    @Override
    public void show(ViewInterface view) {
        view.askCharacter(lightBoard);
    }

    @Override
    public String toString() {
        return "ServerMessage {" +
                "type=" + getType() +
                '}';
    }
}