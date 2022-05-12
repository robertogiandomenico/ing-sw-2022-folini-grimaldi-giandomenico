package it.polimi.ingsw.network.messages.serverMessages;

import it.polimi.ingsw.model.Assistant;
import it.polimi.ingsw.network.messages.MessageType;

import java.util.List;

public class ChooseAssistantRequest extends GenericServerMessage {
    private List<Assistant> availableAssistants;
    private List<Assistant> discardedAssistants;

    public ChooseAssistantRequest(List<Assistant> availableAssistants, List<Assistant> discardedAssistants) {
        super(MessageType.CHOOSE_ASSISTANT_REQUEST);
        this.availableAssistants = availableAssistants;
        this.discardedAssistants = discardedAssistants;
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

    public List<Assistant> getAvailableAssistants() {
        return availableAssistants;
    }

    public List<Assistant> getDiscardedAssistants() {
        return discardedAssistants;
    }
}
