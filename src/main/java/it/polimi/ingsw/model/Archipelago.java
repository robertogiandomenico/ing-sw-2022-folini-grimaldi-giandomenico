package it.polimi.ingsw.model;

import java.util.List;
import java.util.ArrayList;

/**
 * This class represents the groups of islands that form during the game.
 * At first the archipelago is made up of only one island and with the evolution
 * of the game more of them can be added.
 * For each archipelago, the (potential) presence of Mother Nature, no entry
 * tiles and towers is specified.
 */
public class Archipelago {
    private final List<Island> islands;
    private boolean motherNature;
    private int noEntryTile;
    private TowerColor towerColor;
    private static Color forbiddenColor;

    /**
     * Constructs a new archipelago and initializes it with its first island.
     *
     * @param student          the first Student (if there is one) to be put on the
     *                         Island.
     * @param motherNature     a boolean that indicates whether Mother Nature is on
     *                         that Island or not.
     */
    public Archipelago(Student student, boolean motherNature) {
        islands = new ArrayList<>();
        createFirstIsland(student);
        this.motherNature = motherNature;
        noEntryTile = 0;
    }

    /**
     * Creates the first island of the archipelago with a student (if present) on it.
     *
     * @param student          the first Student (if there is one) put on this Island.
     */
    private void createFirstIsland(Student student){
        Island island = new Island(student);
        addIsland(island);
    }

    /**
     * Adds an island to the archipelago.
     *
     * @param island           the Island added to the List of Islands that compose
     *                         this Archipelago.
     */
    public void addIsland(Island island){
        islands.add(island);
    }

    /**
     * Returns the islands that compose the archipelago.
     *
     * @return                 the Island List.
     */
    public List<Island> getIslands() {
        return islands;
    }

    /**
     * Sets the variable that states whether Mother Nature is on this archipelago
     * or not.
     *
     * @param motherNature     a boolean whose value is:
     *                         <p>
     *                         -{@code true} if Mother Nature is on this Archipelago;
     *                         </p> <p>
     *                         -{@code false} if Mother Nature isn't on this Archipelago.
     *                         </p>
     */
    public void setMotherNature(boolean motherNature){
        this.motherNature = motherNature;
    }

    /**
     * Returns a boolean that that states whether Mother Nature is on this archipelago
     * or not.
     *
     * @return                 a boolean whose value is:
     *                         <p>
     *                         -{@code true} if Mother Nature is on this Archipelago;
     *                         </p> <p>
     *                         -{@code false} if Mother Nature isn't on this Archipelago.
     *                         </p>
     */
    public boolean isMNPresent(){
        return motherNature;
    }

    /**
     * Sets the variable that states the number of no entry tiles on this archipelago
     * This method conforms to the use of the
     * {@link it.polimi.ingsw.model.effects.GrannyGrassEffect GrannyGrassEffect}.
     *
     * @param noEntryTile      a boolean whose value is:
     *                         <p>
     *                         -{@code true} if a No Entry tile is on this Archipelago;
     *                         </p> <p>
     *                         -{@code false} if there isn't a No Entry tile on this Archipelago.
     *                         </p>
     */
    public void setNoEntryTile(int noEntryTile){
        this.noEntryTile = noEntryTile;
    }

    /**
     * Returns a boolean that states whether a no entry tile is on this
     * archipelago or not.
     * This method conforms to the use of the
     * {@link it.polimi.ingsw.model.effects.GrannyGrassEffect GrannyGrassEffect}.
     *
     * @return                 a boolean whose value is:
     *                         <p>
     *                         -{@code true} if a No Entry tile is on this Archipelago;
     *                         </p> <p>
     *                         -{@code false} if there isn't a No Entry tile on this Archipelago.
     *                         </p>
     */
    public boolean isNoEntryTilePresent(){
        return noEntryTile>0;
    }

    /**
     * Returns a value that states how many no entry tiles are on this
     * archipelago.
     * This method conforms to the use of the
     * {@link it.polimi.ingsw.model.effects.GrannyGrassEffect GrannyGrassEffect}.
     *
     * @return                 the number of no entry tiles on this archipelago
     */
    public int getNoEntryTile() {
        return noEntryTile;
    }

    /**
     * Sets the tower color based on which player conquers this archipelago.
     *
     * @param towerColor       the TowerColor that matches that of the conqueror
     *                         Player.
     */
    public void setTowerColor(TowerColor towerColor){
        this.towerColor = towerColor;
    }

    /**
     * Returns the color of the tower(s) on this archipelago.
     *
     * @return                 the TowerColor on this Archipelago.
     */
    public TowerColor getTowerColor(){
        return towerColor;
    }

    /**
     * Counts the students of the given color on this archipelago.
     *
     * @param color            the Color of the Students to count.
     * @return                 the actual number of Students, in particular it returns
     *                         0 if there are no Students of the given Color or
     *                         if the Color is forbidden
     *                         ({@link it.polimi.ingsw.model.effects.MushroomManEffect
     *                         MushroomManEffect}).
     */
    public int getTotalStudents(Color color){
        if(color == forbiddenColor){
            return 0;
        }
        int count=0;
        for(Island island : islands) {
            if (!island.hasNoStudents()) count+=island.getStudentNumber(color);
        }
        return count;
    }

    /**
     * Sets the given color as forbidden.
     * Means that students of this color won't be counted as present on this
     * archipelago. Used when the {@link it.polimi.ingsw.model.effects.MushroomManEffect
     * MushroomManEffect} is played.
     *
     * @param forbiddenColor   the Color that has to be set as forbidden.
     */
    public static void setForbiddenColor(Color forbiddenColor) {
        Archipelago.forbiddenColor = forbiddenColor;
    }

    /**
     * Restores to the condition where there are no forbidden colors.
     * Used when the {@link it.polimi.ingsw.model.effects.MushroomManEffect
     * MushroomManEffect} is over.
     */
    public static void resetForbiddenColor() {
        forbiddenColor = null;
    }

}