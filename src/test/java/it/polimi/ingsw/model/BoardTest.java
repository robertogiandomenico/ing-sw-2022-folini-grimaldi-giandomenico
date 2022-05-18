package it.polimi.ingsw.model;

import it.polimi.ingsw.model.effects.*;
import it.polimi.ingsw.view.utilities.lightclasses.LightBoard;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This class tests the {@link it.polimi.ingsw.model.Board Board} methods.
 */
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
                new GameCharacter(1, new MinstrelEffect(), "Minstrel"),
                new GameCharacter(3, new CentaurEffect(), "Centaur"),
        });

        selectedCharacters.add(new GameCharacter[]{
                new GameCharacter(2, new FarmerEffect(), "Farmer"),
                new GameCharacter(2, new KnightEffect(), "Knight"),
                new GameCharacter(1, new JesterEffect(), "Jester"),
        });

        selectedCharacters.add(new GameCharacter[]{
                new GameCharacter(3, new ThiefEffect(), "Thief"),
                new GameCharacter(3, new MushroomManEffect(), "MushroomMan"),
                new GameCharacter(1, new MonkEffect(), "Monk"),
        });

        selectedCharacters.add(new GameCharacter[]{
                new GameCharacter(2, new GrannyGrassEffect(), "GrannyGrass"),
                new GameCharacter(1, new MagicMailmanEffect(), "MagicMailman"),
                new GameCharacter(2, new SpoiledPrincessEffect(), "SpoiledPrincess")
        });

        selectedCharacters.add(new GameCharacter[]{
                new GameCharacter(3, new HeraldEffect(), "Herald"),
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
    void testMoveMotherNature() {
        for (Board b : boards) {
            int indexMN;
            int newIndexMN;

            for (indexMN = 0; indexMN < b.getArchipelagos().size(); indexMN++) {
                if (b.getArchipelago(indexMN).isMNPresent()) break;
            }

            assertEquals(12, b.getArchipelagos().stream().filter(a -> a.getTowerColor() == null).count());

            b.getPlayers().get(0).chooseAssistant(Assistant.TURTLE);
            if(boards.indexOf(b) == 3) {
                b.playCharacter("MagicMailman", 0, 0);
                assertEquals(Assistant.TURTLE.getMaxMNSteps() + 2, b.getCurrentPlayerSchoolBoard().getPlayer().getMaxSteps());
            }
            b.moveMotherNature(b.getPlayers().get(0).getMaxSteps());

            for (newIndexMN = 0; newIndexMN < b.getArchipelagos().size(); newIndexMN++) {
                if (b.getArchipelago(newIndexMN).isMNPresent()) break;
            }

            assertEquals(newIndexMN, (indexMN + b.getPlayers().get(0).getMaxSteps()) % b.getArchipelagos().size());
        }
    }

    @Test
    void testCalculateInfluence(){
        //Adding a new board in order to test every influence-related character's effect and every merging and tower placing scenario
        boards.add(new Board(players, 3, 4, 9, 6, selectedCharacters.get(4)));
        for (Board b : boards) {
            int archiIndex = boards.indexOf(b);

            Archipelago archi = b.getArchipelago(archiIndex);
            assertFalse(archi.isNoEntryTilePresent());

            //if the archi is empty I fill it with a student in order to be able to test all the possible merging and tower placing scenarios
            boolean empty = archi.getIslands().get(0).hasNoStudents();
            if(empty) archi.getIslands().get(0).addStudent(new Student(Color.PINK));

            if (boards.indexOf(b) == 3) {
                b.playCharacter("GrannyGrass", archiIndex, 0);
                assertTrue(archi.isNoEntryTilePresent());
                GrannyGrassEffect gge = (GrannyGrassEffect) b.getSelectedCharacters()[0].getEffect();
                assertEquals(3, gge.getNoEntryTiles());
                archi.setTowerColor(TowerColor.BLACK);
            }

            int rightArchiIndex = (archiIndex + 1) % b.getArchipelagos().size();
            int leftArchiIndex = archiIndex != 0 ? archiIndex - 1 : b.getArchipelagos().size() - 1;

            Archipelago archiLeft = b.getArchipelago(leftArchiIndex);
            Archipelago archiRight = b.getArchipelago(rightArchiIndex);

            //I want the current player to take the archi, so I add a student of the same color of the student on the archi to the player's
            //dining room in order to get the current player to take the professor of that color
            for (Color c : Color.values()) {
                if (archi.getTotalStudents(c) > 0){
                    archi.getIslands().get(0).addStudent(new Student(c));
                    b.getCurrentPlayerSchoolBoard().addToDiningRoom(b.mapToIndex(c));
                    if (boards.indexOf(b) == 2){
                        b.playCharacter("MushroomMan", archiIndex, 1, c);
                    }
                }
            }

            if (boards.indexOf(b) == 0) {
                archi.setTowerColor(TowerColor.BLACK);
                archiLeft.setTowerColor(TowerColor.WHITE);
                archiRight.setTowerColor(TowerColor.WHITE);
                b.playCharacter("Centaur", archiIndex, 0);
            } else if (boards.indexOf(b) == 1) {
                archi.setTowerColor(TowerColor.GREY);
                archiLeft.setTowerColor(TowerColor.WHITE);
                b.playCharacter("Knight", 0, 0);
                assertEquals(2, b.getCurrentPlayerSchoolBoard().getAdditionalInfluence());
            }

            if(boards.indexOf(b) == 4) {
                b.playCharacter("Herald", archiIndex, 0);
            }
            else {
                b.calculateInfluence(archi);
            }

            switch (boards.indexOf(b)) {
                //Board 0: merged both left and right archis into the current one (current player uses Centaur Effect)
                case 0:
                    assertEquals(10, b.getArchipelagos().size());
                    assertEquals(3, archi.getIslands().size());
                    assertEquals(TowerColor.WHITE, archi.getTowerColor());
                    assertFalse(b.getArchipelagos().contains(archiLeft));
                    assertFalse(b.getArchipelagos().contains(archiRight));
                    break;
                //Board 1: merged only left archi into the current one (Current player uses KnightEffect)
                case 1:
                    assertEquals(11, b.getArchipelagos().size());
                    assertEquals(2, archi.getIslands().size());
                    assertEquals(0, b.getCurrentPlayerSchoolBoard().getAdditionalInfluence());
                    assertEquals(TowerColor.WHITE, archi.getTowerColor());
                    assertFalse(b.getArchipelagos().contains(archiLeft));
                    assertTrue(b.getArchipelagos().contains(archiRight));
                    break;
                //Board 2: no merge, no tower placed (Current player uses MushroomManEffect)
                case 2:
                    assertEquals(12, b.getArchipelagos().size());
                    assertNull(archi.getTowerColor());
                    assertTrue(b.getArchipelagos().contains(archiLeft));
                    assertTrue(b.getArchipelagos().contains(archiRight));
                    break;
                //Board 3: no merge, black tower is still present on archi (CurrentPlayer uses GrannyGrassEffect)
                case 3:
                    assertEquals(12, b.getArchipelagos().size());
                    assertEquals(TowerColor.BLACK, archi.getTowerColor());
                    assertTrue(b.getArchipelagos().contains(archiLeft));
                    assertTrue(b.getArchipelagos().contains(archiRight));
                    assertFalse(archi.isNoEntryTilePresent());
                    break;
                //Board 4: no merge, white player conquers archi
                case 4:
                    assertEquals(12, b.getArchipelagos().size());
                    assertEquals(TowerColor.WHITE, archi.getTowerColor());
                    assertTrue(b.getArchipelagos().contains(archiLeft));
                    assertTrue(b.getArchipelagos().contains(archiRight));
            }
        }

    }

    @Test
    void testCoinsManagement() {
        int boardIndex = 0;
        SchoolBoard sb = boards.get(boardIndex).getCurrentPlayerSchoolBoard();
        List<SchoolBoard> otherBoards = new ArrayList<>();

        for (SchoolBoard s : boards.get(boardIndex).getPlayerBoards()){
            if(!s.equals(sb)) otherBoards.add(s);
        }

        //players only have 1 coin in the beginning
        assertEquals(1, sb.getPlayer().getCoins());
        assertEquals(1, otherBoards.get(0).getPlayer().getCoins());
        assertEquals(1, otherBoards.get(1).getPlayer().getCoins());

        Color color = Color.YELLOW;
        int index = boards.get(boardIndex).getColorsIndex().get(color);
        for (int i = 0; i < 2; i++) {                           //adding 2 yellow students
            sb.addToDiningRoom(index);
        }
        assertEquals(1, sb.getPlayer().getCoins());

        sb.removeFromEntrance(sb.getEntrance()[0].getColor());
        sb.addToEntrance(new Student(color));

        boards.get(boardIndex).moveFromEntranceToDiningRoom(new Student(color));

        //making sure that there's possibility of giving a coin and that only the current player gets one
        assertEquals(sb.getCoinsPath()[index], 1);
        assertFalse(sb.checkCoinsPath(index, sb.getDiningRoom()[index]));
        assertEquals(2, sb.getPlayer().getCoins());
        assertEquals(1, otherBoards.get(0).getPlayer().getCoins());
        assertEquals(1, otherBoards.get(1).getPlayer().getCoins());

        //removing the last student from the dining room and re-adding it to check that coins number stays the same
        sb.removeFromDiningRoom(index);
        sb.addToDiningRoom(index);
        assertEquals(2, sb.getPlayer().getCoins());

        //adding 6 more students
        for(int i=0; i<6; i++) {
            sb.addToEntrance(new Student(Color.YELLOW));
            boards.get(boardIndex).moveFromEntranceToDiningRoom(new Student(Color.YELLOW));
        }

        assertEquals(4, sb.getPlayer().getCoins());
        assertTrue(sb.getProfessorTable()[index]);
    }

    @Test
    void testLightBoard(){
        for (Board b : boards){
            LightBoard lb = b.getLightBoard();
            assertNotNull(lb);
            assertEquals(b.getArchipelagos().size(), lb.getArchipelagos().size());
        }
    }
}