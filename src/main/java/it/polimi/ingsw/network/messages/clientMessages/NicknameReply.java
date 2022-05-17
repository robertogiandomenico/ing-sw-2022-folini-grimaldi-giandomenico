package it.polimi.ingsw.network.messages.clientMessages;

import it.polimi.ingsw.controller.phases.ClientHandlerPhases;
import it.polimi.ingsw.network.messages.MessageType;
import it.polimi.ingsw.network.messages.serverMessages.NicknameRequest;
import it.polimi.ingsw.network.server.ClientHandler;
import it.polimi.ingsw.network.server.Server;

public class NicknameReply extends GenericClientMessage {
    private String nickname;

    public NicknameReply(String nickname) {
        super(MessageType.NICKNAME_REPLY);
        this.nickname = nickname;
    }

    public String getNickname() {
        return nickname;
    }

    @Override
    public void execute(Server server, ClientHandler clientHandler) {
        if(!(clientHandler.getClientHandlerPhase() == ClientHandlerPhases.WAITING_NICKNAME)){
            return;
        }
        String REGEX = "^([a-zA-Z]+\\w{2,10})$";
        if(!nickname.matches(REGEX)){
            clientHandler.sendMsgToClient(new NicknameRequest());
        } else {
            clientHandler.setClientNickname(nickname);
        }
    }
}
