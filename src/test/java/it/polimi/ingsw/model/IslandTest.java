package it.polimi.ingsw.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class IslandTest {

    private Island islandNull;
    private Island island;

    @BeforeEach
    void setUp() {
        islandNull = new Island(null);
        island = new Island(new Student(Color.GREEN));
    }

    @Test
    void testGetStudentNumber() {

        for (Color c : Color.values()){
            if(c == Color.GREEN){
                assertEquals(1, island.getStudentNumber(c));
            } else {
                assertEquals(0, island.getStudentNumber(c));
            }
        }

    }

    @Test
    void testAddStudent() {

        //add singular student
        island.addStudent(new Student(Color.GREEN));
        assertEquals(2, island.getStudentNumber(Color.GREEN));
        islandNull.addStudent(new Student(Color.GREEN));
        assertEquals(1, islandNull.getStudentNumber(Color.GREEN));

        //add students from a list
        List<Student> students = new ArrayList<>(Arrays.asList(new Student(Color.BLUE), new Student(Color.YELLOW), new Student(Color.RED) ));

        for (Student s : students) {
            island.addStudent(s);
        }
        assertEquals(1, island.getStudentNumber(Color.RED));
        assertEquals(1, island.getStudentNumber(Color.YELLOW));
        assertEquals(1, island.getStudentNumber(Color.BLUE));
        assertEquals(0, island.getStudentNumber(Color.PINK));
        assertEquals(2, island.getStudentNumber(Color.GREEN));

    }

}