package it.polimi.ingsw.model;

import java.util.List;
import java.util.ArrayList;

public class Island {
    private final List<Student> students;

    public Island(Student student) {
        students = new ArrayList<>();
        students.add(student);
    }

    public void addStudent(Student student) {
        if(students.get(0) == null) students.set(0, student);
        else students.add(student);
    }

    public int getStudentNumber(Color color) {
        try {
            int count = 0;
            for (Student student : students) {
                if (student.getColor().equals(color)) count++;
            }
            return count;
        } catch (NullPointerException e){
            return 0;
        }
    }

}