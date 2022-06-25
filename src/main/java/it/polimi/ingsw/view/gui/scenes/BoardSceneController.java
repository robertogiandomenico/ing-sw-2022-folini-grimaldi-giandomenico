package it.polimi.ingsw.view.gui.scenes;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.network.messages.clientMessages.*;
import it.polimi.ingsw.view.gui.GUI;
import it.polimi.ingsw.view.utilities.DataChores;
import it.polimi.ingsw.view.utilities.MatrixOperations;
import it.polimi.ingsw.view.utilities.lightclasses.LightArchi;
import it.polimi.ingsw.view.utilities.lightclasses.LightBoard;
import it.polimi.ingsw.view.utilities.lightclasses.LightCharacter;
import it.polimi.ingsw.view.utilities.lightclasses.LightSchoolBoard;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * This class represents the controller for the main game scene involving
 * the board and all of its features.
 */
public class BoardSceneController implements SceneControllerInterface {
    @FXML
    private HBox charactersBox;
    @FXML
    private GridPane assistantBox;
    @FXML
    private GridPane archipelagosBox;
    @FXML
    private HBox cloudsBox;
    @FXML
    private AnchorPane coinsSupplyBox;
    @FXML
    private AnchorPane thisPlayerPane;
    @FXML
    private TabPane otherPlayersPane;
    @FXML
    private AnchorPane thisPlayerDR;
    private GUI gui;
    private LightBoard board;
    private LightSchoolBoard thisPlayerBoard;
    private LightCharacter selectedCharacter;
    private int archiIndex;
    private int studentNumber;
    private Color[] studColors;

    /**
     * Initializes the scene.
     * Adds school boards, archipelagos, clouds, characters and other details
     * to the scene.
     */
    @FXML
    public void initialize() {
        initializeCharacters(board.getSelectedCharacters());
        initializeThisPlayer();
        initializeOtherPlayers();
        initializeArchipelagos(board.getArchipelagos());
        initializeClouds();
        initializeCoinsSupply(board.getCoinsSupply());

        charactersBox.setDisable(true);
        assistantBox.setDisable(true);
        archipelagosBox.setDisable(true);
        cloudsBox.setDisable(true);
    }

    /**
     * Initializes the characters to display for this game.
     *
     * @param selectedCharacters  a LightCharacter Array.
     */
    public void initializeCharacters(LightCharacter[] selectedCharacters) {
        try {
            //set character image
            for (int i = 0; i < 3; i++) {
                ((ImageView)((AnchorPane)charactersBox.getChildren().get(i)).getChildren().get(0)).setImage(new Image(getClass().getResourceAsStream("/img/characters/" + selectedCharacters[i].getName().toLowerCase() + ".png")));

                charactersBox.getChildren().get(i).setId(selectedCharacters[i].getName());

                //set additional elements on the card if it's one of the following
                switch (selectedCharacters[i].getName()) {
                    case "GrannyGrass":
                        try {
                            ((Label)((AnchorPane) charactersBox.getChildren().get(i)).getChildren().get(3)).setText("x" + selectedCharacters[i].getNoEntryTiles());

                        } catch (IndexOutOfBoundsException e) {
                            Label noEntryTileLeft = new Label();

                            noEntryTileLeft.setLayoutX(41);
                            noEntryTileLeft.setLayoutY(107);
                            noEntryTileLeft.setText("x" + selectedCharacters[i].getNoEntryTiles());
                            noEntryTileLeft.setStyle("-fx-font-family: \"Metamorphous\"");

                            ((AnchorPane) charactersBox.getChildren().get(i)).getChildren().add(noEntryTileLeft);
                        }
                        break;

                    case "Monk":

                    case "SpoiledPrincess":

                    case "Jester":
                        for (int j = 0; j < selectedCharacters[i].getStudents().length; j++)
                            ((ImageView)((AnchorPane)((AnchorPane)charactersBox.getChildren().get(i)).getChildren().get(1)).getChildren().get(j)).setImage(displayStudent(selectedCharacters[i].getStudents()[j]));

                        break;

                    default:
                        break;
                }

                //set coin image if the character has been already used once
                if (selectedCharacters[i].isAlreadyUsed())
                    ((AnchorPane)charactersBox.getChildren().get(i)).getChildren().get(2).setVisible(true);
                else
                    ((AnchorPane)charactersBox.getChildren().get(i)).getChildren().get(2).setVisible(false);

            }
        } catch (NullPointerException e) {
            //if this exception is thrown, the game is in easy mode
            charactersBox.setDisable(true);
            charactersBox.setVisible(false);
        }
    }

