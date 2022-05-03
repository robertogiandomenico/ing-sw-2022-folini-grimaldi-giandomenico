package it.polimi.ingsw.network.messages.clientMessages;

import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.GameCharacter;
import it.polimi.ingsw.network.messages.MessageType;

public class CharacterReply extends GenericClientMessage{
    private GameCharacter character;
    private int archiIndex;
    private int studentNumber;
    private Color[] studColor;

    public CharacterReply(GameCharacter character, int archiIndex, int studentNumber, Color[] studColor) {
        super(MessageType.CHARACTER_REPLY);
        this.character = character;
        this.archiIndex = archiIndex;
        this.studentNumber = studentNumber;
        this.studColor = studColor;
    }

    public GameCharacter getCharacter() {
        return character;
    }

    public int getArchiIndex() {
        return archiIndex;
    }

    public int getStudentNumber() {
        return studentNumber;
    }

    public Color[] getStudColor() {
        return studColor;
    }
}