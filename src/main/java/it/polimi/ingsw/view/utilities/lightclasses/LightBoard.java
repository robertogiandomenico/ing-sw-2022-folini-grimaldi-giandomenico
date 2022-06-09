package it.polimi.ingsw.view.utilities.lightclasses;

import it.polimi.ingsw.model.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;

/**
 * This class is useful to contain the information needed to display the
 * board.
 * ({@link it.polimi.ingsw.view.cli.CLI CLI}/{@link it.polimi.ingsw.view.gui.GUI GUI})
 */
public class LightBoard implements Serializable {
    private List<LightArchi> archipelagos;
    private Student[][] clouds;
    private List<LightPlayer> players;
    private List<LightSchoolBoard> schoolBoards;
    private int coinsSupply;
    private LightCharacter[] selectedCharacters;
    private LightSchoolBoard currentPlayerSB;

    /**
     * Class constructor.
     *
     * @param b            the Board to simplify.
     */
    public LightBoard(Board b){
        initializeLightPlayers(b.getPlayers());
        initializeLightArchipelagos(b.getArchipelagos(), b.getColorsIndex());
        initializeLightSchoolBoards(b.getPlayerBoards());
        if(b.getSelectedCharacters() != null) {
            initializeLightCharacters(b.getSelectedCharacters());
        } else {
            selectedCharacters = null;
        }
        if(b.getCurrentPlayerSchoolBoard() != null) {
            currentPlayerSB = new LightSchoolBoard(b.getCurrentPlayerSchoolBoard());
        } else {
            currentPlayerSB = null;
        }
        coinsSupply = b.getCoinsSupply();
        initializeLightClouds(b.getClouds());
    }


    /**
     * Initializes a light version of the clouds.
     * Only the cloud content is saved
     * -> clouds are now treated as Student matrices.
     *
     * @param clouds       the Clouds on the Board.
     */
    private void initializeLightClouds(Cloud[] clouds){
        this.clouds = new Student[clouds.length][clouds[0].getCloudContent().length];
        for (int i = 0; i<clouds.length; i++)
            System.arraycopy(clouds[i].getCloudContent(), 0, this.clouds[i], 0, clouds[i].getCloudContent().length);
    }

    /**
     * Initializes a light version of the players.
     * @see LightPlayer
     *
     * @param players      the List of Players for this Game.
     */
    private void initializeLightPlayers(List<Player> players){
        this.players = new ArrayList<>();
        for (Player p : players)
            this.players.add(new LightPlayer(p));
    }

    /**
     * Initializes a light version of the school boards.
     * @see LightSchoolBoard
     *
     * @param schoolBoards the SchoolBoard Array for this Game.
     */
    private void initializeLightSchoolBoards(SchoolBoard[] schoolBoards){
        this.schoolBoards = new ArrayList<>();
        for (SchoolBoard sb : schoolBoards)
            this.schoolBoards.add(new LightSchoolBoard(sb));
    }

    /**
     * Initializes a light version of the archipelagos.
     * @see LightArchi
     *
     * @param archis       the Archipelago List of the Board.
     * @param colorsIndex  the EnumMap associating Colors and Integers.
     */
    private void initializeLightArchipelagos(List<Archipelago> archis, EnumMap<Color, Integer> colorsIndex){
        this.archipelagos = new ArrayList<>();
        for (Archipelago a : archis)
            archipelagos.add(new LightArchi(a, colorsIndex));
    }

    /**
     * Initializes a light version of the characters.
     * @see LightCharacter
     *
     * @param characters   the GameCharacter List of this Game.
     */
    private void initializeLightCharacters(GameCharacter[] characters){
        this.selectedCharacters = new LightCharacter[characters.length];
        for (int i = 0; i< characters.length; i++)
            selectedCharacters[i] = new LightCharacter(characters[i]);
    }

    /**
     * Returns the characters.
     *
     * @return             a LightCharacter List.
     */
    public LightCharacter[] getSelectedCharacters() {
        return selectedCharacters;
    }

    /**
     * Returns the archipelagos.
     *
     * @return             a LightArchi List.
     */
    public List<LightArchi> getArchipelagos(){
        return archipelagos;
    }

    /**
     * Returns the school boards.
     *
     * @return             a LightSchoolBoard List.
     */
    public List<LightSchoolBoard> getSchoolBoards(){
        return schoolBoards;
    }

    /**
     * Returns the school board of the current player.
     *
     * @return             a LightSchoolBoard.
     */
    public LightSchoolBoard getCurrentPlayerSchoolBoard() {
        return currentPlayerSB;
    }

    /**
     * Returns the players.
     *
     * @return             a LightPlayer List.
     */
    public List<LightPlayer> getPlayers(){
        return players;
    }

    /**
     * Returns the students on the given cloud.
     *
     * @param index        the index of the Cloud.
     * @return             a Student array.
     */
    public Student[] getCloud(int index){
        return clouds[index];
    }

    /**
     * Returns the number of clouds on the board.
     *
     * @return             the number of Clouds.
     */
    public int getCloudsNumber() {
        return clouds.length;
    }

    /**
     * Returns the coins supply.
     *
     * @return             the number of coins in the supply.
     */
    public int getCoinsSupply() {
        return coinsSupply;
    }

    /**
     * Returns the game mode.
     *
     * @return             a boolean whose value is:
     *                     <p>
     *                     -{@code true} if the mode is expert;
     *                     </p> <p>
     *                     -{@code false} otherwise.
     *                     </p>
     */
    public boolean isExpertMode() {
        return selectedCharacters != null;
    }

}