    /**
     * Initializes wizard, assistants and board of this client.
     */
    public void initializeThisPlayer() {
        for (LightSchoolBoard lsb : board.getSchoolBoards()) {
            if (lsb.getPlayer().getNickname().equals(gui.getClient().getNickname())) {
                thisPlayerBoard = lsb;
                break;
            }
        }

        //set wizard image and nickname
        ((Label) thisPlayerPane.getChildren().get(1)).setText(thisPlayerBoard.getPlayer().getNickname());
        ((ImageView) thisPlayerPane.getChildren().get(2)).setImage(getWizardIcon(thisPlayerBoard.getPlayer().getSelectedWizard()));

        //set discard pile assistant
        try {
            ((ImageView) thisPlayerPane.getChildren().get(3)).setImage(new Image(getClass().getResourceAsStream("/img/assistants/" + thisPlayerBoard.getPlayer().getDiscardPile().name().toLowerCase() + ".png")));
        } catch (NullPointerException e) {
            ((ImageView) thisPlayerPane.getChildren().get(3)).setImage(blankImg());
        }

        //set assistants' cards
        setAssistants(thisPlayerBoard.getPlayer().getCards(), new ArrayList<>());

        //set entrance
        for (int i = 0; i < thisPlayerBoard.getEntrance().length; i++) {
            ((ImageView)((AnchorPane) thisPlayerPane.getChildren().get(4)).getChildren().get(i)).setImage(displayStudent(thisPlayerBoard.getEntrance()[i]));
        }

        //set towers left
        for (int i = 0; i < (-2 * board.getPlayers().size() + 12); i++) {
            ((ImageView)((AnchorPane) thisPlayerPane.getChildren().get(5)).getChildren().get(i)).setImage(getTowerIcon(thisPlayerBoard.getPlayer().getTowerColor()));
            if(i >= thisPlayerBoard.getTowersLeft())
                ((AnchorPane) thisPlayerPane.getChildren().get(5)).getChildren().get(i).setVisible(false);
        }

        //set professor table
        for (int i = 0; i < thisPlayerBoard.getProfessorTable().length; i++) {
            ((VBox) thisPlayerPane.getChildren().get(6)).getChildren().get(i).setVisible(thisPlayerBoard.getProfessorTable()[i]);
        }

        //set coins
        if(board.isExpertMode()) {
            ((Label)((AnchorPane)thisPlayerPane.getChildren().get(7)).getChildren().get(1)).setText("x" + thisPlayerBoard.getPlayer().getCoins());
        } else {
            thisPlayerPane.getChildren().get(7).setVisible(false);
        }

        //set dining room students
        for (int i = 0; i < thisPlayerBoard.getDiningRoom().length; i++) {
            for (int j = 0; j < 10; j++) {
                ((HBox)thisPlayerDR.getChildren().get(i)).getChildren().get(j).setVisible(j < thisPlayerBoard.getDiningRoom()[i]);
            }
        }

    }

    /**
     * Returns the icon of the tower corresponding to the given tower color.
     *
     * @param towerColor          a TowerColor.
     * @return                    the corresponding Image.
     */
    private Image getTowerIcon(TowerColor towerColor) {
        switch (towerColor) {
            case GREY:
                return new Image(getClass().getResourceAsStream("/img/towers/upgrey.png"));
            case BLACK:
                return new Image(getClass().getResourceAsStream("/img/towers/upblack.png"));
            case WHITE:
                return new Image(getClass().getResourceAsStream("/img/towers/upwhite.png"));
            default:
                return blankImg();
        }
    }

    /**
     * Returns the icon corresponding to the given wizard.
     *
     * @param wizard              a Wizard.
     * @return                    the corresponding Image.
     */
    private Image getWizardIcon(Wizard wizard) {
        switch (wizard) {
            case SKYWIZARD:
                return new Image(getClass().getResourceAsStream("/img/wizards/s_icon.png"));
            case ARTICWIZARD:
                return new Image(getClass().getResourceAsStream("/img/wizards/a_icon.png"));
            case DESERTWIZARD:
                return new Image(getClass().getResourceAsStream("/img/wizards/d_icon.png"));
            case FORESTWIZARD:
                return new Image(getClass().getResourceAsStream("/img/wizards/f_icon.png"));
            default:
                return blankImg();
        }
    }

