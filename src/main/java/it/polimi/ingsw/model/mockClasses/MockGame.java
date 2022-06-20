package it.polimi.ingsw.model.mockClasses;

import it.polimi.ingsw.model.Bag;
import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.GameCharacter;

/**
 * This class is a mock version of the {@link it.polimi.ingsw.model.Game} that uses the {@link MockBoard}
 * instead of the normal {@link it.polimi.ingsw.model.Board} and that doesn't choose randomly the characters.
 */
public class MockGame extends Game {

    public MockGame(boolean expertMode) {
        super(expertMode);
    }

    @Override
    public void initializeBoard() {
        int TOTALCLOUDS, CLOUDSIZE, ENTRANCESIZE, TOTALTOWERS;
        GameCharacter[] selectedCharacters = null;

        TOTALCLOUDS = getNumberOfPlayers();
        CLOUDSIZE = getNumberOfPlayers() + 1;

        //linear functions that map the number of players to the dimension of the entrance and the number of towers
        ENTRANCESIZE = 2 * getNumberOfPlayers() + 3;  //2 players -> 7 spaces, 3 players -> 9 spaces
        TOTALTOWERS = -2 * getNumberOfPlayers() + 12; //2 players -> 8 towers, 3 players -> 6 towers

        if (isExpertMode()) {
            selectedCharacters = new GameCharacter[3];
            for (int i = 0; i < 3; i++) {
                selectedCharacters[i] = getAvailableCharacters().remove(i);
            }
        }

        //if the game is played in easy mode, board is created with a null reference for its selectedCharacters attribute
        setBoard(new Board(getPlayers(), TOTALCLOUDS, CLOUDSIZE, ENTRANCESIZE, TOTALTOWERS, selectedCharacters));
        getBoard().setBag(new Bag());
        getBoard().initBoard();
    }
}
