package it.polimi.ingsw.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class CloudTest {

    private Cloud cloud;
    private Student[] students;

    @BeforeEach
    void setUp() {
        int CLOUDSIZE = 3;
        cloud = new Cloud(CLOUDSIZE);
        students = new Student[] {new Student(Color.BLUE), new Student(Color.YELLOW), new Student(Color.RED)};
    }

    @Test
    void testIsEmpty() {
        //test for an empty cloud
        assertTrue(cloud.isEmpty());

        cloud.fill(students);
        assertFalse(cloud.isEmpty());
    }

    @Test
    void TestGet() {
    }

    @Test
    void TestFill() {

        cloud.fill(students);

        Student[] out;
        out = cloud.get();

        for (Color c : Color.values()){
            if(Arrays.stream(students).anyMatch(x -> x.getColor() == c)) {
                assertEquals(1, Arrays.stream(out).filter(x -> x.getColor() == c).count());
            } else {
                assertEquals(0, Arrays.stream(out).filter(x -> x.getColor() == c).count());
            }
        }
    }
}