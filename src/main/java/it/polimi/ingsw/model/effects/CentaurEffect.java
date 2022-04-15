package it.polimi.ingsw.model.effects;

import it.polimi.ingsw.model.*;

public class CentaurEffect implements Effect {

    public void applyEffect(Board board, int archiIndex, int numOfStudents, Color...studColors) {
        Archipelago selectedArchipelago = board.getArchipelago(archiIndex);
        TowerColor team = selectedArchipelago.getTowerColor();

        if(team != null) {
            int negativeInfluence;

            /* negativeInfluence is equal to the n. of towers in the archipelago, which corresponds with the actual n. of islands
            in the archipelago since every island can have one and one only tower */
            negativeInfluence = selectedArchipelago.getIslands().size() * (-1);

            board.getPlayerSchoolBoardByTeam(team).setAdditionalInfluence(negativeInfluence);

            // TODO: AdditionalInfluence must go back to 0  ** AT THE END OF THE TURN **, NOT IMMEDIATELY, need controller(?) to manage turns and send a signal to de-activate the effect */
        }
    }
}