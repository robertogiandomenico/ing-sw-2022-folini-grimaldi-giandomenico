package it.polimi.ingsw.model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This class tests the {@link it.polimi.ingsw.model.Player Player} methods.
 */
class PlayerTest {
    private final int NUMPLAYERS = 3;
    private List<Player> players;
    private List<String> nicknames;
    private List<TowerColor> towerColors;
    private List<Wizard> wizards;
    private List<Assistant> assistants;

    @BeforeEach
    void setUp() {
        players = new ArrayList<>();
        nicknames = new ArrayList<>(Arrays.asList(
                "ale",
                "fra",
                "rob"
        ));
        towerColors = new ArrayList<>(Arrays.asList(
                TowerColor.WHITE,
                TowerColor.BLACK,
                TowerColor.GREY
        ));

        wizards = new ArrayList<>(Arrays.asList(
                Wizard.ARTICWIZARD,
                Wizard.DESERTWIZARD,
                Wizard.FORESTWIZARD
        ));

        assistants = new ArrayList<>(
                Arrays.asList(
                        Assistant.CAT,
                        Assistant.CHEETAH,
                        Assistant.DOG,
                        Assistant.EAGLE,
                        Assistant.ELEPHANT,
                        Assistant.FOX,
                        Assistant.LIZARD,
                        Assistant.OCTOPUS,
                        Assistant.OSTRICH,
                        Assistant.TURTLE
                )
        );

        for (int i = 0; i < NUMPLAYERS; i++) {
            players.add(new Player(nicknames.get(i), towerColors.get(i), wizards.get(i)));
        }
    }

    @AfterEach
    void tearDown() {
        players = null;
        nicknames = null;
        towerColors = null;
        wizards = null;
    }

    @Test
    void testGetNickname() {
        for (int i = 0; i < NUMPLAYERS; i++){
            assertEquals(nicknames.get(i), players.get(i).getNickname());
        }
    }

    @Test
    void testGetCoins() {
        for (int i = 0; i < NUMPLAYERS; i++){
            assertEquals(1, players.get(i).getCoins());
        }
    }

    @Test
    void testGetTowerColor() {
        for (int i = 0; i < NUMPLAYERS; i++){
            assertEquals(towerColors.get(i), players.get(i).getTowerColor());
        }
    }

    @Test
    void testGetDiscardPile() {
        for (int i = 0; i < NUMPLAYERS; i++){
            assertNull(players.get(i).getDiscardPile());
        }
    }

    @Test
    void testGetSelectedWizard() {
        for (int i = 0; i < NUMPLAYERS; i++){
            assertEquals(wizards.get(i), players.get(i).getSelectedWizard());
        }
    }

    @Test
    void testBooleanFlags() {
        for (int i = 0; i < NUMPLAYERS; i++){
            assertFalse(players.get(i).getCanChooseAssistant());
            assertFalse(players.get(i).getCanMoveStudents());
            assertFalse(players.get(i).getCanBuyCharacter());
        }

        players.get(0).setCanChooseAssistant(true);
        players.get(0).setCanMoveStudents(true);
        players.get(0).setCanBuyCharacter(true);

        assertTrue(players.get(0).getCanChooseAssistant());
    }

    @Test
    void testAddCoin() {
        int[] coins = {5, 3, 9};
        for (int i = 0; i < NUMPLAYERS; i++){
            for (int j = 0; j < coins[i]; j++) {
                players.get(i).addCoin();
            }
        }

        for (int i = 0; i < NUMPLAYERS; i++) {
            assertEquals(coins[i]+1, players.get(i).getCoins());
        }
    }

    @Test
    void testRemoveCoins() {
        for (int i = 0; i < NUMPLAYERS; i++) {
            int removed = players.get(i).removeCoins(1);
            assertEquals(0, players.get(i).getCoins());
        }

        int removed = players.get(0).removeCoins(3);
        assertEquals(0, players.get(0).getCoins());
    }

    @Test
    void testChooseAssistant() {
        for (Player p : players){
            int oldSize = p.getCards().size();
            p.chooseAssistant(p.getCards().get(players.indexOf(p)));
            assertEquals(oldSize - 1, p.getCards().size());
            assertFalse(p.getCards().stream().anyMatch(x -> x == p.getDiscardPile()));
            assertEquals(p.getDiscardPile().getMaxMNSteps(), p.getMaxSteps());
            p.setSelectedSteps(1);
            assertEquals(1, p.getSelectedSteps());
        }

        players.get(0).setAdditionalSteps();
        assertEquals(players.get(0).getDiscardPile().getMaxMNSteps() + 2, players.get(0).getMaxSteps());
    }

    @Test
    void testGetCards() {
        for (int i = 0; i < NUMPLAYERS; i++) {
            assertEquals(assistants, players.get(i).getCards());
        }
    }

}