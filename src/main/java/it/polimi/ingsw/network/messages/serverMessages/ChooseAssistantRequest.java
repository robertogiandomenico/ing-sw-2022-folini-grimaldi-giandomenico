package it.polimi.ingsw.network.messages.serverMessages;

import it.polimi.ingsw.model.Assistant;
import it.polimi.ingsw.network.messages.MessageType;
import it.polimi.ingsw.view.ViewInterface;
import it.polimi.ingsw.view.utilities.lightclasses.LightBoard;

import java.util.List;

/**
 * This class represents a message that the server sends to the client,
 * asking to select an assistant.
 */
public class ChooseAssistantRequest extends GenericServerMessage {
    private static final long serialVersionUID = -5600698207138090264L;
    private final List<Assistant> availableAssistants;
    private final List<Assistant> discardedAssistants;
    private final LightBoard lightBoard;

    /**
     * Class constructor.
     *
     * @param availableAssistants   the Assistant List of available ones.
     * @param discardedAssistants   the Assistant List of discarded ones.
     * @param lightBoard            the LightBoard.
     */
    public ChooseAssistantRequest(List<Assistant> availableAssistants, List<Assistant> discardedAssistants, LightBoard lightBoard) {
        super(MessageType.CHOOSE_ASSISTANT_REQUEST);
        this.availableAssistants = availableAssistants;
        this.discardedAssistants = discardedAssistants;
        this.lightBoard = lightBoard;
    }

    /**
     * Shows the request (CLI or GUI).
     *
     * @param view                  a ViewInterface.
     */
    @Override
    public void show(ViewInterface view) {
        view.printBoard(lightBoard);
        view.askAssistant(availableAssistants, discardedAssistants);
    }

    @Override
    public String toString() {
        return "ServerMessage {" +
                "type=" + getType() +
                '}';
    }
}
