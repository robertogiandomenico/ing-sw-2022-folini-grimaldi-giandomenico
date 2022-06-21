package it.polimi.ingsw.model;

import it.polimi.ingsw.model.effects.*;
import java.util.*;

/**
 * This class represents the whole game.
 * It manages the players and initializes board, wizards, towers and expert mode
 * features during a match.
 */
public class Game {
    private Board board;
    private final List<Player> players;
    private List<Wizard> availableWizards;
    private List<GameCharacter> availableCharacters;
    private List<TowerColor> availableTowers;
    private int numberOfPlayers;
    private List<Player> playerOrder;
    private Player currentPlayer;
    private final boolean expertMode;

    /**
     * Class constructor specifying if expert mode is selected.
     * Initializes wizards and towers and expert mode features (if required).
     *
     * @param expertMode      a boolean whose value is:
     *                        <p>
     *                        -{@code true} if expert mode is selected;
     *                        </p> <p>
     *                        -{@code false} otherwise.
     *                        </p>
     */
    public Game(boolean expertMode) {
        this.expertMode = expertMode;

        players = new ArrayList<>();
        playerOrder = new ArrayList<>();

        initializeWizards();
        initializeTowers();
        if (expertMode) initializeExpertModeFeatures();

    }

    /**
     * Adds a new player to the game.
     *
     * @param newPlayer       the Player to be added.
     */
    public void addNewPlayer(Player newPlayer) {
        players.add(newPlayer);
        playerOrder.add(newPlayer);
        availableTowers.remove(newPlayer.getTowerColor());
        availableWizards.remove(newPlayer.getSelectedWizard());
    }

    /**
     * Returns the player corresponding to the given nickname.
     *
     * @param nickname        the nickname of the Player.
     * @return                the Player corresponding to the nickname.
     */
    public Player getPlayerByNickname(String nickname) {
        for (Player p : players) {
            if (p.getNickname().equals(nickname)) {
                return p;
            }
        }
        return null;
    }

    /**
     * Sets the given player as current.
     *
     * @param currentPlayer   the Player to be set as current.
     */
    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
        for (Player p : players){
            p.setCanMoveStudents(p.equals(currentPlayer));
        }
    }

    /**
     * Returns the current player.
     *
     * @return                the current Player.
     */
    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    /**
     * Updates playing order according to the weight of the chosen assistant cards.
     */
    //this method uses a simple sorting algorithm to sort the players according to the weight
    //of the last assistant played and then sets the current player as the first player for the next turn
    public void updatePlayersOrder() {
        for (int i = 0; i < numberOfPlayers - 1; i++) {
            for (int j = i + 1; j < numberOfPlayers; j++) {
                if (playerOrder.get(i).getDiscardPile().getWeight() > playerOrder.get(j).getDiscardPile().getWeight()) {
                    Player tmp = playerOrder.get(i);
                    playerOrder.set(i, playerOrder.get(j));
                    playerOrder.set(j, tmp);
                }
            }
        }
        setCurrentPlayer(playerOrder.get(0));
    }

    /**
     * Initializes board (either easy or expert mode).
     */
    public void initializeBoard() {
        int TOTALCLOUDS, CLOUDSIZE, ENTRANCESIZE, TOTALTOWERS;
        GameCharacter[] selectedCharacters = null;
        Random rand = new Random();

        TOTALCLOUDS = numberOfPlayers;
        CLOUDSIZE = numberOfPlayers + 1;

        //linear functions that map the number of players to the dimension of the entrance and the number of towers
        ENTRANCESIZE = 2 * numberOfPlayers + 3;  //2 players -> 7 spaces, 3 players -> 9 spaces
        TOTALTOWERS = -2 * numberOfPlayers + 12; //2 players -> 8 towers, 3 players -> 6 towers

        if (expertMode) {
            selectedCharacters = new GameCharacter[3];
            for (int i = 0; i < 3; i++) {
                selectedCharacters[i] = availableCharacters.remove(rand.nextInt(12 - i));
            }
        }

        //if the game is played in easy mode, board is created with a null reference for its selectedCharacters attribute
        board = new Board(players, TOTALCLOUDS, CLOUDSIZE, ENTRANCESIZE, TOTALTOWERS, selectedCharacters);
    }

    /**
     * Initializes all the possible wizards.
     */
    private void initializeWizards() {
        availableWizards = new ArrayList<>(
                Arrays.asList(
                        Wizard.ARTICWIZARD,
                        Wizard.DESERTWIZARD,
                        Wizard.FORESTWIZARD,
                        Wizard.SKYWIZARD
                )
        );
    }

    /**
     * Initializes all the possible tower colors.
     */
    private void initializeTowers() {
        availableTowers = new ArrayList<>(
                Arrays.asList(
                        TowerColor.WHITE,
                        TowerColor.BLACK,
                        TowerColor.GREY
                )
        );
    }

    /**
     * Initializes all the expert mode features (GameCharacters).
     */
    private void initializeExpertModeFeatures() {
        availableCharacters = new ArrayList<>(
                Arrays.asList(
                        new GameCharacter(3, new HeraldEffect(), "Herald"),
                        new GameCharacter(2, new KnightEffect(), "Knight"),
                        new GameCharacter(3, new CentaurEffect(), "Centaur"),
                        new GameCharacter(2, new FarmerEffect(), "Farmer"),
                        new GameCharacter(3, new MushroomManEffect(), "MushroomMan"),
                        new GameCharacter(1, new JesterEffect(), "Jester"),
                        new GameCharacter(3, new ThiefEffect(), "Thief"),
                        new GameCharacter(1, new MinstrelEffect(), "Minstrel"),
                        new GameCharacter(1, new MonkEffect(), "Monk"),
                        new GameCharacter(2, new GrannyGrassEffect(), "GrannyGrass"),
                        new GameCharacter(1, new MagicMailmanEffect(), "MagicMailman"),
                        new GameCharacter(2, new SpoiledPrincessEffect(), "SpoiledPrincess")
                )
        );
    }

    /**
     * Returns game board.
     *
     * @return                the Board of this Game.
     */
    public Board getBoard() {
        return board;
    }

    /**
     * Returns the players of this game.
     *
     * @return                the Player List of this Game.
     */
    public List<Player> getPlayers() {
        return players;
    }

    /**
     * Returns the available wizards.
     *
     * @return                the Wizard List of available ones.
     */
    public List<Wizard> getAvailableWizards() {
        return availableWizards;
    }

    /**
     * Returns the available characters.
     *
     * @return                the GameCharacter List of available ones.
     */
    public List<GameCharacter> getAvailableCharacters() {
        return availableCharacters;
    }

    /**
     * Returns the available tower colors.
     *
     * @return                the TowerColor List of available ones.
     */
    public List<TowerColor> getAvailableTowers() {
        return availableTowers;
    }

    /**
     * Returns the number of players.
     *
     * @return                the number of Players.
     */
    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }

    /**
     * Returns the players ordered according to the turns.
     *
     * @return                a Player List of the playing order.
     */
    public List<Player> getPlayerOrder() {
        return playerOrder;
    }

    /**
     * States whether the expert mode is chosen or not for this game.
     *
     * @return                a boolean whose value is:
     *                        <p>
     *                        -{@code true} if expert mode is selected;
     *                        </p> <p>
     *                        -{@code false} otherwise.
     *                        </p>
     */
    public boolean isExpertMode() {
        return expertMode;
    }

    /**
     * Sets the number of players for this game.
     *
     * @param numberOfPlayers the selected number of Players.
     */
    public void setNumberOfPlayers(int numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;
    }
}