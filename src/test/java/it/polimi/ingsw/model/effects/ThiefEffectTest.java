package it.polimi.ingsw.model.effects;

import it.polimi.ingsw.model.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 *  This class tests the {@link it.polimi.ingsw.model.effects.ThiefEffect ThiefEffect} methods.
 */
class ThiefEffectTest {
    private Board board;
    private List<Player> players;
    private GameCharacter[] selectedCharacters;

    @BeforeEach
    void setUp() {
        players = new ArrayList<>();

        players.add(new Player("player1", TowerColor.WHITE, Wizard.ARTICWIZARD));
        players.add(new Player("player2", TowerColor.BLACK, Wizard.DESERTWIZARD));
        players.add(new Player("player3", TowerColor.GREY, Wizard.FORESTWIZARD));

        selectedCharacters = new GameCharacter[]{new GameCharacter(3, new ThiefEffect(), "Thief"),
                new GameCharacter(1, new MinstrelEffect(), "Minstrel"),
                new GameCharacter(1, new JesterEffect(), "Jester")};

        players.get(0).setCanMoveStudents(true);

        board = new Board(players, 3, 4, 9, 6, selectedCharacters);
    }

    @AfterEach
    void tearDown() {
        board = null;
        players = null;
        selectedCharacters = null;
    }

    @Test
    void applyEffect() {
        Color chosenColor = Color.PINK;
        int studentsPlayer0 = 4;
        int studentsPlayer1 = 2;
        int studentsPlayer2 = 0;


        for (int i = 0; i < studentsPlayer0; i++) {
            board.getPlayerBoards()[0].addToDiningRoom(board.mapToIndex(chosenColor));
        }

        for (int i = 0; i < studentsPlayer1; i++) {
            board.getPlayerBoards()[1].addToDiningRoom(board.mapToIndex(chosenColor));
        }

        for (int i = 0; i < studentsPlayer2; i++) {
            board.getPlayerBoards()[2].addToDiningRoom(board.mapToIndex(chosenColor));
        }

        board.playCharacter("Thief", 0, 1, chosenColor);

        assertEquals(Math.max(studentsPlayer0 - 3, 0), board.getPlayerBoards()[0].getDiningRoom()[board.mapToIndex(chosenColor)]);
        assertEquals(Math.max(studentsPlayer1 - 3, 0), board.getPlayerBoards()[1].getDiningRoom()[board.mapToIndex(chosenColor)]);
        assertEquals(Math.max(studentsPlayer2 - 3, 0), board.getPlayerBoards()[2].getDiningRoom()[board.mapToIndex(chosenColor)]);
    }
}