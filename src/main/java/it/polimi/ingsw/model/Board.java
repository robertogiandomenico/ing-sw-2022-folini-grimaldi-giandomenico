package it.polimi.ingsw.model;


import it.polimi.ingsw.model.effects.*;


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

        //initialize characters with eventual students
        if (selectedCharacters != null) initializeCharacters();
    }

    public void fillClouds(){
        for(int i = 0; i < TOTALCLOUDS; i++) {
            clouds[i].fill(drawStudentsArray(CLOUDSIZE));
        }
    }

    public void moveFromCloudToEntrance(int selectedCloud){
        SchoolBoard currentPlayerSB = getCurrentPlayerSchoolBoard();
        Student[] newStudents = clouds[selectedCloud].get();

        for (Student newStudent : newStudents) {
            currentPlayerSB.addToEntrance(newStudent);
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
        if(!archipelago.isNoEntryTilePresent()){
            int topInfluence = 0;
            SchoolBoard topInfluencer = null;
            TowerColor currentTowerColor = archipelago.getTowerColor();

            for(SchoolBoard s : playerBoards){
                int currentInfluence = 0;
                for(Color c : Color.values()){
                    if (s.isProfessorPresent(mapToIndex(c))){
                        currentInfluence += archipelago.getTotalStudents(c);
                    }
                }
                if(s.getPlayer().getTowerColor() != null && s.getPlayer().getTowerColor() == currentTowerColor){
                    currentInfluence += archipelago.getIslands().size();
                }
                currentInfluence += s.getAdditionalInfluence();
                s.setAdditionalInfluence(0);
                if(currentInfluence > topInfluence) {
                    topInfluence = currentInfluence;
                    topInfluencer = s;
                }
            }

            if(topInfluencer != null && currentTowerColor == null){
                //CASE 1: Controlling an Island
                archipelago.setTowerColor(topInfluencer.getPlayer().getTowerColor());
                topInfluencer.removeTowers(archipelago.getIslands().size());
            } else if(topInfluencer != null && topInfluencer.getPlayer().getTowerColor() != currentTowerColor){
                //CASE 2: Conquering an Island, if the topInfluencer isn't the current owner of the archi, we replace the current owner's towers with theirs
                getPlayerSchoolBoardByTeam(currentTowerColor).addTowers(archipelago.getIslands().size());
                topInfluencer.removeTowers(archipelago.getIslands().size());
                archipelago.setTowerColor(topInfluencer.getPlayer().getTowerColor());
            }
            //We'll need to remember that if topInfluencer.getTowersLeft() == 0 -> topInfluencer wins the game

            Archipelago.resetForbiddenColor();
            checkMerge(archipelago);
        } else {
            for (GameCharacter c : selectedCharacters){
                if (c.getName().equals("GrannyGrass")){
                    GrannyGrassEffect effect = (GrannyGrassEffect) c.getEffect();
                    effect.putBackTile();
                }
            }
        }
    }

    private void checkMerge(Archipelago archipelago){
        int indexCurrentArchi = archipelagos.indexOf(archipelago);
        int indexRightArchi = (indexCurrentArchi + 1) % archipelagos.size();
        int indexLeftArchi = indexCurrentArchi != 0 ?  (indexCurrentArchi - 1) : archipelagos.size()-1;

        Archipelago leftArchi = archipelagos.get(indexLeftArchi);

        if(archipelago.getTowerColor() == archipelagos.get(indexRightArchi).getTowerColor()){
            mergeIslands(indexCurrentArchi, indexRightArchi);
        }

        //We need to recalculate the index of the current and left archis because there is a chance that the deletion of the rightArchi changed them,
        //this happens when initially currentArchi is the last archi of the list and rightArchi is the first one
        indexLeftArchi = archipelagos.indexOf(leftArchi);
        indexCurrentArchi = (indexLeftArchi + 1) % archipelagos.size();

        if(archipelagos.get(indexLeftArchi).getTowerColor() == archipelagos.get(indexCurrentArchi).getTowerColor()){
            mergeIslands(indexLeftArchi, indexCurrentArchi);
        }

        //We'll need to remember that if archipelagos.size() == 3 the game will end and the winner will be the player with min(towersLefts)
    }

    private void mergeIslands(int archi1, int archi2){
        List<Island> islands1 = archipelagos.get(archi1).getIslands();
        List<Island> islands2 = archipelagos.get(archi2).getIslands();

        islands1.addAll(islands2);

        islands2.clear();
        archipelagos.remove(archi2);
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

    private void initializeCharacters(){
        for (GameCharacter c : selectedCharacters){
            switch (c.getName()){
                case "Monk": {
                    MonkEffect effect = (MonkEffect) c.getEffect();
                    effect.setStudents(drawStudentsArray(4));
                    break;
                }
                case "Jester": {
                    JesterEffect effect = (JesterEffect) c.getEffect();
                    effect.setStudents(drawStudentsArray(6));
                    break;
                }
                case "SpoiledPrincess": {
                    SpoiledPrincessEffect effect = (SpoiledPrincessEffect) c.getEffect();
                    effect.setStudents(drawStudentsArray(4));
                    break;
                }
                default: break;
            }
        }
    }

    private Student[] drawStudentsArray(int size){
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

    public Cloud[] getClouds() {
        return clouds;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public int getCoinsSupply() {
        return coinsSupply;
    }

    public GameCharacter[] getSelectedCharacters() {
        return selectedCharacters;
    }

    public int getTOTALCLOUDS() {
        return TOTALCLOUDS;
    }

    public int getCLOUDSIZE() {
        return CLOUDSIZE;
    }

    public int getENTRANCESIZE() {
        return ENTRANCESIZE;
    }

    public int getTOTALTOWERS() {
        return TOTALTOWERS;
    }
}
