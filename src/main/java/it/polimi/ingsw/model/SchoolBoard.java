package it.polimi.ingsw.model;

import java.util.ArrayList;
import java.util.List;
import java.lang.Math;

/**
 * This class represents the school boards of the game.
 * Each school board is composed by an entrance, a professor table, a dining room
 * and an area for the towers.
 * There is a school board for each player.
 */
public class SchoolBoard {
    private final Player player;
    private final Student[] entrance;
    private final boolean[] professorTable = new boolean[5];
    private int towersLeft;
    private final int[] diningRoom = new int[5];
    private int additionalInfluence = 0;
    private boolean farmerEffect;
    private final List<SchoolBoard> otherBoards;
    private int[] coinsPath;

    /**
     * Class constructor.
     *
     * @param player        the Player who owns this SchoolBoard.
     * @param entrance      the Student Array representing the entrance.
     * @param towersLeft    the number of towers in this SchoolBoard.
     */
    public SchoolBoard(Player player, Student[] entrance, int towersLeft) {
        this.player = player;

        this.entrance = new Student[entrance.length];
        System.arraycopy(entrance, 0, this.entrance, 0, entrance.length);

        this.towersLeft = towersLeft;

        coinsPath = new int[]{0, 0, 0, 0, 0};

        otherBoards = new ArrayList<>();
    }

    /**
     * Returns the player who owns this school board.
     *
     * @return              the Player who owns this SchoolBoard.
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Returns the number of towers left in this school board.
     *
     * @return              the number of towers left.
     */
    public int getTowersLeft() {
        return towersLeft;
    }

    /**
     * Returns the dining room of this school board.
     *
     * @return              the Array representing the dining room.
     */
    public int[] getDiningRoom() {
        return diningRoom;
    }

    /**
     * Returns the entrance of this school board.
     *
     * @return              the Student Array representing the entrance.
     */
    public Student[] getEntrance() {
        return entrance;
    }

    /**
     * Returns the additional influence.
     *
     * @return              the additional influence.
     */
    public int getAdditionalInfluence() {
        return additionalInfluence;
    }

    /**
     * States whether the given professor is present or not in this school board.
     *
     * @param index         the index corresponding to the Color of the professor.
     * @return              a boolean whose value is:
     *                      <p>
     *                      -{@code true} if the professor is in this SchoolBoard;
     *                      </p> <p>
     *                      -{@code false} otherwise.
     *                      </p>
     */
    public boolean isProfessorPresent(int index){
        return professorTable[index];
    }

    /**
     * Sets a professor.
     *
     * @param index         the index corresponding to the Color of the professor.
     * @param action        the action.
     */
    public void setProfessor(int index, boolean action){
        professorTable[index] = action;
    }

    /**
     * Adds a student to the entrance of this school board.
     *
     * @param student       the Student to be added.
     */
    public void addToEntrance(Student student){
        for(int i=0; i<(entrance.length); i++){
            if(entrance[i] == null){
                entrance[i] = student;
                break;
            }
        }
    }

    /**
     * Removes a student from the entrance of this school board.
     *
     * @param color         the Color of the Student to be removed.
     * @return              the Student removed.
     */
    public Student removeFromEntrance(Color color){
        Student tmp = null;
        for(int i=0; i<(entrance.length); i++){
            if( (entrance[i]!= null) && (entrance[i].getColor().equals(color)) ){
                tmp = entrance[i];
                entrance[i] = null;
                break;
            }
        }
        return tmp;
    }

    /**
     * Removes a student from the dining room of this school board.
     * Immediately checks if a professor has to be moved.
     *
     * @param index         the index corresponding to the Color of the Student
     *                      to be removed.
     * @param steal         a boolean whose value is:
     *                      <p>
     *                      -{@code true} if the method is called by {@link it.polimi.ingsw.model.effects.ThiefEffect}.
     *                      </p> <p>
     *                      -{@code false} otherwise.
     *                      </p>
     */
    public void removeFromDiningRoom(int index, boolean steal){
        if(diningRoom[index]>0) {
            diningRoom[index]--;
            if(!steal) checkProfessorMovement(index, "remove");
        }
    }

    /**
     * Adds a student to the dining room of this school board.
     * Immediately checks if a professor has to be moved.
     *
     * @param index         the index corresponding to the Student Color.
     */
    public void addToDiningRoom(int index){
        diningRoom[index]++;
        checkProfessorMovement(index, "add");
    }

