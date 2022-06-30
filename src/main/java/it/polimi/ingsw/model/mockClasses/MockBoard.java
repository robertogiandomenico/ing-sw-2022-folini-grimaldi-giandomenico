package it.polimi.ingsw.model.mockClasses;

import it.polimi.ingsw.model.*;

import java.util.List;

/**
 * This class is a mock version of the {@link Board} that uses the {@link MockBag}
 * instead of the normal {@link Bag} in order to always have the same students' disposition
 * on the board.
 */
public class MockBoard extends Board {
    private final int mnPos;

    /**
     * Class constructor specifying a position for mother nature.
     *
     * @param players               a List of Players.
     * @param TOTALCLOUDS           the number of Clouds.
     * @param CLOUDSIZE             the number of Students for each Cloud.
     * @param ENTRANCESIZE          the number of Students for each school entrance.
     * @param TOTALTOWERS           the number of towers for each Player.
     * @param selectedCharacters    the List of GameCharacters drawn for this game.
     * @param mnPos                 Mother Nature's position.
     */
    public MockBoard(List<Player> players, int TOTALCLOUDS, int CLOUDSIZE, int ENTRANCESIZE, int TOTALTOWERS, GameCharacter[] selectedCharacters, int mnPos) {
        super(players, TOTALCLOUDS, CLOUDSIZE, ENTRANCESIZE, TOTALTOWERS, selectedCharacters);
        this.mnPos = mnPos;
        setBag(new MockBag());
        initBoard();
    }

    /**
     * Sets all the board content (islands, bag, clouds, school boards, characters).
     */
    @Override
    public void initBoard() {
        initializeIslands(mnPos);
        fillBag();
        initializeClouds();
        initializeBoards();
        if (getSelectedCharacters() != null) initializeCharacters();
    }
}