    /**
     * Initializes the archipelagos to display, with (if present) towers,
     * Mother Nature, No Entry Tiles and students.
     *
     * @param archipelagos        a LightArchi List.
     */
    public void initializeArchipelagos(List<LightArchi> archipelagos) {
        int i;

        for (i = 0; i < archipelagos.size(); i++) {

            //set ID
            archipelagosBox.getChildren().get(i).setId("archi0" + i);

            //set tower
            try {
                ((ImageView) ((AnchorPane) archipelagosBox.getChildren().get(i)).getChildren().get(1)).setImage(new Image(getClass().getResourceAsStream("/img/towers/" + archipelagos.get(i).getTowerColor().name().toLowerCase() + ".png")));
            } catch (NullPointerException e) {
                ((ImageView) ((AnchorPane) archipelagosBox.getChildren().get(i)).getChildren().get(1)).setImage(blankImg());
            }

            //set mother nature
            ((AnchorPane) archipelagosBox.getChildren().get(i)).getChildren().get(2).setVisible(archipelagos.get(i).isMNPresent());

            //set no entry tiles
            if (archipelagos.get(i).getNoEntryTiles() >= 1) {
                ((AnchorPane) archipelagosBox.getChildren().get(i)).getChildren().get(8).setVisible(true);

                if (archipelagos.get(i).getNoEntryTiles() == 1) {
                    ((AnchorPane) archipelagosBox.getChildren().get(i)).getChildren().get(9).setVisible(false);
                } else if (archipelagos.get(i).getNoEntryTiles() > 1) {
                    ((Label) ((AnchorPane) archipelagosBox.getChildren().get(i)).getChildren().get(9)).setText(Integer.toString(archipelagos.get(i).getNoEntryTiles()));
                    ((AnchorPane) archipelagosBox.getChildren().get(i)).getChildren().get(9).setVisible(true);
                }
            } else {
                ((AnchorPane) archipelagosBox.getChildren().get(i)).getChildren().get(8).setVisible(false);
                ((AnchorPane) archipelagosBox.getChildren().get(i)).getChildren().get(9).setVisible(false);
            }

            //set students number and their visibility
            for (int j = 0; j < 5; j++) {
                ((Label)((AnchorPane)((AnchorPane)archipelagosBox.getChildren().get(i)).getChildren().get(j+3)).getChildren().get(1)).setText(Integer.toString(MatrixOperations.columnSum(archipelagos.get(i).getIslands(), j)));

                if (((Label)((AnchorPane)((AnchorPane)archipelagosBox.getChildren().get(i)).getChildren().get(j+3)).getChildren().get(1)).getText().equals("0"))
                    ((AnchorPane)archipelagosBox.getChildren().get(i)).getChildren().get(j+3).setVisible(false);
                else
                    ((AnchorPane)archipelagosBox.getChildren().get(i)).getChildren().get(j+3).setVisible(true);
            }

            //set the archipelago size if it's > 1
            if (archipelagos.get(i).getSize() > 1) {
                ((AnchorPane) archipelagosBox.getChildren().get(i)).getChildren().get(10).setVisible(true);
                ((Label) ((AnchorPane) archipelagosBox.getChildren().get(i)).getChildren().get(10)).setText("x" + archipelagos.get(i).getSize());
            } else {
                ((AnchorPane) archipelagosBox.getChildren().get(i)).getChildren().get(10).setVisible(false);
            }

        }

        //complete the archipelago circle, hiding the archis that are now merged into others
        while (i <= 11) {
            archipelagosBox.getChildren().get(i).setDisable(true);
            archipelagosBox.getChildren().get(i).setVisible(false);
            i++;
        }
    }

    /**
     * Initializes the clouds for this game.
     */
    public void initializeClouds() {
        int cloudsNumber = board.getCloudsNumber();
        if (cloudsNumber == 2 && cloudsBox.getChildren().size() == 3) {
            cloudsBox.getChildren().remove(cloudsBox.getChildren().get(2));
        }

        for (int i = 0; i < cloudsNumber; i++) {
            for (int j = 0; j < cloudsNumber+1; j++) {
                ((ImageView) ((AnchorPane) cloudsBox.getChildren().get(i)).getChildren().get(j+1)).setImage(displayStudent(board.getCloud(i)[j]));
                cloudsBox.getChildren().get(i).setId("cloud" + i);
            }
        }
    }

    /**
     * Initializes the coins supply to display.
     *
     * @param coinsSupply         the number of coins in the supply.
     */
    public void initializeCoinsSupply(int coinsSupply) {
        if (board.isExpertMode()) {
            ((Label) coinsSupplyBox.getChildren().get(1)).setText("x" + coinsSupply);
        } else {
            coinsSupplyBox.setVisible(false);
        }
    }

