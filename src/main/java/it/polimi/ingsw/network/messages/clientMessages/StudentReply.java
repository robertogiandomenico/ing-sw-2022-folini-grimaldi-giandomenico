package it.polimi.ingsw.network.messages.clientMessages;

import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.network.messages.MessageType;
import it.polimi.ingsw.network.server.ClientHandler;
import it.polimi.ingsw.network.server.Server;

/**
 * This class represents a message that the client sends to the server, that
 * contains the color of a student.
 * Follows a {@link it.polimi.ingsw.network.messages.serverMessages.StudentRequest StudentRequest}.
 */
public class StudentReply extends GenericClientMessage {
    private Color studColor;

    /**
     * Class constructor.
     *
     * @param studColor the Color of the chosen Student.
     */
    public StudentReply(Color studColor) {
        super(MessageType.STUDENT_REPLY);
        this.studColor = studColor;
    }

    /**
     * Returns the student color.
     *
     * @return          the Color of the chosen Student.
     */
    public Color getStudColor() {
        return studColor;
    }

    @Override
    public void execute(Server server, ClientHandler clientHandler) {
        clientHandler.getCurrentAction().receiveMessage(this);
    }
}