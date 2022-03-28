package it.polimi.ingsw.model;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;

public class Board {
    private final int TOTALCLOUDS;
    private final int CLOUDSIZE;
    private final int ENTRANCESIZE;
    private final int TOTALTOWERS;

    private List<Archipelago> Archipelagos;
    private Bag bag;
    private Cloud[] clouds;
    private SchoolBoard[] playerBoards;

    private EnumMap<Color, Integer> colorsIndex;

    private boolean[] availableProfessors;
    private List<Student> intialStudents;
    private int coinsSupply;
    private Character[] selectedCharacters;



    public Board(int TOTALCLOUDS, int CLOUDSIZE, int ENTRANCESIZE, int TOTALTOWERS){
        this.TOTALCLOUDS = TOTALCLOUDS;
        this.CLOUDSIZE = CLOUDSIZE;
        this.ENTRANCESIZE = ENTRANCESIZE;
        this.TOTALTOWERS = TOTALTOWERS;

        //fill the map colorsIndex
        colorsIndex = new EnumMap<>(Color.class);
            colorsIndex.put(Color.GREEN, 0);
            colorsIndex.put(Color.RED, 1);
            colorsIndex.put(Color.YELLOW, 2);
            colorsIndex.put(Color.PINK, 3);
            colorsIndex.put(Color.BLUE, 4);

        //set all availableProfessors to true at the beginning
        Arrays.fill(availableProfessors, true);

        //TODO: complete ALL the Board constructor, even the arguments taken by the method are INCOMPLETE

    }

    public void fillClouds(){
        //TODO: implement fillClouds method
    }

    public void moveFromCloudToEntrance(){
        //TODO: implement moveFromCloudToEntrance method
    }

    public void moveFromEntranceToIsland(){
        //TODO: implement moveFromEntranceToIsland method
    }

    public void moveFromEntranceToDiningRoom(){
        //TODO: implement moveFromEntranceToDiningRoom method
    }

    public void moveMotherNature(){
        //TODO: implement moveMotherNature method
    }

    public void calculateInfluence(){
        //TODO: implement calculateInfluence method
    }

    private void mergeIslands(int island1, int island2){
        //TODO: implement mergeIslands method
    }

}
