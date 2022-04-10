package it.polimi.ingsw.model;

public class CentaurEffect implements Effect {

    public void applyEffect(Board board) {
        int archiIndex = setArchiIndex();
        Archipelago selectedArchipelago = board.getArchipelago(archiIndex);

        if(selectedArchipelago.getTowerColor() != null) {
            int negativeInfluence;
            TeamColor team;

            /* negativeInfluence is equal to the n. of towers in the archipelago, which corresponds with the actual n. of islands
            in the archipelago since every island can have one and one only tower */
            negativeInfluence = selectedArchipelago.getIslands().size() * (-1);
            team = selectedArchipelago.getTowerColor();

            board.getPlayerSchoolBoardByTeam(team).setAdditionalInfluence(negativeInfluence);

            /* is it really the applyEffect that must call the calculateInfluence() ?
            board.calculateInfluence(selectedArchipelago);  */

            //additionalInfluence goes back to 0
            /* TODO: AdditionalInfluence must go back to 0  ** AT THE END OF THE TURN **, NOT IMMEDIATELY, need controller(?) to manage turns and send a signal to de-activate the effect */
            board.getPlayerSchoolBoardByTeam(team).setAdditionalInfluence(0);
        }
    }

    //TODO: controller must intervene to set this variable
    public int setArchiIndex() {
        int index = 0; //controller should ask the user for the input
        return index;
    }
}