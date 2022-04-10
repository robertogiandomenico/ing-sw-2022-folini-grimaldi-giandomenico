package it.polimi.ingsw.model;

public class MonkEffect implements Effect {
    Student[] students = new Student[4];

    public void applyEffect(Board board) {
        Student selectedStudent = selectStudent(board);

        int archiIndex = setArchiIndex();
        Archipelago selectedArchipelago = board.getArchipelago(archiIndex);

        board.moveFromCharacterToArchipelago(selectedStudent, selectedArchipelago);
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

    //TODO: controller must intervene to set this variable
    public int setArchiIndex() {
        int index = 0; //controller should ask the user for the input
        return index;
    }

}