    /**
     * Initializes the school boards of the other player(s) to display to this
     * client.
     */
    public void initializeOtherPlayers() {
        LightSchoolBoard[] otherPlayers = new LightSchoolBoard[board.getSchoolBoards().size()-1];

        //disable tab if there are only 2 players
        if (board.getSchoolBoards().size() == 2 && otherPlayersPane.getTabs().size() == 2) {
            otherPlayersPane.getTabs().remove(1);
        }

        //create an array of enemy players
        for (int i = 0, j = 0; i < board.getSchoolBoards().size(); i++) {
            if (!board.getSchoolBoards().get(i).getPlayer().getNickname().equals(gui.getClient().getNickname())) {
                otherPlayers[j] = board.getSchoolBoards().get(i);
                j++;
            }
        }

        //set the otherPlayers schoolboard
        for (int i = 0; i < otherPlayers.length; i++) {
            otherPlayersPane.getTabs().get(i).setText(otherPlayers[i].getPlayer().getNickname());

            //set nickname
            ((Label)((AnchorPane)otherPlayersPane.getTabs().get(i).getContent()).getChildren().get(1)).setText(otherPlayers[i].getPlayer().getNickname());
            //set chosen wizard
            ((ImageView)((AnchorPane)otherPlayersPane.getTabs().get(i).getContent()).getChildren().get(2)).setImage(getWizardIcon(otherPlayers[i].getPlayer().getSelectedWizard()));

            //set discard pile, if present
            try {
                ((ImageView)((AnchorPane)otherPlayersPane.getTabs().get(i).getContent()).getChildren().get(3)).setImage(new Image(getClass().getResourceAsStream("/img/assistants/" + otherPlayers[i].getPlayer().getDiscardPile().name().toLowerCase() + ".png")));
            } catch (NullPointerException e){
                ((ImageView)((AnchorPane)otherPlayersPane.getTabs().get(i).getContent()).getChildren().get(3)).setImage(blankImg());
            }

            //set entrance
            for (int j = 0; j < otherPlayers[i].getEntrance().length; j++) {
                ((ImageView)((AnchorPane)((AnchorPane)otherPlayersPane.getTabs().get(i).getContent()).getChildren().get(5)).getChildren().get(j)).setImage(displayStudent(otherPlayers[i].getEntrance()[j]));
            }

            //set towers on the schoolboard
            for (int j = 0; j < (-2 * board.getPlayers().size() + 12); j++) {
                ((ImageView)((AnchorPane)((AnchorPane)otherPlayersPane.getTabs().get(i).getContent()).getChildren().get(4)).getChildren().get(j)).setImage(getTowerIcon(otherPlayers[i].getPlayer().getTowerColor()));
                if(j >= otherPlayers[i].getTowersLeft())
                    ((AnchorPane)((AnchorPane)otherPlayersPane.getTabs().get(i).getContent()).getChildren().get(4)).getChildren().get(j).setVisible(false);
            }

            //set professor table
            for (int j = 0; j < otherPlayers[i].getProfessorTable().length; j++) {
                ((VBox)((AnchorPane)otherPlayersPane.getTabs().get(i).getContent()).getChildren().get(6)).getChildren().get(j).setVisible(otherPlayers[i].getProfessorTable()[j]);
            }

            //set coins if it's expert mode
            if (board.isExpertMode()) {
                ((Label) ((AnchorPane) ((AnchorPane) otherPlayersPane.getTabs().get(i).getContent()).getChildren().get(7)).getChildren().get(1)).setText("x" + otherPlayers[i].getPlayer().getCoins());
            } else {
                ((AnchorPane)otherPlayersPane.getTabs().get(i).getContent()).getChildren().get(7).setVisible(false);
            }

            //set dining room students
            for (int j = 0; j < otherPlayers[i].getDiningRoom().length; j++) {
                for (int k = 0; k < 10; k++) {
                    ((HBox)((AnchorPane)((AnchorPane)otherPlayersPane.getTabs().get(i).getContent()).getChildren().get(8)).getChildren().get(j)).getChildren().get(k).setVisible(k < otherPlayers[i].getDiningRoom()[j]);
                }
            }
        }
    }

    /**
     * Returns the icon corresponding to the color of the given student.
     *
     * @param student             a Student.
     * @return                    the corresponding Image.
     */
    private Image displayStudent(Student student) {
        try {
            switch (student.getColor()) {
                case RED:
                    return new Image(getClass().getResourceAsStream("/img/pawns/studentred.png"));
                case BLUE:
                    return new Image(getClass().getResourceAsStream("/img/pawns/studentblue.png"));
                case YELLOW:
                    return new Image(getClass().getResourceAsStream("/img/pawns/studentyellow.png"));
                case GREEN:
                    return new Image(getClass().getResourceAsStream("/img/pawns/studentgreen.png"));
                case PINK:
                    return new Image(getClass().getResourceAsStream("/img/pawns/studentpink.png"));
                default:
                    return null;
            }
        } catch (NullPointerException e) {
            return blankImg();
        }
    }

