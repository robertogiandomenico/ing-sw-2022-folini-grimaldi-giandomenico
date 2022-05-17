package it.polimi.ingsw.network.messages.clientMessages;

import it.polimi.ingsw.model.Assistant;
import it.polimi.ingsw.network.messages.MessageType;
import it.polimi.ingsw.network.server.ClientHandler;
import it.polimi.ingsw.network.server.Server;

public class ChooseAssistantReply extends GenericClientMessage {
    private Assistant assistant;

    public ChooseAssistantReply(Assistant assistant) {
        super(MessageType.CHOOSE_ASSISTANT_REPLY);
        this.assistant = assistant;
    }

    public Assistant getAssistant() {
        return assistant;
    }

    @Override
    public void execute(Server server, ClientHandler clientHandler) {
        clientHandler.getController().receiveMessage(this);
    }
}
