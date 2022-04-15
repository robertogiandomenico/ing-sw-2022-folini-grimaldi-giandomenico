package it.polimi.ingsw.model.effects;

import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.Color;

public interface Effect {
    //Color...studColor means that the function can take a variable number of colors (this is useful because some effects
    //affect only one student while others affect > 1)
    void applyEffect(Board board, int archiIndex, int numOfStudents, Color...studColors);
}