    /**
     * Sets the assistant cards from which the user can choose.
     *
     * @param availableAssistants the Assistant List of available Assistants.
     * @param discardedAssistants the Assistant List of discarded Assistants.
     */
    public void setAssistants(List<Assistant> availableAssistants, List<Assistant> discardedAssistants) {
        int i;
        for (i = 0; i < availableAssistants.size(); i++) {
            ((ImageView) assistantBox.getChildren().get(i)).setImage(new Image(getClass().getResourceAsStream("/img/assistants/" + availableAssistants.get(i).name().toLowerCase() + ".png")));

            assistantBox.getChildren().get(i).setId(availableAssistants.get(i).name().toLowerCase());
            assistantBox.getChildren().get(i).setDisable(availableAssistants.size() > 1 && discardedAssistants.contains(availableAssistants.get(i)));
        }
        while (i != 10) {
            assistantBox.getChildren().get(i).setDisable(true);
            assistantBox.getChildren().get(i).setVisible(false);
            i++;
        }
    }

    /**
     * Enables the assistant box.
     */
    public void enableAssistantBox() {
        assistantBox.setDisable(false);
    }

    /**
     * Enables the characters box.
     */
    public void enableCharactersBox() {
        charactersBox.setDisable(false);
        for (int i = 0; i < 3; i++)
            charactersBox.getChildren().get(i).setDisable(false);

        //check if the player can buy the character
        for (int i = 0; i < 3; i++) {
            if (board.getCurrentPlayerSchoolBoard().getPlayer().getCoins() < board.getSelectedCharacters()[i].getCost())
                charactersBox.getChildren().get(i).setDisable(true);

            //check if granny grass has at least 1 NoEntryTile
            if (board.getSelectedCharacters()[i].getName().equals("GrannyGrass"))
                if (board.getSelectedCharacters()[i].getNoEntryTiles() == 0)
                    charactersBox.getChildren().get(i).setDisable(true);
        }
    }

    /**
     * Enables the archipelagos.
     */
    public void enableArchipelagos() {
        archipelagosBox.setDisable(false);

        for (int i = 0; i < board.getArchipelagos().size(); i++)
            archipelagosBox.getChildren().get(i).setDisable(false);
    }

    /**
     * Enables the cloud box.
     */
    public void enableCloudBox() {
        cloudsBox.setDisable(false);

        for (int i = 0; i < board.getCloudsNumber(); i++)
            cloudsBox.getChildren().get(i).setDisable(false);

        for (int i = 0; i < board.getCloudsNumber(); i++)
            if (board.getCloud(i)[0] == null)
                cloudsBox.getChildren().get(i).setDisable(true);
    }

    /**
     * Gets the assistant chosen by the user and sends it to the server.
     *
     * @param event               a MouseEvent.
     */
    @FXML
    private void chooseAssistant(MouseEvent event) {
        if (event.getButton().equals(MouseButton.PRIMARY)) {
            String selectedAssistant = event.getPickResult().getIntersectedNode().getId();
            gui.getClient().sendMsgToServer(new ChooseAssistantReply(getAssistantByName(selectedAssistant)));
            assistantBox.setDisable(true);
        }
    }

    /**
     * Gets the cloud chosen by the user and sends it to the server.
     *
     * @param event               a MouseEvent.
     */
    @FXML
    private void chooseCloud(MouseEvent event) {
        if (event.getButton().equals(MouseButton.PRIMARY)) {
            int cloudIndex ;
            String cloudId = event.getPickResult().getIntersectedNode().getParent().getId();

            if (cloudId == null)
                cloudId = event.getPickResult().getIntersectedNode().getParent().getParent().getId();

            cloudIndex = Character.getNumericValue(cloudId.charAt(cloudId.length() - 1));

            if (board.getCloud(cloudIndex)[0] == null) {
                gui.warningDialog("Cannot choose this cloud since it's empty. Try again.");
                return;
            }

            gui.getClient().sendMsgToServer(new CloudReply(cloudIndex));
            cloudsBox.setDisable(true);
        }
    }

    public void enableArchisForMN(int maxMNSteps) {
        int currentMNIndex = findCurrentMNIndex(board.getArchipelagos());
        int nArchis = board.getArchipelagos().size();
        archipelagosBox.setDisable(false);

        for (int i = 0; i < nArchis; i++)
            archipelagosBox.getChildren().get(i).setDisable(true);

        for (int i = 0; i < maxMNSteps; i++)
            archipelagosBox.getChildren().get((currentMNIndex+i+1) % nArchis).setDisable(false);
    }

