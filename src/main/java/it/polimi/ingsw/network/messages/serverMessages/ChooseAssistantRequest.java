package it.polimi.ingsw.network.messages.serverMessages;

import it.polimi.ingsw.model.Assistant;
import it.polimi.ingsw.network.messages.MessageType;

import java.util.List;

public class ChooseAssistantRequest extends GenericServerMessage {
    private List<Assistant> availableAssistants;

    public ChooseAssistantRequest(List<Assistant> availableAssistants) {
        super(MessageType.CHOOSE_ASSISTANT_REQUEST);
        this.availableAssistants = availableAssistants;
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
}
