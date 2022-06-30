package it.polimi.ingsw.network.messages.clientMessages;

import it.polimi.ingsw.model.Assistant;
import it.polimi.ingsw.network.messages.MessageType;
import it.polimi.ingsw.network.server.ClientHandler;
import it.polimi.ingsw.network.server.Server;

/**
 * This class represents a message that the client sends to the server, that
 * contains the chosen assistant.
 * Follows a {@link it.polimi.ingsw.network.messages.serverMessages.ChooseAssistantRequest ChooseAssistantRequest}.
 */
public class ChooseAssistantReply extends GenericClientMessage {
    private static final long serialVersionUID = -4576301150786574144L;
    private Assistant assistant;

    /**
     * Class constructor.
     *
     * @param assistant     the chosen Assistant.
     */
    public ChooseAssistantReply(Assistant assistant) {
        super(MessageType.CHOOSE_ASSISTANT_REPLY);
        this.assistant = assistant;
    }

    /**
     * Returns the assistant.
     *
     * @return              the chosen Assistant.
     */
    public Assistant getAssistant() {
        return assistant;
    }

    /**
     * Executes the specific action based on the message.
     *
     * @param server        the Server.
     * @param clientHandler the ClientHandler.
     */
    @Override
    public void execute(Server server, ClientHandler clientHandler) {
        clientHandler.getController().receiveMessage(this);
    }
}