    @FXML
    private void chooseArchipelago(MouseEvent event) {

        if (event.getButton().equals(MouseButton.PRIMARY)) {
            String archiId = event.getPickResult().getIntersectedNode().getId();

            if (archiId == null) {
                archiId = event.getPickResult().getIntersectedNode().getParent().getId();
                if (archiId == null) {
                    archiId = event.getPickResult().getIntersectedNode().getParent().getParent().getId();
                    if (archiId == null)
                        archiId = event.getPickResult().getIntersectedNode().getParent().getParent().getId();
                }
            }
            archiIndex = Integer.parseInt(archiId.substring(archiId.length() - 2));

            if (gui.getClient().isMovingStud()) { //if I'm moving student on archis
                gui.getClient().sendMsgToServer(new PlaceReply("ARCHIPELAGO", archiIndex));

            } else if (gui.getClient().isMovingMN()) { //if I'm moving mother nature
                int currentMNIndex = findCurrentMNIndex(board.getArchipelagos());
                gui.getClient().sendMsgToServer(new MNStepsReply( ((archiIndex - currentMNIndex) + board.getArchipelagos().size()) % board.getArchipelagos().size()) );

            } else if (gui.getClient().isChoosingChar()) { //if I'm choosing archis to give it to characters' effect
                gui.getClient().sendMsgToServer(new CharacterReply(selectedCharacter, archiIndex, studentNumber, studColors));
            }

            archipelagosBox.setDisable(true);
            gui.getClient().setMovingStud(false);
            gui.getClient().setMovingMN(false);
            gui.getClient().setChoosingChar(false);
        }
    }

    private int findCurrentMNIndex(List<LightArchi> archipelagos) {
        for(LightArchi a : archipelagos)
            if (a.isMNPresent())
                return archipelagos.indexOf(a);

        return -1; //function cannot reach this point
    }

    /**
     * Returns an assistant given their name.
     *
     * @param name                the name of an Assistant.
     * @return                    the Assistant.
     */
    private Assistant getAssistantByName(String name) {
        switch (name.toLowerCase()) {
            case "dog":
                return Assistant.DOG;
            case "cat":
                return Assistant.CAT;
            case "ostrich":
                return Assistant.OSTRICH;
            case "eagle":
                return Assistant.EAGLE;
            case "elephant":
                return Assistant.ELEPHANT;
            case "turtle":
                return Assistant.TURTLE;
            case "cheetah":
                return Assistant.CHEETAH;
            case "lizard":
                return Assistant.LIZARD;
            case "octopus":
                return Assistant.OCTOPUS;
            case "fox":
                return Assistant.FOX;
            default:
                gui.warningDialog("An error occured choosing the assistant");
                return null;
        }
    }

