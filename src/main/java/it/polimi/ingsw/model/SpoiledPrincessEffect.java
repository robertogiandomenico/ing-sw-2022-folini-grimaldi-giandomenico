package it.polimi.ingsw.model;

public class SpoiledPrincessEffect implements Effect {
    Student[] students = new Student[4];

    public void applyEffect(Board board) {
        Student selectedStudent = selectStudent(board);

        board.moveFromCharacterToDiningRoom(selectedStudent);
    }

    public void setStudents(Student[] students) {
        this.students = students;
    }

    //TODO: controller must intervene to set this variable
    public Student selectStudent(Board board) {
        Student selectedStudent = null; //controller should ask the user for the input
        Student tmp = null;

        for(Student s : students) {
            if(s == selectedStudent){
                tmp = s;
                s = board.drawStudentsArray(1)[0];
                break;
            }
        }
        return tmp;
    }
}