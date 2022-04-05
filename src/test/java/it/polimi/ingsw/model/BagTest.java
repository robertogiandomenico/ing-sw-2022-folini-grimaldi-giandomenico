package it.polimi.ingsw.model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BagTest {

    @Test
    void testDraw() {
        List<Student> out = new ArrayList<>();

        Bag bag = new Bag();
        assertEquals(10, bag.getSize());

        int bagSize = bag.getSize();
        for (int i = 0; i < bagSize; i++) {
            out.add(bag.draw());
            assertEquals(10 - i - 1, bag.getSize());
        }

        for (Color c : Color.values()) {
            assertEquals(2, out.stream().filter(x -> x.getColor() == c).count());
        }

        assertNull(bag.draw());

    }

    @Test
    void testPut() {
        List<Student> in = new ArrayList<>(Arrays.asList(new Student(Color.BLUE), new Student(Color.YELLOW), new Student(Color.PINK),
                new Student(Color.GREEN), new Student(Color.RED)));
    }
}