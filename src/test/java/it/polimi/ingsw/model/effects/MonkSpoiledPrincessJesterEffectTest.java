package it.polimi.ingsw.model.effects;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.mockClasses.MockBoard;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 *  This class tests {@link it.polimi.ingsw.model.effects.MonkEffect MonkEffect},
 *  {@link it.polimi.ingsw.model.effects.SpoiledPrincessEffect SpoiledPrincessEffect}
 *  and {@link it.polimi.ingsw.model.effects.JesterEffect JesterEffect} methods.
 */
class MonkSpoiledPrincessJesterEffectTest {
    private Board board;
    private List<Player> players;
    private GameCharacter[] selectedCharacters;

    @BeforeEach
    void setUp() {
        players = new ArrayList<>();

        players.add(new Player("player1", TowerColor.WHITE, Wizard.ARCTICWIZARD));
        players.add(new Player("player2", TowerColor.BLACK, Wizard.DESERTWIZARD));
        players.add(new Player("player3", TowerColor.GREY, Wizard.FORESTWIZARD));

        selectedCharacters = new GameCharacter[]{new GameCharacter(1, new MonkEffect(), "Monk"),
                new GameCharacter(2, new SpoiledPrincessEffect(), "SpoiledPrincess"),
                new GameCharacter(1, new JesterEffect(), "Jester")};

        players.get(0).setCurrentPlayer(true);

        board = new MockBoard(players, 3, 4, 9, 6, selectedCharacters, 0);
    }

    @AfterEach
    void tearDown() {
        board = null;
        players = null;
        selectedCharacters = null;
    }

    @Test
    void testApplyMonkEffect() {
        MonkEffect me = (MonkEffect) board.getSelectedCharacters()[0].getEffect();
        Student[] monkStuds = me.getStudents();

        int chosenArchiIndex = 0;
        Archipelago chosenArchi = board.getArchipelago(chosenArchiIndex);
        int[] archiColors = new int[5];

        for (Color c : Color.values()){
            archiColors[board.mapToIndex(c)] += chosenArchi.getTotalStudents(c);
        }

        int bagSize = board.getBag().getSize();

        Student chosenMonkStud = monkStuds[0];
        board.playCharacter("Monk", chosenArchiIndex, 1, chosenMonkStud.getColor());

        assertNotEquals(chosenMonkStud, monkStuds[0]);
        assertNotNull(monkStuds[0]);
        assertEquals(bagSize - 1, board.getBag().getSize());

        for (Color c : Color.values()){
            if (c == chosenMonkStud.getColor()){
                assertEquals(archiColors[board.mapToIndex(c)] + 1, chosenArchi.getTotalStudents(c));
            } else {
                assertEquals(archiColors[board.mapToIndex(c)], chosenArchi.getTotalStudents(c));
            }
        }
    }

    @Test
    void testApplySpoiledPrincessEffect(){
        SpoiledPrincessEffect se = (SpoiledPrincessEffect) board.getSelectedCharacters()[1].getEffect();
        Student[] princessStuds = se.getStudents();

        Student chosenPrincessStud = princessStuds[0];

        int bagSize = board.getBag().getSize();

        assertEquals(0, board.getCurrentPlayerSchoolBoard().getDiningRoom()[board.mapToIndex(chosenPrincessStud.getColor())]);

        board.playCharacter("SpoiledPrincess", 0, 1, chosenPrincessStud.getColor());

        assertNotEquals(chosenPrincessStud, princessStuds[0]);
        assertNotNull(princessStuds[0]);
        assertEquals(1, board.getCurrentPlayerSchoolBoard().getDiningRoom()[board.mapToIndex(chosenPrincessStud.getColor())]);
        assertEquals(bagSize - 1, board.getBag().getSize());
    }

    @Test
    void testApplyJesterEffect(){
        JesterEffect je = (JesterEffect) board.getSelectedCharacters()[2].getEffect();
        Student[] jesterStuds = je.getStudents();
        Student[] entrance = board.getCurrentPlayerSchoolBoard().getEntrance();

        Student[] chosenJesterStud = new Student[]{jesterStuds[0], jesterStuds[1], jesterStuds[2]};
        Student[] chosenEntranceStud = new Student[]{entrance[0], entrance[1], entrance[2]};

        for (Student s : jesterStuds){
            assertFalse(Arrays.asList(entrance).contains(s));
        }

        for (Student s : entrance){
            assertFalse(Arrays.asList(jesterStuds).contains(s));
        }

        board.playCharacter("Jester", 0, 3, chosenJesterStud[0].getColor(), chosenJesterStud[1].getColor(), chosenJesterStud[2].getColor(), chosenEntranceStud[0].getColor(), chosenEntranceStud[1].getColor(), chosenEntranceStud[2].getColor());

        for (int i = 0; i < 3; i++){
            assertEquals(jesterStuds[i], chosenEntranceStud[i]);
            assertEquals(entrance[i], chosenJesterStud[i]);
        }
    }
}