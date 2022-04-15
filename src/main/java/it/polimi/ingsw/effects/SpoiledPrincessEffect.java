package it.polimi.ingsw.effects;
import it.polimi.ingsw.model.*;

public class SpoiledPrincessEffect implements Effect {
    Student[] students = new Student[4];

    public void applyEffect(Board board, int archiIndex, int numOfStudents, Color...studColors) {
        int selectedStudent;
        for(selectedStudent = 0; selectedStudent < students.length; selectedStudent++){
            if(students[selectedStudent].getColor() == studColors[numOfStudents-1]) break;
        }

        board.getCurrentPlayerSchoolBoard().addToDiningRoom(board.mapToIndex(students[selectedStudent].getColor()));

        students[selectedStudent] = board.getBag().draw();
    }

    public void setStudents(Student[] students) {
        this.students = students;
    }

}