    /**
     * Checks whether a student was put in the given dining room position for
     * the first time, thus if the player is entitled to get an additional coin.
     * (only in Expert Mode)
     *
     * @param dinIndex      the index corresponding to the Student Color.
     * @param dinPosition   the number of Students in that dining room.
     * @return              a boolean whose value is:
     *                      <p>
     *                      -{@code true} if the Player has to get a coin.
     *                      </p> <p>
     *                      -{@code false} otherwise.
     *                      </p>
     */
    public boolean checkCoinsPath(int dinIndex, int dinPosition){
        if( ((dinPosition == 3 && coinsPath[dinIndex] < 1) ||
                (dinPosition == 6 && coinsPath[dinIndex] < 2) ||
                (dinPosition == 9 && coinsPath[dinIndex] < 3)) ) {
            updateCoinsPath(dinIndex);
            return true;
        }
        return false;
    }

    /**
     * Updates the coins path of this school board. It increases every time the
     * player puts a student for the first time in a dining room position
     * containing a coin. (only in Expert Mode)
     *
     * @param index         the index corresponding to the Student Color.
     */
    public void updateCoinsPath(int index){
        coinsPath[index]++;
    }

    /**
     * Returns the coins path of this school board.
     *
     * @return              the Array representing coins path.
     */
    public int[] getCoinsPath(){
        return coinsPath;
    }

    /**
     * Returns the professor table.
     *
     * @return              the boolean Array representing the professor table.
     */
    public boolean[] getProfessorTable(){
        return professorTable;
    }

    /**
     * Removes towers from this school board.
     *
     * @param numOfTowers   the number of towers to be removed.
     */
    public void removeTowers(int numOfTowers){
        towersLeft = Math.max(towersLeft - numOfTowers, 0);
    }

    /**
     * Adds towers to this school board.
     *
     * @param numOfTowers   the number of towers to be added.
     */
    public void addTowers(int numOfTowers){
        towersLeft += numOfTowers;
    }

    /**
     * Sets the additional influence.
     * This method is called twice, once to set to the extraInfluence and once
     * to set it back to 0.
     *
     * @param additionalInfluence   the influence to be added.
     */
    public void setAdditionalInfluence(int additionalInfluence) {
        this.additionalInfluence = additionalInfluence;
    }

    /**
     * Sets the variable that states whether the {@link it.polimi.ingsw.model.effects.FarmerEffect
     * FarmerEffect} is active or not.
     *
     * @param farmerEffect  a boolean whose value is:
     *                      <p>
     *                      -{@code true} if the FarmerEffect is active;
     *                      </p> <p>
     *                      -{@code false} otherwise.
     *                      </p>
     */
    public void setFarmerEffect(boolean farmerEffect) {
        this.farmerEffect = farmerEffect;
    }

    /**
     * Adds a school board to the list containing other players' boards.
     *
     * @param schoolBoard   the SchoolBoard to be added.
     */
    public void addOtherBoard(SchoolBoard schoolBoard) {
        otherBoards.add(schoolBoard);
    }

    /**
     * Checks if a professor has to be moved from their position.
     * Adds a professor to/Removes a professor from this or other players' school
     * boards according to the game rules.
     *
     * @param index         the index corresponding to Color of the professor.
     * @param action        the action to undertake ({@code add}/{@code remove}).
     */
    public void checkProfessorMovement(int index, String action) {
        SchoolBoard enemyBoard = null;

        switch (action) {
            case "add":
                if(!professorTable[index]) { //I do not already own the professor
                    for(SchoolBoard x : otherBoards) { //check if one of my opponents has it
                        if(x.isProfessorPresent(index)) {
                            enemyBoard = x;
                            break;
                        }
                    }

                    if(enemyBoard == null) {
                        // case opponent not found, I ipso facto take the professor
                        professorTable[index] = true;
                    } else if((diningRoom[index] > enemyBoard.getDiningRoom()[index]) ||
                              (diningRoom[index] == enemyBoard.getDiningRoom()[index] && farmerEffect)) {
                        // case opponent found and I have more students OR I have equal students with farmerEffect active
                        professorTable[index] = true;
                        enemyBoard.setProfessor(index, false);
                    }
                }
                break;

            case "remove":
                if(professorTable[index]) { //I do already own the professor
                    enemyBoard = otherBoards.get(0);
                    for(SchoolBoard x : otherBoards) { //find opponent with the max number of students of that color
                        if(x.getDiningRoom()[index] > enemyBoard.getDiningRoom()[index]) {
                            enemyBoard = x;
                        }
                    }

                    if(enemyBoard.getDiningRoom()[index] > diningRoom[index]) {
                        //the selected enemy has more students of that color than me
                        enemyBoard.setProfessor(index, true);
                        professorTable[index] = false;
                    }
                }

                break;

            default:    //function should never reach this point
                break;
        }

    }

}
