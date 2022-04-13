package it.polimi.ingsw.model;

public class MushroomManEffect implements Effect {

    public void applyEffect(Board board, int archiIndex, int numOfStudents, Color...studColors) {
        Archipelago.setForbiddenColor(studColors[numOfStudents-1]);
    }

}