package it.polimi.ingsw.model.effects;

import it.polimi.ingsw.model.*;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class JesterEffect implements Effect {
    Student[] students = new Student[6];

    public void applyEffect(Board board, int archiIndex, int numOfStudents, Color...studColors) {
        //the user will choose how many students they want to move and this number will be saved in the variable numOfStudents
        //the first numOfStudents Colors in studColors[] will be the colors of the students chosen FROM THIS CARD, while the remaining
        //will be the colors of the students chosen FROM THE ENTRANCE

        int[] studentIndexesOnCard = new int[numOfStudents];
        Student[] studentsFromEntrance = new Student[numOfStudents];
        SchoolBoard currentPlayerSB = board.getCurrentPlayerSchoolBoard();
        Arrays.fill(studentIndexesOnCard, -1);

        //Finding the indexes of numOfStudents students with the specified color, in order to move them from the card to the entrance
        for (int i = 0; i < numOfStudents; i++) {
            for (int j = 0; j < students.length; j++) {
                if (studColors[i] == students[j].getColor() && !alreadyPresent(j, studentIndexesOnCard)){
                    studentIndexesOnCard[i] = j;
                    break;
                }
            }
        }

        //Removing the students with the specified color from the entrance and then adding the students chosen from the card
        for (int i = 0; i < numOfStudents; i++) {
            studentsFromEntrance[i] = currentPlayerSB.removeFromEntrance(studColors[i+numOfStudents]);
        }

        for (int i = 0; i < numOfStudents; i++) {
            currentPlayerSB.addToEntrance(students[studentIndexesOnCard[i]]);
        }

        //Replacing students chosen from the card with the students chosen from the entrance
        for (int i = 0; i < numOfStudents; i++) {
            students[studentIndexesOnCard[i]] = studentsFromEntrance[i];
        }
    }

    private boolean alreadyPresent(int index, int[] indexes){
        for (int i : indexes){
            if (i == index) return true;
        }
        return false;
    }

    public void setStudents(Student[] students){
        this.students = students;
    }

    public Student[] getStudents() {
        return students;
    }

}