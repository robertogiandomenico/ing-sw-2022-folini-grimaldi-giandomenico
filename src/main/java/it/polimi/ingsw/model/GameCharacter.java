package it.polimi.ingsw.model;

public abstract class GameCharacter {
    int cost;
    Effect effect;
    boolean active;


    public GameCharacter(int cost, Effect effect, boolean active) {
        this.cost = cost;
        this.effect = effect;
        this.active = active;
    }

    public void updateCost(){
        cost++;
    }

    public int getCost(){
        return cost;
    }

    public boolean isActive(){
        return active;
    }

    public abstract void useEffect();

}
