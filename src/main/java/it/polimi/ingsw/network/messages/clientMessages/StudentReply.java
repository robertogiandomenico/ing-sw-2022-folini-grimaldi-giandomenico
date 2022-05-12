package it.polimi.ingsw.network.messages.clientMessages;

import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.network.messages.MessageType;
import it.polimi.ingsw.network.server.ClientHandler;
import it.polimi.ingsw.network.server.Server;

public class StudentReply extends GenericClientMessage {
    private Color studColor;

    public StudentReply(Color studColor) {
        super(MessageType.STUDENT_REPLY);
        this.studColor = studColor;
    }

    public Color getStudColor() {
        return studColor;
    }

    @Override
    public void execute(Server server, ClientHandler clientHandler) {

    }
}