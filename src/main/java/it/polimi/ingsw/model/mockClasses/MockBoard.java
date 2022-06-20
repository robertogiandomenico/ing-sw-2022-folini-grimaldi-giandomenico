package it.polimi.ingsw.model.mockClasses;

import it.polimi.ingsw.model.*;

import java.util.List;

/**
 * This class is a mock version of the {@link it.polimi.ingsw.model.Board} that uses the {@link MockBag}
 * instead of the normal {@link it.polimi.ingsw.model.Bag} in order to always have the same students' disposition
 * on the board.
 */
public class MockBoard extends Board {
    private final int mnPos;

    public MockBoard(List<Player> players, int TOTALCLOUDS, int CLOUDSIZE, int ENTRANCESIZE, int TOTALTOWERS, GameCharacter[] selectedCharacters, int mnPos) {
        super(players, TOTALCLOUDS, CLOUDSIZE, ENTRANCESIZE, TOTALTOWERS, selectedCharacters);
        this.mnPos = mnPos;
        setBag(new MockBag());
        initBoard();
    }

    @Override
    public void initBoard() {
        initializeIslands(mnPos);
        fillBag();
        initializeClouds();
        initializeBoards();
        if (getSelectedCharacters() != null) initializeCharacters();
    }
}
