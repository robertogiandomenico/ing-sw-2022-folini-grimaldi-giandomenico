package it.polimi.ingsw.model;

import it.polimi.ingsw.model.mockClasses.MockBag;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * This class represents the game bag that contains the student discs.
 * Discs can be drawn from it and also put in there according to the game rules.
 * There is only one instance of this class for each game.
 */
public class Bag {
    private final List<Student> bagContent = new ArrayList<>();

    /**
     * Class constructor that initializes this bag with two students for each color.
     */
    public Bag(){
        for (int i = 0; i < 2; i++) {
            bagContent.add(new Student(Color.GREEN));
            bagContent.add(new Student(Color.RED));
            bagContent.add(new Student(Color.YELLOW));
            bagContent.add(new Student(Color.PINK));
            bagContent.add(new Student(Color.BLUE));
        }
        Collections.shuffle(this.bagContent);
    }

    /**
     * Draws a student from this bag.
     *
     * @return                 a Student randomly drawn from this Bag.
     */
    public Student draw(){
        return getSize()==0 ? null : bagContent.remove(getSize()-1);
    }

    /**
     * Puts the given student inside this bag.
     *
     * @param student          the Student added to the List that represents the bag
     *                         content.
     */
    public void put(Student student){
        bagContent.add(student);
        Collections.shuffle(this.bagContent);
    }

    /**
     * Returns the number of students inside this bag.
     *
     * @return                 the size of the bag's Student List.
     */
    public int getSize(){
        return bagContent.size();
    }

    /**
     * Returns the content of this bag. It is used for the {@link MockBag} class.
     *
     *
     * @return                 the bag's Student List.
     */
    public List<Student> getBagContent() {
        return bagContent;
    }
}