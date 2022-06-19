package it.polimi.ingsw.model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This class tests the {@link it.polimi.ingsw.model.Bag Bag} methods.
 */
class BagTest {

    @Test
    void testDraw() {
        List<Student> out = new ArrayList<>();

        Bag bag = new Bag();
        refillBag(bag);
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

    private void refillBag(Bag bag) {
        while (bag.getSize() != 0){
            bag.draw();
        }
        for (int i = 0; i < 2; i++) {
            bag.put(new Student(Color.GREEN));
            bag.put(new Student(Color.RED));
            bag.put(new Student(Color.YELLOW));
            bag.put(new Student(Color.PINK));
            bag.put(new Student(Color.BLUE));
        }
    }

    @Test
    void testPut() {
        List<Student> in = new ArrayList<>(Arrays.asList(new Student(Color.BLUE), new Student(Color.YELLOW), new Student(Color.PINK),
                new Student(Color.GREEN), new Student(Color.RED)));
        List<Student> out = new ArrayList<>();

        Bag bag = new Bag();
        refillBag(bag);
        
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