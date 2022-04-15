package it.polimi.ingsw.model;


import java.util.*;

public class Board {
    private final int TOTALCLOUDS;
    private final int CLOUDSIZE;
    private final int ENTRANCESIZE;
    private final int TOTALTOWERS;

    private final List<Archipelago> archipelagos;
    private final Bag bag;
    private Cloud[] clouds;
    private final List<Player> players;
    private SchoolBoard[] playerBoards;

    private final EnumMap<Color, Integer> colorsIndex;

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
        archipelagos = new ArrayList<>();

        //create and fill the map colorsIndex
        colorsIndex = new EnumMap<>(Color.class);
        mapSetup();

        bag = new Bag();

        //initialize the islands with mother nature and the students
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
            clouds[i].fill(drawStudentsArray(CLOUDSIZE));
        }
    }

    public void moveFromCloudToEntrance(int selectedCloud){
        SchoolBoard currentPlayerSB = getCurrentPlayerSchoolBoard();
        Student[] newStudents = clouds[selectedCloud].get();

        for (int i = 0; i < newStudents.length; i++) {
            currentPlayerSB.addToEntrance(newStudents[i]);
        }
    }

    public void moveFromEntranceToArchipelago(Student student, int archiIndex){
        SchoolBoard currentPlayerSB = getCurrentPlayerSchoolBoard();
        Archipelago selectedArchipelago = archipelagos.get(archiIndex);

        currentPlayerSB.removeFromEntrance(student.getColor());
        selectedArchipelago.getIslands().get(0).addStudent(student);
    }

    public void moveFromEntranceToDiningRoom(Student student){
        SchoolBoard currentPlayerSB = getCurrentPlayerSchoolBoard();
        int indexDR = mapToIndex(student.getColor());

        currentPlayerSB.removeFromEntrance(student.getColor());
        currentPlayerSB.addToDiningRoom(indexDR);
    }

    public void moveMotherNature(int mnSteps){
        int archiIndex = 0; //needed to know the index of the starting archipelago
        int nextArchiIndex;

        for(Archipelago archi : archipelagos) {
            if (archi.isMNPresent()) {
                archi.setMotherNature(false);

                nextArchiIndex = (archiIndex + mnSteps) % archipelagos.size();
                archipelagos.get(nextArchiIndex).setMotherNature(true);
                calculateInfluence(archipelagos.get(nextArchiIndex));
                break;
            }

            archiIndex++;
        }
    }

    public void calculateInfluence(Archipelago archipelago){
        //TODO: implement calculateInfluence method
    }

    private void mergeIslands(int archi1, int archi2){
        List<Island> islands1 = archipelagos.get(archi1).getIslands();
        List<Island> islands2 = archipelagos.get(archi2).getIslands();

        islands1.addAll(islands2);

        islands2.removeAll(islands2);
        archipelagos.remove(getArchipelago(archi2));
    }

    private void mapSetup(){
        colorsIndex.put(Color.GREEN, 0);
        colorsIndex.put(Color.RED, 1);
        colorsIndex.put(Color.YELLOW, 2);
        colorsIndex.put(Color.PINK, 3);
        colorsIndex.put(Color.BLUE, 4);
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
            playerBoards[i] = new SchoolBoard(players.get(i), drawStudentsArray(ENTRANCESIZE), TOTALTOWERS);
        }

        for(SchoolBoard x : playerBoards) {
            for(SchoolBoard y : playerBoards) {
                if(x != y) {
                    x.addOtherBoard(y);
                }
            }
        }
    }

    public Student[] drawStudentsArray(int size){
        Student[] tmp = new Student[size];
        for (int i = 0; i < size; i++) {
            tmp[i] = bag.draw();
        }
        return tmp;
    }

    public Archipelago getArchipelago(int archiIndex) {
        return archipelagos.get(archiIndex);
    }

    public SchoolBoard getCurrentPlayerSchoolBoard() {
        SchoolBoard currentPlayerSchoolBoard = null;
        for(SchoolBoard s : playerBoards){
            if(s.getPlayer().getCanMoveStudents()) {
                currentPlayerSchoolBoard = s;
            }
        }

        //short form: return Arrays.stream(playerBoards).filter(s -> s.getPlayer().getCanMoveStudents()).findFirst().orElse(null);
        return currentPlayerSchoolBoard;
    }

    public SchoolBoard getPlayerSchoolBoardByTeam(TowerColor towerColor) {
        SchoolBoard playerSchoolBoard = null;
        for(SchoolBoard s : playerBoards){
            if(s.getPlayer().getTowerColor() == towerColor) {
                playerSchoolBoard = s;
            }
        }
        //short form: return Arrays.stream(playerBoards).filter(s -> s.getPlayer().getPlayerTeam() == towerColor).findFirst().orElse(null);
        return playerSchoolBoard;
    }

    public Bag getBag() {
        return bag;
    }

    public SchoolBoard[] getPlayerBoards() {
        return playerBoards;
    }

    public int mapToIndex(Color color){
        return colorsIndex.get(color);
    }

}
