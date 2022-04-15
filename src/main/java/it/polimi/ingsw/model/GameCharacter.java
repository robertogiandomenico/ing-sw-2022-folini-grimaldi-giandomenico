package it.polimi.ingsw.model;
import it.polimi.ingsw.effects.*;

public class GameCharacter {
    private int cost;
    private Effect effect;
    private boolean alreadyUsed;

    public GameCharacter(int cost, Effect effect) {
        this.cost = cost;
        this.effect = effect;
    }

    private void updateCost(){
        cost++;
        alreadyUsed = true;
    }

    public int getCost(){
        return cost;
    }

    public void useEffect(Board board, int archiIndex, int numOfStudents, Color...studColors) {
        if (!alreadyUsed) updateCost();
        effect.applyEffect(board, archiIndex, numOfStudents, studColors);
    }
}
