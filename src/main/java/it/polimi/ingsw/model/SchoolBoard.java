package it.polimi.ingsw.model;

import java.util.Arrays;

public class SchoolBoard {
    private final Player player;
    private Student[] entrance;
    private boolean[] professorTable;
    private int towersLeft;
    private int[] diningRoom;

    public SchoolBoard(Player player, Student[] entrance) {
        this.player = player;

        this.entrance = new Student[entrance.length];
        System.arraycopy(entrance, 0, this.entrance, 0, this.entrance.length);

        //set all professors to FALSE at the beginning
        Arrays.fill(professorTable, false);

        //set every table in diningRoom to 0 at the beginning
        Arrays.fill(diningRoom, 0);
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
    }

}
