package it.polimi.ingsw.network.messages.clientMessages;

import it.polimi.ingsw.controller.phases.ClientHandlerPhases;
import it.polimi.ingsw.network.messages.MessageType;
import it.polimi.ingsw.network.messages.serverMessages.NicknameRequest;
import it.polimi.ingsw.network.server.ClientHandler;
import it.polimi.ingsw.network.server.Server;

/**
 * This class represents a message that the client sends to the server, to
 * set the nickname.
 * Follows a {@link it.polimi.ingsw.network.messages.serverMessages.NicknameRequest NicknameRequest}.
 */
public class NicknameReply extends GenericClientMessage {
    private static final long serialVersionUID = -2849240260911470749L;
    private String nickname;

    /**
     * Class constructor.
     *
     * @param nickname      the nickname chosen by the Player.
     */
    public NicknameReply(String nickname) {
        super(MessageType.NICKNAME_REPLY);
        this.nickname = nickname;
    }

    /**
     * Returns the nickname.
     *
     * @return              the chosen nickname.
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * Executes the specific action based on the message.
     *
     * @param server        the Server.
     * @param clientHandler the ClientHandler.
     */
    @Override
    public void execute(Server server, ClientHandler clientHandler) {
        if(!(clientHandler.getClientHandlerPhase() == ClientHandlerPhases.WAITING_NICKNAME)){
            return;
        }
        String REGEX = "^([a-zA-Z]\\w{2,10})$";
        if(!nickname.matches(REGEX)){
            clientHandler.sendMsgToClient(new NicknameRequest());
        } else {
            clientHandler.setClientNickname(nickname);
        }
    }
}
