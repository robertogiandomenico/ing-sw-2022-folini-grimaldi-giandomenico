package it.polimi.ingsw.view.utilities.lightclasses;

import it.polimi.ingsw.model.Archipelago;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.TowerColor;

import java.io.Serializable;
import java.util.EnumMap;

/**
 * This class is useful to contain the information needed to display the
 * archipelagos.
 * ({@link it.polimi.ingsw.view.cli.CLI CLI}/{@link it.polimi.ingsw.view.gui.GUI GUI})
 */
public class LightArchi implements Serializable {
    private int[][] islands;
    private boolean motherNature;
    private int noEntryTile;
    private TowerColor towerColor;
    private int size;

    /**
     * Class constructor.
     *
     * @param a            the Archipelago to simplify.
     * @param colorsIndex  the EnumMap that associates Colors and Integers.
     */
    LightArchi(Archipelago a, EnumMap<Color,Integer> colorsIndex){
        this.motherNature = a.isMNPresent();
        this.noEntryTile = a.getNoEntryTile();
        this.towerColor = a.getTowerColor();
        this.size = a.getIslands().size();
        this.islands = new int[a.getIslands().size()][5];

        for (int i = 0; i < a.getIslands().size(); i++)
            for(Color c : Color.values())
                islands[i][colorsIndex.get(c)] = a.getIslands().get(i).getStudentNumber(c);
    }


    /**
     * Returns the islands.
     *
     * @return             an Array representing the number of Students for
     *                     each Color for each Island.
     */
    public int[][] getIslands() {
        return islands;
    }

    /**
     * Returns a boolean that that states whether Mother Nature is on this archipelago
     * or not.
     *
     * @return             a boolean whose value is:
     *                     <p>
     *                     -{@code true} if Mother Nature is on this Archipelago;
     *                     </p> <p>
     *                     -{@code false} otherwise.
     *                     </p>
     */
    public boolean isMNPresent() {
        return motherNature;
    }

    /**
     * Returns a boolean that states whether a no entry tile is on this
     * archipelago or not.
     *
     * @return             a boolean whose value is:
     *                     <p>
     *                     -{@code true} if a No Entry tile is on this Archipelago;
     *                     </p> <p>
     *                     -{@code false} otherwise.
     *                     </p>
     */
    public int getNoEntryTiles() {
        return noEntryTile;
    }

    /**
     * Returns the color of the tower(s) on this archipelago.
     *
     * @return             the TowerColor on this Archipelago.
     */
    public TowerColor getTowerColor() {
        return towerColor;
    }

    /**
     * Returns the size of this archipelago.
     *
     * @return             the size of this Archipelago.
     */
    public int getSize() {
        return size;
    }

}
