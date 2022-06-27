package it.polimi.ingsw.model.effects;

import it.polimi.ingsw.model.*;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * This class implements the {@link Effect} of the Jester character.
 * Up to 3 students can be taken from the card and replaced with the same number
 * of students from the player's entrance.
 */
public class JesterEffect implements Effect {
    Student[] students = new Student[6];

    /**
     * Applies the changes caused by the effect of the Jester character.
     * The students chosen from the card will be replaced with the students chosen
     * from the entrance.
     *
     * @param board            the Board of the Game (to access current player
     *                         SchoolBoard).
     * @param archiIndex       the selected Archipelago (unused in this case).
     * @param numOfStudents    the number of Students to take and replace.
     * @param studColors       the Colors of the Students (the first numOfStudents
     *                         Colors in studColors[] will be those of the Students
     *                         chosen FROM THIS CARD; the remaining will be those of
     *                         the Students chosen FROM THE ENTRANCE).
     */
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
                if (students[j] != null && studColors[i] == students[j].getColor() && !alreadyPresent(j, studentIndexesOnCard)){
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

    /**
     * Checks if a given number is within the elements of an array.
     * Used in {@link JesterEffect#applyEffect}.
     *
     * @param index            the selected number.
     * @param indexes          an Array of indexes.
     *
     * @return                 a boolean whose value is:
     *                         <p>
     *                         -{@code true} if there is the sought match;
     *                         </p> <p>
     *                         -{@code false} otherwise.
     *                         </p>
     */
    private boolean alreadyPresent(int index, int[] indexes){
        for (int i : indexes){
            if (i == index) return true;
        }
        return false;
    }

    /**
     * Puts the given students on the card.
     *
     * @param students         the Student Array of students to put on the card.
     */
    public void setStudents(Student[] students){
        this.students = students;
    }

    /**
     * Takes students from the card.
     *
     * @return                 the Student Array of removed students.
     */
    public Student[] getStudents() {
        return students;
    }

}