package it.polimi.ingsw.network.messages.clientMessages;

import it.polimi.ingsw.network.messages.MessageType;

public class NicknameReply extends GenericClientMessage {
    private String nickname;

    public NicknameReply(String nickname) {
        super(MessageType.NICKNAME_REPLY);
        this.nickname = nickname;
    }

    public String getNickname() {
        return nickname;
    }
}
