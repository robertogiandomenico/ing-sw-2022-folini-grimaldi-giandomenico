package it.polimi.ingsw.network.messages.serverMessages;

import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.network.messages.MessageType;

import java.util.List;

public class StudentRequest extends GenericServerMessage {
    private List<Color> availableColors;

    public StudentRequest(List<Color> availableColors) {
        super(MessageType.STUDENT_REQUEST);
        this.availableColors = availableColors;
    }

    @Override
    public String toString() {
        return "ServerMessage {" +
                "type=" + getType() +
                '}';
    }

    public List<Color> getAvailableColors() {
        return availableColors;
    }
}