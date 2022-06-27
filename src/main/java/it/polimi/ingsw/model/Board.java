package it.polimi.ingsw.model;

import it.polimi.ingsw.model.effects.*;
import it.polimi.ingsw.view.utilities.lightclasses.LightBoard;


import java.util.*;

/**
 * This class represents the game board.
 * The board includes 12 islands and 2 or 3 school boards and clouds. If expert
 * mode, it also includes coins and selected characters.
 * There is only one instance of this class for each game.
 */
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
    private int coinsSupply;
    private final GameCharacter[] selectedCharacters;


    /**
     * Class constructor that initializes the board using the parameters to correctly
     * set school boards, towers, clouds and characters.
     *
     * @param players               the Player List.
     * @param TOTALCLOUDS           the number of Clouds.
     * @param CLOUDSIZE             the number of Students for each Cloud.
     * @param ENTRANCESIZE          the number of Students for each school entrance.
     * @param TOTALTOWERS           the number of towers for each Player.
     * @param selectedCharacters    the List of GameCharacters drawn for this game.
     */
    public Board(List<Player> players, int TOTALCLOUDS, int CLOUDSIZE, int ENTRANCESIZE, int TOTALTOWERS, GameCharacter[] selectedCharacters) {
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
    }

    /**
     * Sets all the board content (islands, bag, clouds, school boards, characters).
     */
    public void initBoard(){
        //initialize the islands with mother nature and the students
        initializeIslands(new Random().nextInt(12));

        //fill the bag with the remaining 120 students
        fillBag();

        //initialize the clouds array and fill the clouds with students from the bag
        initializeClouds();

        //initialize the playerBoards array
        initializeBoards();

        //initialize characters with eventual students
        if (selectedCharacters != null) initializeCharacters();
    }

    /**
     * Puts students on the clouds.
     */
    private void fillClouds() {
        for(int i = 0; i < TOTALCLOUDS; i++) {
            clouds[i].fill(drawStudentsArray(CLOUDSIZE));
        }
    }

    /**
     * Moves students from a cloud to the player's school entrance.
     *
     * @param selectedCloud         the Cloud from which Students are taken.
     */
    public void moveFromCloudToEntrance(int selectedCloud) {
        SchoolBoard currentPlayerSB = getCurrentPlayerSchoolBoard();
        Student[] newStudents = clouds[selectedCloud].get();

        for (Student newStudent : newStudents) {
            currentPlayerSB.addToEntrance(newStudent);
        }
    }

    /**
     * Moves a student from the player's entrance to the given archipelago.
     *
     * @param student               the Student that has to be moved.
     * @param archiIndex            the selected Archipelago.
     */
    public void moveFromEntranceToArchipelago(Student student, int archiIndex) {
        SchoolBoard currentPlayerSB = getCurrentPlayerSchoolBoard();
        Archipelago selectedArchipelago = archipelagos.get(archiIndex);

        currentPlayerSB.removeFromEntrance(student.getColor());
        selectedArchipelago.getIslands().get(0).addStudent(student);
    }

    /**
     * Moves a student from the player's entrance to the corresponding dining room.
     *
     * @param student               the Student that has to be moved.
     */
    public void moveFromEntranceToDiningRoom(Student student) {
        SchoolBoard currentPlayerSB = getCurrentPlayerSchoolBoard();
        int indexDR = mapToIndex(student.getColor());

        currentPlayerSB.removeFromEntrance(student.getColor());

        // if(currentPlayerSB.getDiningRoom()[indexDR]<10) then do lines 125-134
        currentPlayerSB.addToDiningRoom(indexDR);

        if (selectedCharacters != null) {
            //this means that expert mode was chosen
            updatePlayerCoins(currentPlayerSB, indexDR);
        }
    }

    /**
     * Updates player's coins whenever they position a student in a dining room
     * space that allows them to get an additional coin, according to game rules
     * (only in Expert Mode).
     *
     * @param currSB                the SchoolBoard of the current Player.
     * @param indexDR               the index of the dining room.
     */
    public void updatePlayerCoins(SchoolBoard currSB, int indexDR){
        boolean canTake = currSB.checkCoinsPath(indexDR, currSB.getDiningRoom()[indexDR]);
        if (canTake && coinsSupply > 0) {
            currSB.getPlayer().addCoin();
            removeFromCoinsSupply();
        }
    }

    /**
     * Removes a coin from the coins supply.
     */
    public void removeFromCoinsSupply() {
        coinsSupply--;
    }

    /**
     * Moves Mother Nature according to the selected number of steps and then
     * calculates the influence.
     *
     * @param mnSteps               the number of steps Mother Nature has to take.
     */
    public void moveMotherNature(int mnSteps) {
        int archiIndex = 0; //necessary to know the index of the starting archipelago
        int nextArchiIndex;

        for (Archipelago archi : archipelagos) {
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

    /**
     * Calculates the influence for the given archipelago.
     * Adds/replaces towers if necessary.
     * {@link it.polimi.ingsw.model.effects.GrannyGrassEffect Granny Grass} and
     * {@link it.polimi.ingsw.model.effects.MushroomManEffect Mushroom Man}
     * effects are considered.
     *
     * @param archipelago           the Archipelago where influence is calculated.
     */
    public void calculateInfluence(Archipelago archipelago) {
        if (!archipelago.getIslands().get(0).hasNoStudents()) {
            if (!archipelago.isNoEntryTilePresent()) {
                int topInfluence = 0;
                SchoolBoard topInfluencer = null;
                TowerColor currentTowerColor = archipelago.getTowerColor();

                for (SchoolBoard s : playerBoards) {
                    int currentInfluence = 0;
                    for (Color c : Color.values()) {
                        if (s.isProfessorPresent(mapToIndex(c))) {
                            currentInfluence += archipelago.getTotalStudents(c);
                        }
                    }
                    if (s.getPlayer().getTowerColor() != null && s.getPlayer().getTowerColor() == currentTowerColor) {
                        currentInfluence += archipelago.getIslands().size();
                    }
                    currentInfluence += s.getAdditionalInfluence();
                    s.setAdditionalInfluence(0);
                    if (currentInfluence > topInfluence || (currentInfluence == topInfluence && currentTowerColor == s.getPlayer().getTowerColor())) {
                        topInfluence = currentInfluence;
                        topInfluencer = s;
                    } else if(currentInfluence == topInfluence && currentTowerColor == null) {
                        topInfluencer = null;
                    }
                }

                if (topInfluencer != null && currentTowerColor == null) {
                    //CASE 1: Controlling an Island
                    archipelago.setTowerColor(topInfluencer.getPlayer().getTowerColor());
                    topInfluencer.removeTowers(archipelago.getIslands().size());
                } else if (topInfluencer != null && topInfluencer.getPlayer().getTowerColor() != currentTowerColor) {
                    //CASE 2: Conquering an Island, if the topInfluencer isn't the current owner of the archi, we replace the current owner's towers with theirs
                    getPlayerSchoolBoardByTeam(currentTowerColor).addTowers(archipelago.getIslands().size());
                    topInfluencer.removeTowers(archipelago.getIslands().size());
                    archipelago.setTowerColor(topInfluencer.getPlayer().getTowerColor());
                }

                Archipelago.resetForbiddenColor();
                if (archipelago.getTowerColor() != null) checkMerge(archipelago);
            } else {
                archipelago.setNoEntryTile(archipelago.getNoEntryTile() - 1);
                for (GameCharacter c : selectedCharacters) {
                    if (c.getName().equals("GrannyGrass")) {
                        GrannyGrassEffect effect = (GrannyGrassEffect) c.getEffect();
                        effect.putBackTile();
                    }
                }
            }
        }
    }

    /**
     * Checks if adjacent archipelagos can be merged into the given one.
     *
     * @param archipelago           the selected Archipelago.
     */
    private void checkMerge(Archipelago archipelago) {
        //If one of the adjacent archis can be merged we'll merge THAT archi into archipelago, so that we can preserve the reference to the
        //current archi on which we calculated the influence (archipelago)
        int indexCurrentArchi = archipelagos.indexOf(archipelago);
        int indexRightArchi = (indexCurrentArchi + 1) % archipelagos.size();
        int indexLeftArchi = indexCurrentArchi != 0 ? (indexCurrentArchi - 1) : archipelagos.size() - 1;

        Archipelago leftArchi = archipelagos.get(indexLeftArchi);

        if (archipelago.getTowerColor() == archipelagos.get(indexRightArchi).getTowerColor()) {
            mergeIslands(indexCurrentArchi, indexRightArchi);
        }

        //We need to recalculate the index of the current and left archis because there is a chance that the deletion of the rightArchi changed them,
        //this happens when initially currentArchi is the last archi of the list and rightArchi is the first one
        indexLeftArchi = archipelagos.indexOf(leftArchi);
        indexCurrentArchi = (indexLeftArchi + 1) % archipelagos.size();

        if (archipelagos.get(indexLeftArchi).getTowerColor() == archipelagos.get(indexCurrentArchi).getTowerColor()) {
            mergeIslands(indexCurrentArchi, indexLeftArchi);
        }

        //We'll need to remember that if archipelagos.size() == 3 the game will end and the winner will be the player with min(towersLefts)
    }

    /**
     * Merges islands from the second archipelago into the first one.
     *
     * @param archi1                the first Archipelago.
     * @param archi2                the second Archipelago.
     */
    private void mergeIslands(int archi1, int archi2) {
        List<Island> islands1 = archipelagos.get(archi1).getIslands();
        List<Island> islands2 = archipelagos.get(archi2).getIslands();

        islands1.addAll(islands2);
        if (archipelagos.get(archi2).isNoEntryTilePresent()) {
            archipelagos.get(archi1).setNoEntryTile(archipelagos.get(archi1).getNoEntryTile() + archipelagos.get(archi2).getNoEntryTile());
        }

        islands2.clear();
        archipelagos.remove(archi2);
    }

    /**
     * Maps each color to an index.
     */
    private void mapSetup() {
        colorsIndex.put(Color.GREEN, 0);
        colorsIndex.put(Color.RED, 1);
        colorsIndex.put(Color.YELLOW, 2);
        colorsIndex.put(Color.PINK, 3);
        colorsIndex.put(Color.BLUE, 4);
    }

    /**
     * Initializes the 12 islands on the board.
     * A random number states where Mother Nature is located initially.
     */
    public void initializeIslands(int mnPos) {
        for (int i = 0; i < 12; i++) {
            if (i == mnPos) {
                archipelagos.add(new Archipelago(null, true));
            } else if (i == (mnPos + 6) % 12) {
                archipelagos.add(new Archipelago(null, false));
            } else {
                archipelagos.add(new Archipelago(bag.draw(), false));
            }
        }
    }

    /**
     * Fills the bag with students of each color.
     */
    public void fillBag() {
        for (int i = 0; i < 24; i++) {
            bag.put(new Student(Color.GREEN));
            bag.put(new Student(Color.RED));
            bag.put(new Student(Color.YELLOW));
            bag.put(new Student(Color.PINK));
            bag.put(new Student(Color.BLUE));
        }
    }

    /**
     * Initializes the clouds on this board.
     */
    public void initializeClouds() {
        clouds = new Cloud[TOTALCLOUDS];
        for (int i = 0; i < TOTALCLOUDS; i++) {
            clouds[i] = new Cloud(CLOUDSIZE);
        }
        fillClouds();
    }

    /**
     * Initializes a school board for each player.
     */
    public void initializeBoards() {
        playerBoards = new SchoolBoard[players.size()];
        for (int i = 0; i < players.size(); i++) {
            playerBoards[i] = new SchoolBoard(players.get(i), drawStudentsArray(ENTRANCESIZE), TOTALTOWERS);
        }

        for (SchoolBoard x : playerBoards) {
            for (SchoolBoard y : playerBoards) {
                if (x != y) {
                    x.addOtherBoard(y);
                }
            }
        }
    }

    /**
     * Preliminarily sets the features of some characters
     * ({@link MonkEffect}, {@link JesterEffect}, {@link SpoiledPrincessEffect}).
     */
    public void initializeCharacters() {
        for (GameCharacter c : selectedCharacters) {
            switch (c.getName()) {
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
                default:
                    break;
            }
        }
    }

    /**
     * Draws students from the bag.
     *
     * @param size                  the number of Students to draw.
     * @return                      the Student Array of drawn students.
     */
    public Student[] drawStudentsArray(int size) {
        Student[] tmp = new Student[size];
        for (int i = 0; i < size; i++) {
            tmp[i] = bag.draw();
        }
        return tmp;
    }

    /**
     * Returns an archipelago given its index.
     *
     * @param archiIndex            the selected index.
     * @return                      the corresponding Archipelago.
     */
    public Archipelago getArchipelago(int archiIndex) {
        return archipelagos.get(archiIndex);
    }

    /**
     * Returns the school board of the current player.
     *
     * @return                      the SchoolBoard of the current Player.
     */
    public SchoolBoard getCurrentPlayerSchoolBoard() {
        SchoolBoard currentPlayerSchoolBoard = null;
        for (SchoolBoard s : playerBoards) {
            if (s.getPlayer().isCurrentPlayer()) {
                currentPlayerSchoolBoard = s;
            }
        }

        //short form: return Arrays.stream(playerBoards).filter(s -> s.getPlayer().getCanMoveStudents()).findFirst().orElse(null);
        return currentPlayerSchoolBoard;
    }

    /**
     * Returns a school board given the tower color.
     *
     * @param towerColor            the color of the towers.
     * @return                      the corresponding SchoolBoard.
     */
    public SchoolBoard getPlayerSchoolBoardByTeam(TowerColor towerColor) {
        SchoolBoard playerSchoolBoard = null;
        for (SchoolBoard s : playerBoards) {
            if (s.getPlayer().getTowerColor() == towerColor) {
                playerSchoolBoard = s;
            }
        }
        //short form: return Arrays.stream(playerBoards).filter(s -> s.getPlayer().getPlayerTeam() == towerColor).findFirst().orElse(null);
        return playerSchoolBoard;
    }

    /**
     * Enables the effect of the given character with the given parameters.
     *
     * @param characterName         the name of the GameCharacter.
     * @param archiIndex            the selected Archipelago.
     * @param numOfStudents         the number of Students.
     * @param studColors            the Colors of the Students.
     *
     * @see Effect
     *
     */
    public void playCharacter(String characterName, int archiIndex, int numOfStudents, Color... studColors) {
        GameCharacter selected = null;
        for (GameCharacter c : selectedCharacters) {
            if (c.getName().equals(characterName)) {
                selected = c;
                break;
            }
        }
        if (selected != null) {
            Player p = getCurrentPlayerSchoolBoard().getPlayer();
            p.removeCoins(selected.getCost());
            coinsSupply = coinsSupply + (selected.isAlreadyUsed() ? selected.getCost() : selected.getCost() - 1);
            selected.useEffect(this, archiIndex, numOfStudents, studColors);
        }
    }

    /**
     * Returns the bag on this board.
     *
     * @return                      the Bag on this Board.
     */
    public Bag getBag() {
        return bag;
    }

    /**
     * Gets the school board for each player.
     *
     * @return                      a SchoolBoard Array.
     */
    public SchoolBoard[] getPlayerBoards() {
        return playerBoards;
    }

    /**
     * Returns the color index according to the map.
     *
     * @param color                 the given Color.
     * @return                      the index corresponding to that Color.
     *
     * @see Board#mapSetup()
     */
    public int mapToIndex(Color color) {
        return colorsIndex.get(color);
    }

    /**
     * Returns the archipelagos on this board.
     *
     * @return                      the Archipelago List of this Board's archipelagos.
     */
    public List<Archipelago> getArchipelagos() {
        return archipelagos;
    }

    /**
     * Returns the clouds on this board.
     *
     * @return                      the Cloud Array of this Board's clouds.
     */
    public Cloud[] getClouds() {
        return clouds;
    }

    /**
     * Returns the players for this game.
     *
     * @return                      the Player List of players for this game.
     */
    public List<Player> getPlayers() {
        return players;
    }

    /**
     * Returns how many coins are in the supply.
     *
     * @return                      the number of coins.
     */
    public int getCoinsSupply() {
        return coinsSupply;
    }

    /**
     * Returns the selected characters for this game.
     *
     * @return                      the GameCharacter Array of selected characters.
     */
    public GameCharacter[] getSelectedCharacters() {
        return selectedCharacters;
    }

    /**
     * Returns the number of clouds on this board.
     *
     * @return                      the number of Clouds.
     */
    public int getTOTALCLOUDS() {
        return TOTALCLOUDS;
    }

    /**
     * Returns the cloud size for this game.
     *
     * @return                      the size of the Clouds.
     */
    public int getCLOUDSIZE() {
        return CLOUDSIZE;
    }

    /**
     * Returns the entrance size for this game.
     *
     * @return                      the size of the entrance.
     */
    public int getENTRANCESIZE() {
        return ENTRANCESIZE;
    }

    /**
     * Returns the number of towers for this game.
     *
     * @return                      the number of towers.
     */
    public int getTOTALTOWERS() {
        return TOTALTOWERS;
    }

    /**
     * Returns the colors index map.
     *
     * @return                      an EnumMap associating Colors and Integers.
     */
    public EnumMap<Color, Integer> getColorsIndex() {
        return colorsIndex;
    }

    /**
     * Returns the light version of the given board.
     *
     * @return                      the LightBoard.
     */
    public LightBoard getLightBoard(){
        return new LightBoard(this);
    }

    /**
     * Sets the board's bag (or mock bag)
     * @param bag                      the Bag
     */
    public void setBag(Bag bag) {
        this.bag = bag;
    }
}
