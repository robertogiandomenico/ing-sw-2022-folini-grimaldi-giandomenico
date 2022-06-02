package it.polimi.ingsw.model.effects;

import it.polimi.ingsw.model.*;

/**
 * This class implements the {@link Effect} of the Monk character.
 * 1 student from this card can be placed on an island of the player's choice, and
 * then the card is refilled.
 */
public class MonkEffect implements Effect {
    Student[] students = new Student[4];

    /**
     * Applies the changes caused by the effect of the Monk character.
     * Puts the selected student on the selected archipelago, and then refills with a
     * student drawn from the bag.
     *
     *  @param board                the Board of the game.
     *  @param archiIndex           the selected Archipelago.
     *  @param numOfStudents        the number of Students.
     *  @param studColors           the Colors of the Students (one in this case).
     */
    public void applyEffect(Board board, int archiIndex, int numOfStudents, Color...studColors) {

        //This comments along with the line of code 18 are something I did before referring to the student by its index, I left them because they contain
        //some useful things we should remember later about error checking

        //orElse(null) sets selectedStudent = null if there isn't a student with that color on the card
        //the way we will deal with this null value depends on where we will do the error checking of the user input (controller or model)
        //if we will decide to do it in the controller, selectedStudent will never be = null (but the orElse(null) part needs to stay
        //because it's mandatory with this kind of operations on streams)

        //Student selectedStudent = Arrays.stream(students).filter(s -> s.getColor() == studColor[numOfStudents-1]).findFirst().orElse(null);

        //In the end I decided to treat the selectedStudent like an index this to simplify the replacing operation
        int selectedStudent;
        for(selectedStudent = 0; selectedStudent < students.length; selectedStudent++){
            if(students[selectedStudent] != null && students[selectedStudent].getColor() == studColors[numOfStudents-1]) break;
        }

        Archipelago selectedArchipelago = board.getArchipelago(archiIndex);

        selectedArchipelago.getIslands().get(0).addStudent(students[selectedStudent]);

        students[selectedStudent] = board.getBag().draw();

    }

    /**
     * Puts the students on the card.
     *
     * @param students              a Student Array of students to put on the card.
     */
    public void setStudents(Student[] students) {
        this.students = students;
    }

    /**
     * Takes students from the card.
     *
     * @return                      the Student Array of removed students.
     */
    public Student[] getStudents() {
        return students;
    }
}