package it.polimi.ingsw.model.mockClasses;

import it.polimi.ingsw.model.Bag;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Student;

import java.util.List;


/**
 * This class represents a modification of game bag that contains the student discs in a specific order.
 * All the other functionalities of the normal bag still remains but this class doesn't shuffle the students.
 */
public class MockBag extends Bag {

    /**
     * Class constructor that initializes this bag with two students for each color.
     */
    public MockBag() {
        //Because of the implicit super() call
        List<Student> bc = getBagContent();
        while(!bc.isEmpty()){
            draw();
        }

        for (int i = 0; i < 2; i++) {
            getBagContent().add(new Student(Color.GREEN));
            getBagContent().add(new Student(Color.RED));
            getBagContent().add(new Student(Color.YELLOW));
            getBagContent().add(new Student(Color.PINK));
            getBagContent().add(new Student(Color.BLUE));
        }
    }

    /**
     * Puts the given student inside this bag.
     *
     * @param student          the Student added to the List that represents the bag
     *                         content.
     */
    @Override
    public void put(Student student){
        getBagContent().add(student);
    }
}
