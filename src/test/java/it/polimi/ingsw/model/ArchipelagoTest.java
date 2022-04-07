package it.polimi.ingsw.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ArchipelagoTest {

    private Archipelago archi;
    boolean mnValue;

    @BeforeEach
    void setUp() {
        mnValue = false;
        archi = new Archipelago(new Student(Color.YELLOW), mnValue);
    }

    @Test
    void testAddIsland() {
        //only the first basic island is present
        assertEquals(1, archi.countIslands());

        archi.addIsland(new Island(null));
        assertEquals(2, archi.countIslands());

        archi.addIsland(new Island(new Student(Color.RED)));
        assertEquals(3, archi.countIslands());
    }


    @Test
    void testGetTotalStudents() {
        //only the student placed by the constructor is present
        for (Color c : Color.values()){
            if(c == Color.YELLOW){
                assertEquals(1, archi.getTotalStudents(c));
            } else {
                assertEquals(0, archi.getTotalStudents(c));
            }
        }

        archi.addIsland(new Island(null));
        for (Color c : Color.values()){
            if(c == Color.YELLOW){
                assertEquals(1, archi.getTotalStudents(c));
            } else {
                assertEquals(0, archi.getTotalStudents(c));
            }
        }

        archi.addIsland(new Island(new Student(Color.BLUE)));
        for (Color c : Color.values()){
            if(c == Color.YELLOW) {
                assertEquals(1, archi.getTotalStudents(c));
            } else if(c == Color.BLUE) {
                assertEquals(1, archi.getTotalStudents(c));
            } else {
                assertEquals(0, archi.getTotalStudents(c));
            }
        }

        Island isla = new Island(new Student(Color.BLUE));
        isla.addStudent(new Student(Color.YELLOW));
        archi.addIsland(isla);
        for (Color c : Color.values()){
            if(c == Color.YELLOW) {
                assertEquals(2, archi.getTotalStudents(c));
            } else if(c == Color.BLUE) {
                assertEquals(2, archi.getTotalStudents(c));
            } else {
                assertEquals(0, archi.getTotalStudents(c));
            }
        }
    }

}