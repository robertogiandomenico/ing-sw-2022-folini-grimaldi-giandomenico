package it.polimi.ingsw.model.effects;

import it.polimi.ingsw.model.*;

/**
 * This class implements the {@link Effect} of the Minstrel character.
 * Up to 2 students can be exchanged between player's dining room and entrance.
 */
public class MinstrelEffect implements Effect {

    /**
     * Applies the changes caused by the effect of the Minstrel character.
     * The students chosen from the dining room will be exchanged with the students
     * chosen from the entrance.
     *
     * @param board            the Board of the game.
     * @param archiIndex       the selected Archipelago.
     * @param numOfStudents    the number of Students.
     * @param studColors       the Colors of the Students (the first numOfStudents
     *                         Colors in studColors[] will be those of the Students
     *                         chosen FROM THE DINING ROOM; the remaining will be
     *                         those of the Students chosen FROM THE ENTRANCE).
     */
    public void applyEffect(Board board, int archiIndex, int numOfStudents, Color...studColors) {
        //the user will choose how many students they want to move and this number will be saved in the variable numOfStudents

        Student[] studentsFromDR = new Student[numOfStudents];
        Student[] studentsFromEntrance = new Student[numOfStudents];
        SchoolBoard currentPlayerSB = board.getCurrentPlayerSchoolBoard();

        //Removing the students from the dining room
        for (int i = 0; i < numOfStudents ; i++) {
            currentPlayerSB.removeFromDiningRoom(board.mapToIndex(studColors[i]), false);
            studentsFromDR[i] = new Student(studColors[i]);
        }

        //Removing the students from the entrance
        for (int i = 0; i < numOfStudents ; i++) {
            studentsFromEntrance[i] = currentPlayerSB.removeFromEntrance(studColors[i + numOfStudents]);
        }

        //Switching the removed students between the dining room and the entrance
        for (int i = 0; i < numOfStudents; i++) {
            if(studentsFromEntrance[i] != null){
                currentPlayerSB.addToEntrance(studentsFromDR[i]);
                currentPlayerSB.addToDiningRoom(board.mapToIndex(studentsFromEntrance[i].getColor()));
            } else {
                currentPlayerSB.addToDiningRoom(board.mapToIndex(studentsFromDR[i].getColor()));
            }
        }
    }

}