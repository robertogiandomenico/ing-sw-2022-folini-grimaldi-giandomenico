package it.polimi.ingsw.network.messages.clientMessages;

import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.network.messages.MessageType;

public class StudentReply extends GenericClientMessage {
    private Color studColor;

    public StudentReply(Color studColor) {
        super(MessageType.STUDENT_REPLY);
        this.studColor = studColor;
    }

    public Color getStudColor() {
        return studColor;
    }
}