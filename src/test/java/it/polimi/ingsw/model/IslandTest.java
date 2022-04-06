package it.polimi.ingsw.model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class IslandTest {

    //this is a test for both methods, addStudent() and getStudentNumber()
    @Test
    void testAddStudent() {

        //absolute first student is placed
        Island island = new Island(new Student(Color.RED));
        assertEquals(1, island.getStudentNumber(Color.RED));
        assertEquals(0, island.getStudentNumber(Color.YELLOW));
        assertEquals(0, island.getStudentNumber(Color.BLUE));
        assertEquals(0, island.getStudentNumber(Color.PINK));
        assertEquals(0, island.getStudentNumber(Color.GREEN));

        //add singular student
        island.addStudent(new Student(Color.RED));
        assertEquals(2, island.getStudentNumber(Color.RED));

        //add students from a list
        List<Student> students = new ArrayList<>(Arrays.asList(new Student(Color.BLUE), new Student(Color.YELLOW), new Student(Color.RED) ));

        for (Student s : students) {
            island.addStudent(s);
        }
        assertEquals(3, island.getStudentNumber(Color.RED));
        assertEquals(1, island.getStudentNumber(Color.YELLOW));
        assertEquals(1, island.getStudentNumber(Color.BLUE));
        assertEquals(0, island.getStudentNumber(Color.PINK));
        assertEquals(0, island.getStudentNumber(Color.GREEN));
    }
}