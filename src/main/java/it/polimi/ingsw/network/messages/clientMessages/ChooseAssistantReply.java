package it.polimi.ingsw.network.messages.clientMessages;

import it.polimi.ingsw.model.Assistant;
import it.polimi.ingsw.network.messages.MessageType;

public class ChooseAssistantReply extends GenericClientMessage {
    private Assistant assistant;

    public ChooseAssistantReply(Assistant assistant) {
        super(MessageType.CHOOSE_ASSISTANT_REPLY);
        this.assistant = assistant;
    }

    public Assistant getAssistant() {
        return assistant;
    }
}
