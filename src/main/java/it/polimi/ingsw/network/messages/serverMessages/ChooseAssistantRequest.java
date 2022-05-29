package it.polimi.ingsw.network.messages.serverMessages;

import it.polimi.ingsw.model.Assistant;
import it.polimi.ingsw.network.messages.MessageType;
import it.polimi.ingsw.view.ViewInterface;
import it.polimi.ingsw.view.utilities.lightclasses.LightBoard;

import java.util.List;

public class ChooseAssistantRequest extends GenericServerMessage {
    private final List<Assistant> availableAssistants;
    private final List<Assistant> discardedAssistants;
    private final LightBoard lightBoard;

    public ChooseAssistantRequest(List<Assistant> availableAssistants, List<Assistant> discardedAssistants, LightBoard lightBoard) {
        super(MessageType.CHOOSE_ASSISTANT_REQUEST);
        this.availableAssistants = availableAssistants;
        this.discardedAssistants = discardedAssistants;
        this.lightBoard = lightBoard;
    }

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
