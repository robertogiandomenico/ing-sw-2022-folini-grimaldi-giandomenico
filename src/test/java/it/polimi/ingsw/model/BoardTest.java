package it.polimi.ingsw.model;

import it.polimi.ingsw.model.effects.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {

    private List<Board> boards;
    private List<Player> players;
    private List<GameCharacter[]> selectedCharacters;

    @BeforeEach
    void setUp() {
        boards = new ArrayList<>();
        players = new ArrayList<>();
        selectedCharacters = new ArrayList<>();

        players.add(new Player("player1", TowerColor.WHITE, Wizard.WIZARD1));
        players.add(new Player("player2", TowerColor.BLACK, Wizard.WIZARD2));
        players.add(new Player("player3", TowerColor.GREY, Wizard.WIZARD3));

        players.get(0).setCanMoveStudents(true);

        selectedCharacters.add(new GameCharacter[]{
                new GameCharacter(3, new HeraldEffect(), "Herald"),
                new GameCharacter(2, new KnightEffect(), "Knight"),
                new GameCharacter(3, new CentaurEffect(), "Centaur"),
        });

        selectedCharacters.add(new GameCharacter[]{
                new GameCharacter(2, new FarmerEffect(), "Farmer"),
                new GameCharacter(3, new MushroomManEffect(), "MushroomMan"),
                new GameCharacter(1, new JesterEffect(), "Jester"),
        });

        selectedCharacters.add(new GameCharacter[]{
                new GameCharacter(3, new ThiefEffect(), "Thief"),
                new GameCharacter(1, new MinstrelEffect(), "Minstrel"),
                new GameCharacter(1, new MonkEffect(), "Monk"),
        });

        selectedCharacters.add(new GameCharacter[]{
                new GameCharacter(2, new GrannyGrassEffect(), "GrannyGrass"),
                new GameCharacter(1, new MagicMailmanEffect(), "MagicMailman"),
                new GameCharacter(2, new SpoiledPrincessEffect(), "SpoiledPrincess")
        });

        for (GameCharacter[] selChar : selectedCharacters){
            boards.add(new Board(players, 3, 4, 9, 6, selChar));
        }
    }

    @AfterEach
    void tearDown() {
        boards = null;
        players = null;
        selectedCharacters = null;
    }

    @Test
    void testInitialization(){
        for (Board b : boards){
            assertEquals(17, b.getCoinsSupply());
            assertEquals(6, b.getTOTALTOWERS());

            List<Archipelago> archis = new ArrayList<>();
            for (int i = 0; i < 12; i++) {
                archis.add(b.getArchipelago(i));
            }

            //there is only one archi with MN
            assertEquals(1, archis.stream().filter(Archipelago::isMNPresent).count());

            //there are 2 archis with no students
            assertEquals(2, archis.stream().mapToInt(x -> x.getTotalStudents(Color.GREEN) + x.getTotalStudents(Color.RED) +
                                                             x.getTotalStudents(Color.BLUE) + x.getTotalStudents(Color.YELLOW) +
                                                             x.getTotalStudents(Color.PINK)).filter(s -> s == 0).count());
            //there are 10 archis with 1 student
            assertEquals(10, archis.stream().mapToInt(x -> x.getTotalStudents(Color.GREEN) + x.getTotalStudents(Color.RED) +
                                                             x.getTotalStudents(Color.BLUE) + x.getTotalStudents(Color.YELLOW) +
                                                             x.getTotalStudents(Color.PINK)).filter(s -> s == 1).count());

            assertEquals(b.getTOTALCLOUDS(), b.getClouds().length);
            for(Cloud c : b.getClouds()){
                assertFalse(c.isEmpty());
            }

            assertEquals(players, b.getPlayers());
            assertEquals(players.size(), b.getPlayerBoards().length);

            for (SchoolBoard sb : b.getPlayerBoards()){
                assertNotNull(sb);
            }

            assertEquals(selectedCharacters.get(boards.indexOf(b)), b.getSelectedCharacters());

        }

        assertEquals(120 - boards.get(0).getTOTALCLOUDS()*boards.get(0).getCLOUDSIZE() - 3*boards.get(0).getENTRANCESIZE(), boards.get(0).getBag().getSize());

        //120 - TOTALCLOUDS*CLOUDSIZE - players.size()*ENTRANCESIZE - JesterEffect.students.length
        assertEquals(120 - boards.get(1).getTOTALCLOUDS()*boards.get(1).getCLOUDSIZE() - 3*boards.get(1).getENTRANCESIZE() - 6,  boards.get(1).getBag().getSize());

        //120 - TOTALCLOUDS*CLOUDSIZE - players.size()*ENTRANCESIZE - MonkEffect.students.length
        assertEquals(120 - boards.get(2).getTOTALCLOUDS()*boards.get(2).getCLOUDSIZE() - 3*boards.get(2).getENTRANCESIZE() - 4, boards.get(2).getBag().getSize());

        //120 - TOTALCLOUDS*CLOUDSIZE - players.size()*ENTRANCESIZE - SpoiledPrincessEffect.students.length
        assertEquals(120 - boards.get(3).getTOTALCLOUDS()*boards.get(3).getCLOUDSIZE() - 3*boards.get(3).getENTRANCESIZE() - 4, boards.get(3).getBag().getSize());

    }

    @Test
    void moveFromCloudToEntrance() {
        Student[] mockCloudContent = {new Student(Color.PINK), new Student(Color.YELLOW), new Student(Color.GREEN), new Student(Color.BLUE)};
        for (int i = 0; i < 3; i++) {
            Board b = boards.get(i);
            Student[] entrance = b.getCurrentPlayerSchoolBoard().getEntrance();
            for (int j = 0; j < 4; j++) {
                b.moveFromEntranceToDiningRoom(entrance[j]);
            }

            b.getClouds()[i].fill(mockCloudContent);

            assertFalse(b.getClouds()[i].isEmpty());
            assertEquals(4, Arrays.stream(entrance).filter(Objects::isNull).count());
            b.moveFromCloudToEntrance(i);
            assertTrue(b.getClouds()[i].isEmpty());
            for (int j = 0; j < 4; j++) {
                assertEquals(mockCloudContent[i], entrance[i]);
            }

        }
    }

    @Test
    void testMoveFromEntrance() {
        int archiIndex = 5;
        for (Board b : boards){
            Student selectedStud = b.getCurrentPlayerSchoolBoard().getEntrance()[0];
            int oldNumOfStudents = b.getArchipelago(archiIndex).getTotalStudents(selectedStud.getColor());
            b.moveFromEntranceToArchipelago(selectedStud, archiIndex);
            assertEquals(oldNumOfStudents + 1, b.getArchipelago(archiIndex).getTotalStudents(selectedStud.getColor()));
            assertNull(b.getCurrentPlayerSchoolBoard().getEntrance()[0]);

            selectedStud = b.getCurrentPlayerSchoolBoard().getEntrance()[1];
            oldNumOfStudents = b.getCurrentPlayerSchoolBoard().getDiningRoom()[b.mapToIndex(selectedStud.getColor())];
            b.moveFromEntranceToDiningRoom(selectedStud);
            assertEquals(oldNumOfStudents + 1, b.getCurrentPlayerSchoolBoard().getDiningRoom()[b.mapToIndex(selectedStud.getColor())]);
            assertNull(b.getCurrentPlayerSchoolBoard().getEntrance()[1]);
        }
    }

    @Test
    void moveMotherNature() {

    }

}