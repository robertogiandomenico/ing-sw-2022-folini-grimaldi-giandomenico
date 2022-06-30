package it.polimi.ingsw.network.messages.clientMessages;

import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.network.messages.MessageType;
import it.polimi.ingsw.network.server.ClientHandler;
import it.polimi.ingsw.network.server.Server;
import it.polimi.ingsw.view.utilities.lightclasses.LightCharacter;

/**
 * This class represents a message that the client sends to the server, that
 * contains the information needed when a player chooses to use a character.
 * Follows a {@link it.polimi.ingsw.network.messages.serverMessages.CharacterRequest CharacterRequest}.
 */
public class CharacterReply extends GenericClientMessage{
    private static final long serialVersionUID = -7752965836181499280L;
    private final LightCharacter character;
    private final int archiIndex;
    private final int studentNumber;
    private final Color[] studColor;

    /**
     * Class constructor.
     *
     * @param character     the chosen LightCharacter.
     * @param archiIndex    the index of the chosen Archipelago.
     * @param studentNumber the chosen number of Students.
     * @param studColor     the chosen Student Colors.
     */
    public CharacterReply(LightCharacter character, int archiIndex, int studentNumber, Color[] studColor) {
        super(MessageType.CHARACTER_REPLY);
        this.character = character;
        this.archiIndex = archiIndex;
        this.studentNumber = studentNumber;
        this.studColor = studColor;
    }

    /**
     * Returns the character.
     *
     * @return              the chosen LightCharacter.
     */
    public LightCharacter getCharacter() {
        return character;
    }

    /**
     * Returns the index of the archipelago.
     *
     * @return              the index of the chosen Archipelago.
     */
    public int getArchiIndex() {
        return archiIndex;
    }

    /**
     * Returns the number of students.
     *
     * @return              the chosen number of Students.
     */
    public int getStudentNumber() {
        return studentNumber;
    }

    /**
     * Returns the colors of the students.
     *
     * @return              the Color Array of the chosen ones.
     */
    public Color[] getStudColor() {
        return studColor;
    }

    /**
     * Executes the specific action based on the message.
     *
     * @param server        the Server.
     * @param clientHandler the ClientHandler.
     */
    @Override
    public void execute(Server server, ClientHandler clientHandler) {
        clientHandler.getCurrentAction().receiveMessage(this);
    }
}