package it.polimi.ingsw.model.effects;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.mockClasses.MockBoard;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 *  This class tests the {@link it.polimi.ingsw.model.effects.MinstrelEffect MinstrelEffect} methods.
 */
class MinstrelEffectTest {
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
                new GameCharacter(1, new CentaurEffect(), "Centaur")};

        players.get(0).setCanMoveStudents(true);

        board = new MockBoard(players, 3, 4, 9, 6, selectedCharacters, 0);
        initializeEntrances(board);
    }

    private void initializeEntrances(Board b) {
        for(SchoolBoard sb : b.getPlayerBoards()){
            for(Student s : sb.getEntrance()){
                sb.removeFromEntrance(s.getColor());
            }
            for(int i = 0; i < 3; i++){
                sb.addToEntrance(new Student(Color.GREEN));
                sb.addToEntrance(new Student(Color.YELLOW));
                sb.addToEntrance(new Student(Color.BLUE));
            }
        }
    }

    @AfterEach
    void tearDown() {
        board = null;
        players = null;
        selectedCharacters = null;
    }


    @Test
    void applyEffect() {
        Student[] entrance = board.getCurrentPlayerSchoolBoard().getEntrance();

        board.getCurrentPlayerSchoolBoard().addToDiningRoom(0);
        board.getCurrentPlayerSchoolBoard().addToDiningRoom(1);
        board.getCurrentPlayerSchoolBoard().addToDiningRoom(2);

        int[] diningRoom = board.getCurrentPlayerSchoolBoard().getDiningRoom().clone();

        Student chosenFromEntrance = entrance[0];

        board.playCharacter("Minstrel", 0, 2, Color.RED, Color.YELLOW, chosenFromEntrance.getColor(), Color.PINK);

        assertEquals(Color.RED, board.getCurrentPlayerSchoolBoard().getEntrance()[0].getColor());
        assertNotEquals(chosenFromEntrance, board.getCurrentPlayerSchoolBoard().getEntrance()[0]);

        int[] updatedDR = board.getCurrentPlayerSchoolBoard().getDiningRoom();

        if(chosenFromEntrance.getColor() == Color.RED){
            for (int i = 0; i < updatedDR.length; i++) {
                assertEquals(diningRoom[i], updatedDR[i]);
            }
        } else {
            assertEquals(diningRoom[board.mapToIndex(Color.RED)] - 1, updatedDR[board.mapToIndex(Color.RED)]);
            assertEquals(diningRoom[board.mapToIndex(chosenFromEntrance.getColor())] + 1, updatedDR[board.mapToIndex(chosenFromEntrance.getColor())]);
        }
    }
}