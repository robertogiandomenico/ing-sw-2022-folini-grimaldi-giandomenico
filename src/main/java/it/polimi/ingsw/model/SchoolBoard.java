package it.polimi.ingsw.model;

public class SchoolBoard {
    private final Player player;
    private final Student[] entrance;
    private final boolean[] professorTable = new boolean[5];
    private int towersLeft;
    private final int[] diningRoom = new int[5];

    public SchoolBoard(Player player, Student[] entrance, int towersLeft) {
        this.player = player;

        this.entrance = new Student[entrance.length];
        System.arraycopy(entrance, 0, this.entrance, 0, entrance.length);

        this.towersLeft = towersLeft;
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
    }

    public void removeTowers(int numOfTowers){
        towersLeft -= numOfTowers;
    }

    public void addTowers(int numOfTowers){
        towersLeft += numOfTowers;
    }
}
