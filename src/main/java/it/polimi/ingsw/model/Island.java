package it.polimi.ingsw.model;

import java.util.List;
import java.util.ArrayList;

/**
 * This class represents the islands on the board.
 * Each one has a list of present students.
 */
public class Island {
    private final List<Student> students;

    /**
     * Constructs the island specifying its first student.
     *
     * @param student          the first Student (if present) to be added.
     */
    public Island(Student student) {
        students = new ArrayList<>();
        students.add(student);
    }

    /**
     * Adds a student to this island.
     *
     * @param student          the Student to be added.
     */
    public void addStudent(Student student) {
        if(hasNoStudents()) students.set(0, student);
        else students.add(student);
    }

    /**
     * Returns a boolean that states whether the island is empty or not.
     *
     * @return                 a boolean whose value is:
     *                         <p>
     *                         -{@code true} if there is a Student in the 0 position of the Student List
     *                         (= there is at least a Student on this Island)
     *                         </p> <p>
     *                         -{@code false} if the 0 position of the Student List is {@code null}
     *                         (= there are no students on this Island).
     *                         </p>
     */
    public boolean hasNoStudents(){
        return students.get(0) == null;
    }

    /**
     * Counts the students of the given color on this island.
     *
     * @return                 the number of sought Students.
     */
    public int getStudentNumber(Color color) {
        int count = 0;
        if (hasNoStudents()) return 0;
        for (Student student : students) {
            if (student.getColor().equals(color)) count++;
        }
        return count;
    }

}