    /**
     * Gets the character chosen by the user and sends it to the server.
     *
     * @param event               a MouseEvent.
     */
    @FXML
    private void chooseCharacter(MouseEvent event) {

        if (event.getButton().equals(MouseButton.PRIMARY)) {
            String characterName;
            archiIndex = -1;
            studentNumber = 0;
            studColors = null;

             characterName = event.getPickResult().getIntersectedNode().getParent().getId();
             if (characterName == null)
                 characterName = event.getPickResult().getIntersectedNode().getParent().getParent().getId();

            if(board.getSelectedCharacters()[getCharIndexByName(characterName)].getCost() > board.getCurrentPlayerSchoolBoard().getPlayer().getCoins()) {
                gui.warningDialog("Cannot choose this character card since you do not have enough coins. Try again.");
                return;
            } else if (characterName.equalsIgnoreCase("minstrel") && Arrays.stream(board.getCurrentPlayerSchoolBoard().getDiningRoom()).allMatch(t -> t == 0)) {
                gui.warningDialog("Cannot choose Minstrel card since you do not have any students in your dining room. Try again.");
                return;
            }

            String finalCharacterName = characterName;
            Task<Void> task = new Task<>() {
                @Override public Void call() {

                    selectedCharacter = board.getSelectedCharacters()[getCharIndexByName(finalCharacterName)];
                    List<Color> availableColors;

                    //switch case of all characters to ask the proper values
                    //characters that need an archiIndex do not send the message here, but chooseArchipelago() does it for them
                    switch (selectedCharacter.getName()) {
                        case "Monk":
                            gui.charInfoDialog("Monk effect activated!");
                            studentNumber = 1;
                            studColors = new Color[studentNumber*2];
                            gui.askColor(DataChores.getColorsByStudents(selectedCharacter.getStudents()), "Select the student you would like to take from the character card");
                            studColors[studentNumber-1] = gui.getStudColor();

                            gui.charInfoDialog("Select the island where you would like to move the student on");
                            gui.enableArchiBox();
                            break;

                        case "Farmer":
                            gui.charInfoDialog("Farmer effect activated! You take control of any professor if there's a tie between players' students in the dining rooms.");
                            gui.getClient().sendMsgToServer(new CharacterReply(selectedCharacter, archiIndex, studentNumber, studColors));
                            break;

                        case "Herald":
                            gui.charInfoDialog("Herald effect activated! Select the island you would like to be resolved");
                            gui.enableArchiBox();
                            break;

                        case "MagicMailman":
                            gui.charInfoDialog("Magic Mailman effect activated! You may now move mother nature up to 2 additional islands");
                            gui.getClient().sendMsgToServer(new CharacterReply(selectedCharacter, archiIndex, studentNumber, studColors));
                            break;

                        case "GrannyGrass":
                            gui.charInfoDialog("Granny Grass effect activated! Select the island where you would like to put the No Entry Tile on");
                            gui.enableArchiBox();
                            break;

                        case "Centaur":
                            gui.charInfoDialog("Centaur effect activated! Select the island to cancel its towers influence");
                            gui.enableArchiBox();
                            break;

                        case "Jester":
                            gui.charInfoDialog("Jester effect activated!");
                            studentNumber = studNumberDialog(3, "Select the number of students you would like to swap from the character card [up to 3]");
                            studColors = new Color[studentNumber*2];
                            availableColors = DataChores.getColorsByStudents(selectedCharacter.getStudents());

                            for (int i = 0; i < studentNumber; i++) {
                                gui.askColor(availableColors, "Select the student you would like to take from the character card. " + (studentNumber-i) + " student(s) left");
                                studColors[i] = gui.getStudColor();
                                DataChores.checkColorNumber(selectedCharacter.getStudents(), studColors, i, availableColors);
                            }
                            availableColors = DataChores.getColorsByStudents(board.getCurrentPlayerSchoolBoard().getEntrance());
                            askEntranceStudents(board, studentNumber, studColors, availableColors);
                            gui.getClient().sendMsgToServer(new CharacterReply(selectedCharacter, archiIndex, studentNumber, studColors));

                            break;

                        case "Knight":
                            gui.charInfoDialog("Knight effect activated! You now have 2 more points of influence on islands during this turn.");
                            gui.getClient().sendMsgToServer(new CharacterReply(selectedCharacter, archiIndex, studentNumber, studColors));
                            break;

                        case "MushroomMan":
                            gui.charInfoDialog("Mushroom Man effect activated!");
                            studentNumber = 1;
                            studColors = new Color[studentNumber*2];
                            gui.askColor(new ArrayList<>(Arrays.asList(Color.values())), "Select a color. During this turn, this color adds no influence.");
                            studColors[studentNumber-1] = gui.getStudColor();
                            gui.getClient().sendMsgToServer(new CharacterReply(selectedCharacter, archiIndex, studentNumber, studColors));
                            break;

                        case "Minstrel":
                            gui.charInfoDialog("Minstrel effect activated!");

                            studentNumber = studNumberDialog(2, "Select the number of students you would like to swap from the dining room [up to 2]");

                            while (studentNumber > Arrays.stream(board.getCurrentPlayerSchoolBoard().getDiningRoom()).sum()) {
                                Platform.runLater(() -> {
                                    gui.warningDialog("Invalid student number. In the dining room there is only 1 student. Try again and select '1'");
                                    synchronized (gui.getLock()) {
                                        gui.getLock().notify();
                                    }
                                });
                                synchronized (gui.getLock()) {
                                    try {
                                        gui.getLock().wait();
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                }

                                studentNumber = studNumberDialog(2, "Select the number of students you would like to swap from the dining room [up to 2]");
                            }
                            studColors = new Color[studentNumber*2];
                            availableColors = DataChores.getColorsByDR(board.getCurrentPlayerSchoolBoard().getDiningRoom());

                            for (int i = 0; i < studentNumber; i++) {
                                gui.askColor(availableColors, "Select the student you would like to take from the dining room. " + (studentNumber-i) + " student(s) left.");
                                studColors[i] = gui.getStudColor();
                                DataChores.checkColorNumberDR(board.getCurrentPlayerSchoolBoard().getDiningRoom(), studColors, i, availableColors);
                            }

                            availableColors = DataChores.getColorsByStudents(board.getCurrentPlayerSchoolBoard().getEntrance());
                            askEntranceStudents(board, studentNumber, studColors, availableColors);
                            gui.getClient().sendMsgToServer(new CharacterReply(selectedCharacter, archiIndex, studentNumber, studColors));

                            break;

                        case "SpoiledPrincess":
                            gui.charInfoDialog("Spoiled Princess effect activated!");
                            studentNumber = 1;
                            studColors = new Color[studentNumber*2];
                            gui.askColor(DataChores.getColorsByStudents(selectedCharacter.getStudents()), "Select the student you would like to take from the character card and place in your dining room");
                            studColors[studentNumber-1] = gui.getStudColor();
                            gui.getClient().sendMsgToServer(new CharacterReply(selectedCharacter, archiIndex, studentNumber, studColors));
                            break;

                        case "Thief":
                            gui.charInfoDialog("Thief effect activated!");
                            studentNumber = 1;
                            studColors = new Color[studentNumber*2];
                            gui.askColor(new ArrayList<>(Arrays.asList(Color.values())), "Select a color. Every player, including yourself, will return 3 students of that color from the dining room to the bag.");
                            studColors[studentNumber-1] = gui.getStudColor();
                            gui.getClient().sendMsgToServer(new CharacterReply(selectedCharacter, archiIndex, studentNumber, studColors));
                            break;

                        default:
                            break;
                    }

                    return null;
                }
            };

            new Thread(task).start();
            charactersBox.setDisable(true);
        }
    }

    private int getCharIndexByName(String characterName) {
        for (int i = 0; i < board.getSelectedCharacters().length; i++)
            if (characterName.equalsIgnoreCase(board.getSelectedCharacters()[i].getName()))
                return i;

        return -1;  //function cannot reach this point
    }

    private int studNumberDialog(int maxStudNumber, String text) {
        AtomicInteger selectedStudNumber = new AtomicInteger();

        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Student number selection");
            alert.setHeaderText("Press the right button");
            alert.setContentText(text);
            ((Stage) alert.getDialogPane().getScene().getWindow()).getIcons().add(new Image("/img/icon.png"));
            alert.getDialogPane().getScene().getWindow().setOnCloseRequest(event -> {
                gui.warningDialog("You have to choose the number first in order to close this panel");
                event.consume();
            });

            ButtonType button1 = new ButtonType("1");
            ButtonType button2 = new ButtonType("2");
            ButtonType button3 = new ButtonType("3");

            if (maxStudNumber == 2) {
                alert.getButtonTypes().setAll(button1, button2);
            } else
                alert.getButtonTypes().setAll(button1, button2, button3);

            Optional<ButtonType> result = alert.showAndWait();

            if (result.get() == button1) {
                selectedStudNumber.set(1);
            } else if (result.get() == button2) {
                selectedStudNumber.set(2);
            } else if (result.get() == button3) {
                selectedStudNumber.set(3);
            } else {
                gui.warningDialog("An error occurred.");
                selectedStudNumber.set(-1);
            }

            synchronized (gui.getLock()) {
                gui.getLock().notify();
            }

        });

