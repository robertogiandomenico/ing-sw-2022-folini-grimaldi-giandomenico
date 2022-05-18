package it.polimi.ingsw.view.utilities.lightclasses;

import it.polimi.ingsw.model.SchoolBoard;
import it.polimi.ingsw.model.Student;

import java.io.Serializable;

/**
 * This class is useful to contain the information needed to display the
 * school boards. (CLI/GUI)
 */
public class LightSchoolBoard implements Serializable {
    private final LightPlayer player;
    private final Student[] entrance;
    private final boolean[] professorTable;
    private int towersLeft;
    private final int[] diningRoom;

    /**
     * Class constructor.
     *
     * @param sb           the SchoolBoard to simplify.
     */
    LightSchoolBoard(SchoolBoard sb){
        player = new LightPlayer(sb.getPlayer());
        entrance = sb.getEntrance();
        professorTable = sb.getProfessorTable();
        towersLeft = sb.getTowersLeft();
        diningRoom = sb.getDiningRoom();
    }


    /**
     * Returns the name of the player.
     *
     * @return             a String containing the nickname of the Player.
     */
    public LightPlayer getPlayer() {
        return player;
    }

    /**
     * Returns the entrance of this school board.
     *
     * @return             the Student Array representing the entrance.
     */
    public Student[] getEntrance() {
        return entrance;
    }

    /**
     * Returns the professor table.
     *
     * @return             the boolean Array representing the professor table.
     */
    public boolean[] getProfessorTable() {
        return professorTable;
    }

    /**
     * Returns the number of towers left in this school board.
     *
     * @return             the number of towers left.
     */
    public int getTowersLeft() {
        return towersLeft;
    }

    /**
     * Returns the dining room of this school board.
     *
     * @return             the Array representing the dining room.
     */
    public int[] getDiningRoom() {
        return diningRoom;
    }

}
