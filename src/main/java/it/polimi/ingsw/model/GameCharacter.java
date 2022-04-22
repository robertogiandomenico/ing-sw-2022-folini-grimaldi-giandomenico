package it.polimi.ingsw.model;

import it.polimi.ingsw.model.effects.*;

public class GameCharacter {
    private int cost;
    private final String name;
    private final Effect effect;
    private boolean alreadyUsed;

    public GameCharacter(int cost, Effect effect, String name) {
        this.cost = cost;
        this.effect = effect;
        this.name = name;
    }

    private void updateCost(){
        cost++;
        alreadyUsed = true;
    }

    public int getCost(){
        return cost;
    }

    public String getName() {
        return name;
    }

    public Effect getEffect(){
        return effect;
    }

    public boolean isAlreadyUsed() {
        return alreadyUsed;
    }

    public void useEffect(Board board, int archiIndex, int numOfStudents, Color...studColors) {
        if (!alreadyUsed) updateCost();
        effect.applyEffect(board, archiIndex, numOfStudents, studColors);
    }
}
