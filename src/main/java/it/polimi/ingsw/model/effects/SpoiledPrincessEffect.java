package it.polimi.ingsw.model.effects;

import it.polimi.ingsw.model.*;

/**
 * This class implements the {@link Effect} of the Spoiled Princess character.
 * 1 student can be taken from this card and put in the player's dining room.
 */
public class SpoiledPrincessEffect implements Effect {
    Student[] students = new Student[4];

    /**
     * Applies the changes caused by the effect of the Spoiled Princess character.
     * Puts the selected student in the player's dining room, and then refills with a
     * student drawn from the bag.
     *
     * @param board                the Board of the game.
     * @param archiIndex           the selected Archipelago (unused in this case).
     * @param numOfStudents        the number of Students.
     * @param studColors           the Color of the Student (one in this case).
     */
    public void applyEffect(Board board, int archiIndex, int numOfStudents, Color...studColors) {
        int selectedStudent;
        for(selectedStudent = 0; selectedStudent < students.length; selectedStudent++){
            if(students[selectedStudent].getColor() == studColors[numOfStudents-1]) break;
        }

        board.getCurrentPlayerSchoolBoard().addToDiningRoom(board.mapToIndex(students[selectedStudent].getColor()));
        board.updatePlayerCoins(board.getCurrentPlayerSchoolBoard(), board.mapToIndex(students[selectedStudent].getColor()));

        students[selectedStudent] = board.getBag().draw();

    }

    /**
     * Puts students on the card.
     *
     * @param students             the Student Array of students to put on the card.
     */
    public void setStudents(Student[] students) {
        this.students = students;
    }

    /**
     * Takes students from the card.
     *
     * @return                     the Student Array of removed students.
     */
    public Student[] getStudents() {
        return students;
    }

}