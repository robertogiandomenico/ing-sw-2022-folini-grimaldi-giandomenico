package it.polimi.ingsw.view.utilities;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.network.messages.MessageType;
import it.polimi.ingsw.network.messages.serverMessages.GenericServerMessage;

import java.util.EnumMap;
import java.util.List;

/**
 * This class is useful to contain the information needed to display the
 * board. (CLI/GUI)
 */
public class LightBoard {
    private List<LightArchi> archipelagos;
    private Student[][] clouds;
    private List<LightPlayer> players;
    private List<LightSchoolBoard> playerBoards;
    private int coinsSupply;

    /**
     * Class constructor.
     *
     * @param b      the Board to simplify.
     */
    LightBoard(Board b){
        initializeLightClouds(b.getClouds());
        initializeLightPlayers(b.getPlayers());
        initializeLightArchipelagos(b.getArchipelagos(), b.getColorsIndex());
        initializeLightSchoolBoards(b.getPlayerBoards());
        this.coinsSupply = b.getCoinsSupply();
    }

    /**
     * Initializes a light version of the clouds.
     * Only the cloud content is saved
     * -> clouds are now treated as Student matrices.
     *
     * @param clouds the Clouds on the Board.
     */
    private void initializeLightClouds(Cloud[] clouds){
        int index = 0;

        for (Cloud c : clouds){
            System.arraycopy(c.getCloudContent(), 0, this.clouds[index], 0, c.getCloudContent().length);
            index++;
        }

    }

    /**
     * Initializes a light version of the players.
     * {@see LightPlayer}
     *
     * @param players the List of Players for this Game.
     */
    private void initializeLightPlayers(List<Player> players){
        for (Player p : players){
            this.players.add(new LightPlayer(p));
        }
    }

    /**
     * Initializes a light version of the school boards.
     * {@see LightSchoolBoard}
     *
     * @param schoolBoards the SchoolBoard Array for this Game.
     */
    private void initializeLightSchoolBoards(SchoolBoard[] schoolBoards){
        for (SchoolBoard sb : schoolBoards){
            this.playerBoards.add(new LightSchoolBoard(sb));
        }
    }

    /**
     * Initializes a light version of the archipelagos.
     * {@see LightArchis}
     *
     * @param archis      the Archipelago List of the Board.
     * @param colorsIndex the EnumMap associating Colors and Integers.
     */
    private void initializeLightArchipelagos(List<Archipelago> archis, EnumMap<Color, Integer> colorsIndex){
        for (Archipelago a : archis){
            this.archipelagos.add(new LightArchi(a, colorsIndex));
        }
    }


    /**
     * Returns the archipelagos.
     *
     * @return       a LightArchi List.
     */
    public List<LightArchi> getArchipelagos(){
        return archipelagos;
    }

    /**
     * Returns the school boards.
     *
     * @return       a LightSchoolBoard List.
     */
    public List<LightSchoolBoard> getSchoolBoards(){
        return playerBoards;
    }

    /**
     * Returns the players.
     *
     * @return       a LightPlayer List.
     */
    public List<LightPlayer> getPlayers(){
        return players;
    }

    /**
     * Returns the students on the given cloud.
     *
     * @param index  the index of the Cloud.
     * @return       a Student array.
     */
    public Student[] getCloud(int index){
        return clouds[index];
    }

}
