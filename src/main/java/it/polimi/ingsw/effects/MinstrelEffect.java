package it.polimi.ingsw.effects;
import it.polimi.ingsw.model.*;

public class MinstrelEffect implements Effect {

    public void applyEffect(Board board, int archiIndex, int numOfStudents, Color...studColors) {
        //the user will choose how many students they want to move and this number will be saved in the variable numOfStudents
        //the first numOfStudents Colors in studColors[] will be the colors of the students chosen FROM THE DINING ROOM, while the remaining
        //will be the colors of the students chosen FROM THE ENTRANCE

        Student[] studentsFromDR = new Student[numOfStudents];
        Student[] studentsFromEntrance = new Student[numOfStudents];
        SchoolBoard currentPlayerSB = board.getCurrentPlayerSchoolBoard();

        //Removing the students from the dining room
        for (int i = 0; i < numOfStudents ; i++) {
            currentPlayerSB.removeFromDiningRoom(board.mapToIndex(studColors[i]));
            studentsFromDR[i] = new Student(studColors[i]);
        }

        //Removing the students from the entrance
        for (int i = 0; i < numOfStudents ; i++) {
            studentsFromEntrance[i] = currentPlayerSB.removeFromEntrance(studColors[i + numOfStudents]);
        }

        //Switching the removed students between the dining room and the entrance
        for (int i = 0; i < numOfStudents; i++) {
            currentPlayerSB.addToEntrance(studentsFromDR[i]);
            currentPlayerSB.addToDiningRoom(board.mapToIndex(studentsFromEntrance[i].getColor()));
        }
    }

}