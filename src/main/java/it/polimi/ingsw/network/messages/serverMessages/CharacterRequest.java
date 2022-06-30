package it.polimi.ingsw.network.messages.serverMessages;

import it.polimi.ingsw.network.messages.MessageType;
import it.polimi.ingsw.view.ViewInterface;
import it.polimi.ingsw.view.utilities.lightclasses.LightBoard;

/**
 * This class represents a message that the server sends to the client,
 * requesting information regarding the activation of a character.
 */
public class CharacterRequest extends GenericServerMessage {
    private static final long serialVersionUID = 1875234681947328963L;
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