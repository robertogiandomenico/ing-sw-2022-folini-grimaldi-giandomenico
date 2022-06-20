package it.polimi.ingsw.model;

import it.polimi.ingsw.model.mockClasses.MockGame;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This class tests the {@link it.polimi.ingsw.model.Game Game} methods using the {@link it.polimi.ingsw.model.mockClasses.MockGame}.
 */
class GameTest {
    private List<Game> games;
    private List<Player> players;

    @BeforeEach
    void setUp() {
        games = new ArrayList<>(
                Arrays.asList(
                        new MockGame(false),
                        new MockGame(true)
                )
        );
        players = new ArrayList<>(
                Arrays.asList(
                        new Player("test1", TowerColor.WHITE, Wizard.ARTICWIZARD),
                        new Player("test2", TowerColor.BLACK, Wizard.DESERTWIZARD),
                        new Player("test3", TowerColor.GREY, Wizard.FORESTWIZARD)
                )
        );

        for (Game g : games){
            assertEquals(List.of(Wizard.values()), g.getAvailableWizards());
            assertEquals(List.of(TowerColor.values()), g.getAvailableTowers());
            if (g.isExpertMode()){
                assertNotNull(g.getAvailableCharacters());
            } else {
                assertNull(g.getAvailableCharacters());
            }
        }
    }

    @AfterEach
    void tearDown() {
        games = null;
        players = null;
    }

    @Test
    void testAddNewPlayer() {
        for (Game g : games){
            assertEquals(0, g.getNumberOfPlayers());
        }

        for (Player p : players){
            games.get(0).addNewPlayer(p);
            games.get(0).setNumberOfPlayers(games.get(0).getNumberOfPlayers() + 1);
        }

        for (int i = 0; i < 2; i++) {
            games.get(1).addNewPlayer(players.get(i));
            games.get(1).setNumberOfPlayers(games.get(1).getNumberOfPlayers() + 1);
        }

        assertEquals(3, games.get(0).getNumberOfPlayers());
        assertEquals(players, games.get(0).getPlayers());
        assertEquals(2, games.get(1).getNumberOfPlayers());
        assertEquals(players.subList(0, 2), games.get(1).getPlayers());
    }

    @Test
    void testGetPlayerByNickname() {
        for (Player p : players){
            games.get(0).addNewPlayer(p);
            games.get(1).addNewPlayer(p);
        }

        String[] nicknames = {"test1", "test2", "test3", "", "prova29133", "Test2", "test", ".3.2"};

        for (int i = 0; i < nicknames.length; i++){
            if(i < 3){
                assertEquals(players.get(i), games.get(0).getPlayerByNickname(nicknames[i]));
                assertEquals(players.get(i), games.get(1).getPlayerByNickname(nicknames[i]));
            } else {
                assertNull(games.get(0).getPlayerByNickname(nicknames[i]));
                assertNull(games.get(1).getPlayerByNickname(nicknames[i]));
            }
        }
    }

    @Test
    void testUpdatePlayersOrder() {
        games.get(0).setNumberOfPlayers(players.size());
        for (Player p : players){
            games.get(0).addNewPlayer(p);
        }

        List<Player> expectedOrder = new ArrayList<>(
                Arrays.asList(
                        games.get(0).getPlayers().get(2),
                        games.get(0).getPlayers().get(1),
                        games.get(0).getPlayers().get(0)
                )
        );

        games.get(0).getPlayers().get(0).chooseAssistant(Assistant.DOG); //weight = 8
        games.get(0).getPlayers().get(1).chooseAssistant(Assistant.CAT); //weight = 3
        games.get(0).getPlayers().get(2).chooseAssistant(Assistant.OSTRICH); //weight = 2
        games.get(0).updatePlayersOrder();

        assertEquals(expectedOrder, games.get(0).getPlayerOrder());
        assertEquals(expectedOrder.get(0), games.get(0).getCurrentPlayer());
    }

    @Test
    void testInitializeBoard() {
        for (Player p : players){
            games.get(0).addNewPlayer(p);
            games.get(1).addNewPlayer(p);
        }

        for (Game g : games){
            g.initializeBoard();
            Board b = g.getBoard();
            assertEquals(players, b.getPlayers());
            assertEquals(g.getNumberOfPlayers(), b.getTOTALCLOUDS());
            assertEquals((g.getNumberOfPlayers() + 1), b.getCLOUDSIZE());
            assertEquals((2 * g.getNumberOfPlayers() + 3), b.getENTRANCESIZE());
            assertEquals((-2 * g.getNumberOfPlayers() + 12), b.getTOTALTOWERS());
            if (!g.isExpertMode()){
                assertNull(b.getSelectedCharacters());
            } else {
                for (GameCharacter c : b.getSelectedCharacters()){
                    assertFalse(g.getAvailableCharacters().stream().anyMatch(c1 -> c1.equals(c)));
                }
            }
        }
    }
}