package it.polimi.ingsw.model;


import java.util.*;

public class Board {
    private final int TOTALCLOUDS;
    private final int CLOUDSIZE;
    private final int ENTRANCESIZE;
    private final int TOTALTOWERS;

    private final List<Archipelago> archipelagos;
    private Bag bag;
    private Cloud[] clouds;
    private final List<Player> players;
    private SchoolBoard[] playerBoards;

    private final EnumMap<Color, Integer> colorsIndex;

    private final boolean[] availableProfessors = new boolean[5];
    private int coinsSupply;
    private final GameCharacter[] selectedCharacters;


    public Board(List<Player> players, int TOTALCLOUDS, int CLOUDSIZE, int ENTRANCESIZE, int TOTALTOWERS, GameCharacter[] selectedCharacters){
        this.players = players;
        this.TOTALCLOUDS = TOTALCLOUDS;
        this.CLOUDSIZE = CLOUDSIZE;
        this.ENTRANCESIZE = ENTRANCESIZE;
        this.TOTALTOWERS = TOTALTOWERS;
        coinsSupply = 20 - players.size();
        this.selectedCharacters = selectedCharacters;
        archipelagos = new ArrayList<Archipelago>();

        //create and fill the map colorsIndex
        colorsIndex = new EnumMap<Color, Integer>(Color.class);
        mapSetup();

        //set all availableProfessors to true at the beginning
        Arrays.fill(availableProfessors, true);

        //place 2 students of each color into the bag and initialize the islands with mother nature and the students
        initializeBag();
        initializeIslands();

        //fill the bag with the remaining 120 students
        fillBag();

        //initialize the clouds array and fill the clouds with students from the bag
        initializeClouds();

        //initialize the playerBoards array
        initializeBoards();
    }

    public void fillClouds(){
        for(int i = 0; i < TOTALCLOUDS; i++) {
            clouds[i].fill(createStudentsArray(CLOUDSIZE));
        }
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

    private void mapSetup(){
        colorsIndex.put(Color.GREEN, 0);
        colorsIndex.put(Color.RED, 1);
        colorsIndex.put(Color.YELLOW, 2);
        colorsIndex.put(Color.PINK, 3);
        colorsIndex.put(Color.BLUE, 4);
    }

    private void initializeBag(){
        List<Student> bagContent = new ArrayList<Student>();
        for (int i = 0; i < 2; i++) {
            bagContent.add(new Student(Color.GREEN));
            bagContent.add(new Student(Color.RED));
            bagContent.add(new Student(Color.YELLOW));
            bagContent.add(new Student(Color.PINK));
            bagContent.add(new Student(Color.BLUE));
        }
        bag = new Bag(bagContent);
    }

    private void initializeIslands(){
        int n = new Random().nextInt(12);
        for (int i = 0; i < 12; i++) {
            if(i == n) {
                archipelagos.add(new Archipelago(null, true));
            } else if(i == (n + 6) % 12) {
                archipelagos.add(new Archipelago(null, false));
            } else {
                archipelagos.add(new Archipelago(bag.draw(), false));
            }
        }
    }

    private void fillBag(){
        for (int i = 0; i < 24; i++) {
            bag.put(new Student(Color.GREEN));
            bag.put(new Student(Color.RED));
            bag.put(new Student(Color.YELLOW));
            bag.put(new Student(Color.PINK));
            bag.put(new Student(Color.BLUE));
        }
    }

    private void initializeClouds(){
        clouds = new Cloud[TOTALCLOUDS];
        for(int i = 0; i < TOTALCLOUDS; i++) {
            clouds[i] = new Cloud(CLOUDSIZE);
        }
        fillClouds();
    }

    private void initializeBoards(){
        playerBoards = new SchoolBoard[players.size()];
        for (int i = 0; i < players.size(); i++) {
            playerBoards[i] = new SchoolBoard(players.get(i), createStudentsArray(ENTRANCESIZE), TOTALTOWERS);
        }
    }

    private Student[] createStudentsArray(int size){
        Student[] tmp = new Student[size];
        for (int i = 0; i < size; i++) {
            tmp[i] = bag.draw();
        }
        return tmp;
    }
}
