package it.polimi.ingsw.model;

import java.util.ArrayList;
import java.util.List;

public class SchoolBoard {
    private final Player player;
    private final Student[] entrance;
    private final boolean[] professorTable = new boolean[5];
    private int towersLeft;
    private final int[] diningRoom = new int[5];
    private int additionalInfluence = 0;
    private boolean farmerEffect;
    private final List<SchoolBoard> otherBoards;

    public SchoolBoard(Player player, Student[] entrance, int towersLeft) {
        this.player = player;

        this.entrance = new Student[entrance.length];
        System.arraycopy(entrance, 0, this.entrance, 0, entrance.length);

        this.towersLeft = towersLeft;

        otherBoards = new ArrayList<>();
    }

    public Player getPlayer() {
        return player;
    }

    public int getTowersLeft() {
        return towersLeft;
    }

    public int[] getDiningRoom() {
        return diningRoom;
    }

    public boolean isProfessorPresent(int index){
        return professorTable[index];
    }

    public void setProfessor(int index, boolean action){
        professorTable[index] = action;
    }

    public void addToEntrance(Student student){
        for(int i=0; i<(entrance.length); i++){
            if(entrance[i] == null){
                entrance[i] = student;
                break;
            }
        }
    }

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

    public void addToDiningRoom(int index){
        diningRoom[index]++;
        checkProfessorMovement(index, "add");
    }

    public void removeFromDiningRoom(int index){
        if(diningRoom[index]>0) {
            diningRoom[index]--;
            checkProfessorMovement(index, "remove");
        }
    }

    public void removeTowers(int numOfTowers){
        towersLeft -= numOfTowers;
    }

    public void addTowers(int numOfTowers){
        towersLeft += numOfTowers;
    }

    //call this method twice, once to set to 2 and once to set it back to 0
    public void setAdditionalInfluence() {
        additionalInfluence = additionalInfluence==0 ? 2 : 0;
    }

    public void setFarmerEffect(boolean farmerEffect) {
        this.farmerEffect = farmerEffect;
    }

    public void addOtherBoard(SchoolBoard schoolBoard) {
        otherBoards.add(schoolBoard);
    }

    private void checkProfessorMovement(int index, String action) {
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
                              (diningRoom[index] == enemyBoard.getDiningRoom()[index] && farmerEffect)){
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
