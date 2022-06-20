package it.polimi.ingsw.model;

import it.polimi.ingsw.model.mockClasses.MockBag;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This class tests the {@link it.polimi.ingsw.model.Bag Bag} methods using the {@link MockBag}.
 */
class BagTest {

    @Test
    void testDraw() {
        List<Student> out = new ArrayList<>();

        Bag bag = new MockBag();
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
        List<Student> out = new ArrayList<>();

        Bag bag = new MockBag();
        
        for (int i = 0; i < in.size(); i++) {
            bag.put(in.get(i));
            assertEquals(10 + i + 1, bag.getSize());
        }

        int bagSize = bag.getSize();
        for (int i = 0; i < bagSize; i++) {
            out.add(bag.draw());
        }

        for (Color c : Color.values()){
            assertEquals(3, out.stream().filter(x -> x.getColor() == c).count());
        }
    }
}