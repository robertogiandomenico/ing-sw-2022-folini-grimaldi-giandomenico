package it.polimi.ingsw.network.messages.serverMessages;

import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.network.messages.MessageType;
import it.polimi.ingsw.view.ViewInterface;

import java.util.List;

/**
 * This class represents a message that the server sends to the client,
 * asking to select a student.
 */
public class StudentRequest extends GenericServerMessage {
    private final List<Color> availableColors;

    /**
     * Class constructor specifying the colors of the students that can be chosen.
     *
     * @param availableColors   the Color List of the available ones.
     */
    public StudentRequest(List<Color> availableColors) {
        super(MessageType.STUDENT_REQUEST);
        this.availableColors = availableColors;
    }

    /**
     * Shows the request (CLI or GUI).
     *
     * @param view              a ViewInterface.
     */
    @Override
    public void show(ViewInterface view) {
        view.askStudent(availableColors);
    }

    @Override
    public String toString() {
        return "ServerMessage {" +
                "type=" + getType() +
                '}';
    }
}