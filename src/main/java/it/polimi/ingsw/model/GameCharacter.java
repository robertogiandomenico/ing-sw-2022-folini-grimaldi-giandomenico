package it.polimi.ingsw.model;

public class GameCharacter {
    int cost;
    Effect effect;
    boolean alreadyUsed;

    public GameCharacter(int cost, Effect effect) {
        this.cost = cost;
        this.effect = effect;
    }

    public void updateCost(){
        if(!alreadyUsed){
            cost++;
            alreadyUsed = true;
        }
    }

    public int getCost(){
        return cost;
    }

    public void useEffect(Board board) {
        effect.applyEffect(board);
    }
}
