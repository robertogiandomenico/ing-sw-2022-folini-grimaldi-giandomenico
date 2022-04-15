package it.polimi.ingsw.model;

import it.polimi.ingsw.effects.*;
import java.util.*;

public class Game {
    private Board board;
    private final List<Player> players;
    private List<Wizard> availableWizards;
    private List<GameCharacter> availableCharacters;
    private List<TowerColor> availableTowers;
    private int numberOfPlayers;
    private Player[] playerOrder;
    private Player currentPlayer;
    private final boolean expertMode;

    public Game(boolean expertMode){
        this.expertMode = expertMode;

        players = new ArrayList<>();

        initializeWizards();
        initializeTowers();
        if(expertMode) initializeExpertModeFeatures();

        setInitialOrder();

        //TODO: implement players login methods

        setBoard();
    }

    public void start(){
        //TODO: implement start() method
    }

    public void createNewPlayer(){
        //TODO: implement createNewPlayer()
        numberOfPlayers++;
    }

    public Player getPlayerByNickname(String nickname){
        for(Player p : players){
            if(p.getNickname().equals(nickname)){
                return p;
            }
        }
        return null;
    }

    public void setCurrentPlayer(Player currentPlayer){
        this.currentPlayer = currentPlayer;
    }

    public Player getCurrentPlayer(){
        return currentPlayer;
    }

    //this method uses a simple sorting algorithm to sort the players according to the weight
    //of the last assistant played and then sets the current player as the first player for the next turn
    public void updatePlayersOrder(){
        for (int i = 0; i < numberOfPlayers - 1; i++) {
            for(int j = i+1; j < numberOfPlayers; j++){
                if(playerOrder[i].getDiscardPile().getWeight() > playerOrder[j].getDiscardPile().getWeight()){
                    Player tmp = playerOrder[i];
                    playerOrder[i] = playerOrder[j];
                    playerOrder[j] = tmp;
                }
            }
        }
        setCurrentPlayer(playerOrder[0]);
    }

    public void setBoard(){
        int TOTALCLOUDS, CLOUDSIZE, ENTRANCESIZE, TOTALTOWERS;
        GameCharacter[] selectedCharacters = null;
        Random rand = new Random();

        TOTALCLOUDS = numberOfPlayers;
        CLOUDSIZE = numberOfPlayers + 1;

        //linear functions that map the number of players to the dimension of the entrance and the number of towers
        ENTRANCESIZE = 2 * numberOfPlayers + 3;  //2 players -> 7 spaces, 3 players -> 9 spaces
        TOTALTOWERS = -2 * numberOfPlayers + 12; //2 players -> 8 towers, 3 players -> 6 towers

        if(expertMode){
            selectedCharacters = new GameCharacter[3];
            for (int i = 0; i < 3; i++){
                selectedCharacters[i] = availableCharacters.remove(rand.nextInt(12-i));
            }
        }

        //if the game is played in easy mode, board is created with a null reference for its selectedCharacters attribute
        board = new Board(players, TOTALCLOUDS, CLOUDSIZE, ENTRANCESIZE, TOTALTOWERS, selectedCharacters);
    }

    //initialize all the possible wizards
    private void initializeWizards(){
        availableWizards = new ArrayList<>(
                Arrays.asList(
                        Wizard.WIZARD1,
                        Wizard.WIZARD2,
                        Wizard.WIZARD3,
                        Wizard.WIZARD4
                )
        );
    }

    //initialize all the possible tower colors
    private void initializeTowers(){
        availableTowers = new ArrayList<>(
                Arrays.asList(
                        TowerColor.WHITE,
                        TowerColor.BLACK,
                        TowerColor.GREY
                )
        );
    }

    //initialize all the expert mode features (GameCharacters)
    private void initializeExpertModeFeatures(){
        availableCharacters = new ArrayList<>(
                Arrays.asList(
                        new GameCharacter(3, new HeraldEffect()),
                        new GameCharacter(2, new KnightEffect()),
                        new GameCharacter(3, new CentaurEffect()),
                        new GameCharacter(2, new FarmerEffect()),
                        new GameCharacter(3, new MushroomManEffect()),
                        new GameCharacter(1, new JesterEffect()),
                        new GameCharacter(3, new ThiefEffect()),
                        new GameCharacter(1, new MinstrelEffect()),
                        new GameCharacter(1, new MonkEffect()),
                        new GameCharacter(2, new GrannyGrassEffect()),
                        new GameCharacter(1, new MagicMailmanEffect()),
                        new GameCharacter(2, new SpoiledPrincessEffect())
                )
        );
    }

    //set the initial order of the players equals to the login order
    private void setInitialOrder(){
        for (int i = 0; i < numberOfPlayers; i++) {
            playerOrder[i] = players.get(i);
        }
    }
}