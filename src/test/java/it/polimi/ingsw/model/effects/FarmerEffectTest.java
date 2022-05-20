package it.polimi.ingsw.model.effects;

import it.polimi.ingsw.model.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This class tests the {@link it.polimi.ingsw.model.effects.FarmerEffect FarmerEffect} methods.
 */
class FarmerEffectTest {

    private Board board;
    private List<Player> players;
    private GameCharacter[] selectedCharacters;

    @BeforeEach
    void setUp() {
        players = new ArrayList<>();

        players.add(new Player("player1", TowerColor.WHITE, Wizard.ARTICWIZARD));
        players.add(new Player("player2", TowerColor.BLACK, Wizard.DESERTWIZARD));
        players.add(new Player("player3", TowerColor.GREY, Wizard.FORESTWIZARD));

        selectedCharacters = new GameCharacter[]{new GameCharacter(2, new FarmerEffect(), "Farmer"),
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
    void testApplyEffect() {
        for (Color c : Color.values()){
            assertFalse(board.getCurrentPlayerSchoolBoard().isProfessorPresent(board.mapToIndex(c)));
        }
        board.playCharacter("Farmer", 0, 0);

        for (Color c : Color.values()){
            assertTrue(board.getCurrentPlayerSchoolBoard().isProfessorPresent(board.mapToIndex(c)));
        }

        //player0(current) profTable = [true, true, true, true, true]
        //player0(current) diningRoom = [1, 1, 1, 1, 0]
        board.getCurrentPlayerSchoolBoard().addToDiningRoom(0);
        board.getCurrentPlayerSchoolBoard().addToDiningRoom(1);
        board.getCurrentPlayerSchoolBoard().addToDiningRoom(2);
        board.getCurrentPlayerSchoolBoard().addToDiningRoom(3);

        board.getCurrentPlayerSchoolBoard().setFarmerEffect(false);

        //CurrentPlayer = player0 -> player1
        players.get(0).setCanMoveStudents(false);
        players.get(1).setCanMoveStudents(true);

        //player1's profTable = [false, false, false, false, false]
        //player1's diningRoom = [1, 1, 0, 0, 0]
        board.getCurrentPlayerSchoolBoard().addToDiningRoom(0);
        board.getCurrentPlayerSchoolBoard().addToDiningRoom(1);

        for (Color c : Color.values()){
            assertFalse(board.getCurrentPlayerSchoolBoard().isProfessorPresent(board.mapToIndex(c)));
        }

        //player1(current) profTable = [true, true, false, false, true]
        //player0's profTable = [false, false, true, true, false]
        board.playCharacter("Farmer", 0, 0);

        for (Color c : Color.values()){
            if(board.mapToIndex(c) < 2 || board.mapToIndex(c) == 4) {
                assertTrue(board.getCurrentPlayerSchoolBoard().isProfessorPresent(board.mapToIndex(c)));
                assertFalse(board.getPlayerBoards()[0].isProfessorPresent(board.mapToIndex(c)));
            } else {
                assertFalse(board.getCurrentPlayerSchoolBoard().isProfessorPresent(board.mapToIndex(c)));
                assertTrue(board.getPlayerBoards()[0].isProfessorPresent(board.mapToIndex(c)));
            }
        }

        //player0's profTable = [false, false, false, true, false]
        //player1(current) profTable = [true, true, true, false (because FarmerEffect off when added), true]
        //player1's diningRoom = [1, 1, 1, 1, 0]
        board.getCurrentPlayerSchoolBoard().addToDiningRoom(2);
        board.getCurrentPlayerSchoolBoard().setFarmerEffect(false);
        board.getCurrentPlayerSchoolBoard().addToDiningRoom(3);

        for (Color c : Color.values()){
            if (board.mapToIndex(c) != 3){
                assertTrue(board.getCurrentPlayerSchoolBoard().isProfessorPresent(board.mapToIndex(c)));
                assertFalse(board.getPlayerBoards()[0].isProfessorPresent(board.mapToIndex(c)));
            } else {
                assertFalse(board.getCurrentPlayerSchoolBoard().isProfessorPresent(board.mapToIndex(c)));
                assertTrue(board.getPlayerBoards()[0].isProfessorPresent(board.mapToIndex(c)));
            }
        }
    }
}