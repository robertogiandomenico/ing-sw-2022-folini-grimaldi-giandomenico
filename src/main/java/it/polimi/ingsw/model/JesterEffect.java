package it.polimi.ingsw.model;

public class JesterEffect implements Effect {
    Student[] students = new Student[6];

    public void applyEffect(Board board, int archiIndex, int numOfStudents, Color...studColors) {
        // TODO: implement effect
    }

    public void setStudents(Student[] students){
        this.students = students;
    }

}