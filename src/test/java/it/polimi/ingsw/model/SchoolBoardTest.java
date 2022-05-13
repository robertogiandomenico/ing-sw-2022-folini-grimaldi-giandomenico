package it.polimi.ingsw.model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This class tests the {@link it.polimi.ingsw.model.SchoolBoard SchoolBoard} methods.
 */
class SchoolBoardTest {

    private SchoolBoard sb; //currentPlayerSB
    private List<SchoolBoard> otherBoards;
    private List<Player> players;

    @BeforeEach
    void setUp() {
        players = new ArrayList<>();
        otherBoards = new ArrayList<>();

        players.add(new Player("player1", TowerColor.WHITE, Wizard.WIZARD1));
        players.add(new Player("player2", TowerColor.BLACK, Wizard.WIZARD2));
        players.add(new Player("player3", TowerColor.GREY, Wizard.WIZARD3));

        //blue x2, green x1, yellow x2, pink x4, red x0
        Student[] entrance1 = new Student[]{new Student(Color.BLUE), new Student(Color.GREEN), new Student(Color.YELLOW), new Student(Color.YELLOW), new Student(Color.PINK), new Student(Color.PINK), new Student(Color.BLUE), new Student(Color.PINK), new Student(Color.PINK)};
        //blue x2, green x3, yellow x1, pink x1, red x2
        Student[] entrance2 = new Student[]{new Student(Color.GREEN), new Student(Color.GREEN), new Student(Color.YELLOW), new Student(Color.GREEN), new Student(Color.PINK), new Student(Color.BLUE), new Student(Color.BLUE), new Student(Color.RED), new Student(Color.RED)};
        //blue x1, green x2, yellow x1, pink x1, red x4
        Student[] entrance3 = new Student[]{new Student(Color.YELLOW), new Student(Color.RED), new Student(Color.RED), new Student(Color.BLUE), new Student(Color.RED), new Student(Color.RED), new Student(Color.GREEN), new Student(Color.GREEN), new Student(Color.PINK)};

        sb = new SchoolBoard(players.get(0), entrance1, 6);

        otherBoards.add(new SchoolBoard(players.get(1), entrance2, 6));
        otherBoards.add(new SchoolBoard(players.get(2), entrance3, 6));

        sb.addOtherBoard(otherBoards.get(0));
        sb.addOtherBoard(otherBoards.get(1));
        otherBoards.get(0).addOtherBoard(sb);
        otherBoards.get(0).addOtherBoard(otherBoards.get(1));
        otherBoards.get(1).addOtherBoard(sb);
        otherBoards.get(1).addOtherBoard(otherBoards.get(0));
    }

    @AfterEach
    void tearDown() {
        sb = null;
        players = null;
        otherBoards = null;
    }

    @Test
    void testGetPlayer() {
        assertEquals(players.get(0), sb.getPlayer());
    }

    @Test
    void testTowersLeftMethods() {
        assertEquals(6, sb.getTowersLeft());

        sb.removeTowers(5);
        assertEquals(1, sb.getTowersLeft());

        sb.addTowers(4);
        assertEquals(5, sb.getTowersLeft());

        sb.removeTowers(1000);
        assertEquals(0, sb.getTowersLeft());
    }

    @Test
    void testAdditionalInfluence() {
        int[] test = {2, 0, -5};
        for (int i : test) {
            sb.setAdditionalInfluence(i);
            assertEquals(i, sb.getAdditionalInfluence());
        }
    }

    @Test
    void testEntranceMovements() {
        for (Color c : Color.values()) {
            if (c == Color.RED) {
                assertNull(sb.removeFromEntrance(c));
            } else {
                assertEquals(c, sb.removeFromEntrance(c).getColor());
            }
        }
        //after 1st for loop: blue x1, green x0, yellow x1, pink x3, red x0

        for (int i = 0; i < 4; i++) {
            sb.addToEntrance(new Student(Color.GREEN));
        }
        //after 2nd for loop: blue x1, green x4, yellow x1, pink x3, red x0

        List<Student> expectedEntrance = new ArrayList<>(
                Arrays.asList(
                        new Student(Color.BLUE), new Student(Color.GREEN), new Student(Color.GREEN), new Student(Color.GREEN), new Student(Color.GREEN), new Student(Color.YELLOW), new Student(Color.PINK), new Student(Color.PINK), new Student(Color.PINK)
                )
        );

        for (Color c : Color.values()) {
            assertEquals(expectedEntrance.stream().filter(x -> x.getColor() == c).count(), Arrays.stream(sb.getEntrance()).filter(x -> x.getColor() == c).count());
        }
    }

    @Test
    void testDiningRoomMovements() {
        //INDEXES: GREEN 0, RED 1, YELLOW 2, PINK 3, BLUE 4
        int index = 0;

        //checking that nobody has the green professor
        assertFalse(sb.isProfessorPresent(index));
        for (SchoolBoard x : otherBoards) {
            assertFalse(x.isProfessorPresent(index));
        }

        //adding a green student to the currentPlayerSB and making sure that only the current player has the green professor
        sb.addToDiningRoom(index);
        assertEquals(1, sb.getDiningRoom()[index]);
        assertTrue(sb.isProfessorPresent(index));
        for (SchoolBoard x : otherBoards) {
            assertEquals(0, x.getDiningRoom()[index]);
            assertFalse(x.isProfessorPresent(index));
        }

        //adding 2 green students to another player's dining room and making sure that they have the green professor now
        for (int i = 0; i < 2; i++) {
            otherBoards.get(1).addToDiningRoom(index);
        }
        assertTrue(otherBoards.get(1).isProfessorPresent(index));
        assertFalse(sb.isProfessorPresent(index));

        //adding a green student to the currentPlayerSB (with farmerEffect active) and making sure that they got their green professor back
        //because both they and the previous owner of the green professor have 2 students
        sb.setFarmerEffect(true);
        sb.addToDiningRoom(index);
        assertTrue(sb.isProfessorPresent(index));
        assertFalse(otherBoards.get(1).isProfessorPresent(index));
        sb.setFarmerEffect(false);

        //removing a green student from the currentPlayerSB and making sure that they lose the green professor
        sb.removeFromDiningRoom(index);
        assertEquals(1, sb.getDiningRoom()[index]);
        assertTrue(otherBoards.get(1).isProfessorPresent(index));
        assertFalse(sb.isProfessorPresent(index));
    }
}