        synchronized (gui.getLock()) {
            try {
                gui.getLock().wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return selectedStudNumber.get();
    }

    /**
     * Asks the user which students they want to swap from their entrance.
     * Used in {@link GUI#askCharacter(LightBoard) askCharacter method} in order to
     * activate {@link it.polimi.ingsw.model.effects.JesterEffect JesterEffect} and
     * {@link it.polimi.ingsw.model.effects.MinstrelEffect MinstrelEffect}.
     *
     * @param board                the LightBoard to access the entrance of the current Player.
     * @param studentNumber        the number of Students the user can move.
     * @param studColors           the Color Array of Students chosen by the user.
     * @param availableColors      the Color List of Students present in the entrance.
     */
    private void askEntranceStudents(LightBoard board, int studentNumber, Color[] studColors, List<Color> availableColors) {
        for (int i = 0; i < studentNumber; i++) {
            gui.askColor(availableColors, "Select now the student(s) you would like to swap from your entrance. " + (studentNumber-i) + " student(s) left.");
            studColors[studentNumber+i] = gui.getStudColor();
            DataChores.checkColorNumber(board.getCurrentPlayerSchoolBoard().getEntrance(), studColors, i, availableColors);
        }
    }

    /**
     * Returns a blank image.
     *
     * @return                    a blank image used as placeholder.
     */
    private Image blankImg() {
        return new Image(getClass().getResourceAsStream("/img/blank.png"));
    }

    /**
     * Sets the light board.
     *
     * @param board          a LightBoard.
     */
    public void setBoard(LightBoard board) {
        this.board = board;
    }

    /**
     * Sets the GUI.
     *
     * @param gui                 a GUI.
     */
    @Override
    public void setGUI(GUI gui) {
        this.gui = gui;
    }
}
