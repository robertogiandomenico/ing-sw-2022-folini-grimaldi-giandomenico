package it.polimi.ingsw.model;

import java.io.Serializable;

/**
 * This class represents the student discs of the game.
 */
public class Student implements Serializable {
    private final Color color;

    /**
     * Class constructor specifying the color for this student.
     *
     * @param color            the Color of this Student.
     */
    public Student(Color color){
        this.color = color;
    }

    /**
     * Returns the color of this student.
     *
     * @return                 the Color of this Student.
     */
    public Color getColor(){
        return color;
    }

}
