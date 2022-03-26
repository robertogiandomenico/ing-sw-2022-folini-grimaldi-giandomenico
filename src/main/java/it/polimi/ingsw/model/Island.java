package it.polimi.ingsw.model;

import java.util.List;
import java.util.ArrayList;

public class Island {
    private List<Student> students;

    public Island(List<Student> students) {
        this.students = new ArrayList<Student>(students);
    }

    public void addStudent(Student student) {
        students.add(student);
    }

    public int getStudentNumber(Color color) {
        int count=0;
        for(Student student : students){
            if(student.getColor().equals(color)) count++;
        }
        return count;
    }

}