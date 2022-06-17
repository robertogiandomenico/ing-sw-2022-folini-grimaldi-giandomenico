package it.polimi.ingsw.view.gui.scenes;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.network.messages.clientMessages.CharacterReply;
import it.polimi.ingsw.network.messages.clientMessages.ChooseAssistantReply;
import it.polimi.ingsw.network.messages.clientMessages.CloudReply;
import it.polimi.ingsw.network.messages.clientMessages.MNStepsReply;
import it.polimi.ingsw.view.cli.CLI;
import it.polimi.ingsw.view.gui.GUI;
import it.polimi.ingsw.view.utilities.DataChores;
import it.polimi.ingsw.view.utilities.MatrixOperations;
import it.polimi.ingsw.view.utilities.lightclasses.LightArchi;
import it.polimi.ingsw.view.utilities.lightclasses.LightBoard;
import it.polimi.ingsw.view.utilities.lightclasses.LightCharacter;
import it.polimi.ingsw.view.utilities.lightclasses.LightSchoolBoard;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
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
        thisPlayerPane.setDisable(true);
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
                        Label noEntryTileLeft = new Label();

                        noEntryTileLeft.setLayoutX(41);
                        noEntryTileLeft.setLayoutY(107);
                        noEntryTileLeft.setText("x" + selectedCharacters[i].getNoEntryTiles());
                        noEntryTileLeft.setStyle("-fx-font-family: \"Metamorphous\"");

                        ((AnchorPane)charactersBox.getChildren().get(i)).getChildren().add(noEntryTileLeft);
                        break;

                    case "Monk":

                    case "SpoiledPrincess":

                    case "Jester":
                        for (int j = 0; j < selectedCharacters[i].getStudents().length; j++) {
                            ((ImageView)((AnchorPane)((AnchorPane)charactersBox.getChildren().get(i)).getChildren().get(1)).getChildren().get(j)).setImage(displayStudent(selectedCharacters[i].getStudents()[j]));
                        }
                        break;

                    default:
                        break;
                }

                //set coin image if the character has been already used
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

        //set available assistants
        try {
            ((ImageView) thisPlayerPane.getChildren().get(3)).setImage(new Image(getClass().getResourceAsStream("/img/assistant/" + thisPlayerBoard.getPlayer().getDiscardPile().name().toLowerCase() + ".png")));
        } catch (NullPointerException e) {
            ((ImageView) thisPlayerPane.getChildren().get(3)).setImage(blankImg());
        }

        //set entrance
        for (int i = 0; i < thisPlayerBoard.getEntrance().length; i++) {
            ((ImageView)((AnchorPane) thisPlayerPane.getChildren().get(4)).getChildren().get(i)).setImage(displayStudent(thisPlayerBoard.getEntrance()[i]));
        }

        //set towers left
        for (int i = 0; i < thisPlayerBoard.getTowersLeft(); i++) {
            ((ImageView)((AnchorPane) thisPlayerPane.getChildren().get(5)).getChildren().get(i)).setImage(getTowerIcon(thisPlayerBoard.getPlayer().getTowerColor()));
        }

        //set professor table
        for (int i = 0; i < thisPlayerBoard.getProfessorTable().length; i++) {
            ((VBox) thisPlayerPane.getChildren().get(6)).getChildren().get(i).setVisible(thisPlayerBoard.getProfessorTable()[i]);
        }

        //set coins
        if(board.isExpertMode()) {
            ((Label)((AnchorPane)thisPlayerPane.getChildren().get(7)).getChildren().get(1)).setText("x" + thisPlayerBoard.getPlayer().getCoins());
        } else {
            thisPlayerPane.getChildren().get(7).setDisable(true);
            thisPlayerPane.getChildren().get(7).setVisible(false);
        }

        //set dining room students
        for (int i = 0; i < thisPlayerBoard.getDiningRoom().length; i++) {
            for (int j = 9; j >= thisPlayerBoard.getDiningRoom()[i]; j--) {
                ((HBox)thisPlayerDR.getChildren().get(i)).getChildren().get(j).setVisible(false);
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

            //set no entry tile
            ((AnchorPane) archipelagosBox.getChildren().get(i)).getChildren().get(8).setVisible(archipelagos.get(i).isNoEntryTilePresent());

            //set students number and visibility
            for (int j = 0; j < 5; j++) {
                ((Label)((AnchorPane)((AnchorPane)archipelagosBox.getChildren().get(i)).getChildren().get(j+3)).getChildren().get(1)).setText(Integer.toString(MatrixOperations.columnSum(archipelagos.get(i).getIslands(), j)));

                if (((Label)((AnchorPane)((AnchorPane)archipelagosBox.getChildren().get(i)).getChildren().get(j+3)).getChildren().get(1)).getText().equals("0"))
                    ((AnchorPane)archipelagosBox.getChildren().get(i)).getChildren().get(j+3).setVisible(false);
                else
                    ((AnchorPane)archipelagosBox.getChildren().get(i)).getChildren().get(j+3).setVisible(true);
            }

            //set the archipelago size
            ((Label)((AnchorPane)archipelagosBox.getChildren().get(i)).getChildren().get(9)).setText("x" + archipelagos.get(i).getSize());

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
        if (cloudsNumber == 2) {
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
            coinsSupplyBox.setDisable(true);
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
        if (board.getSchoolBoards().size() == 2) {
            otherPlayersPane.getTabs().get(1).setDisable(true);
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
                ((ImageView)((AnchorPane)otherPlayersPane.getTabs().get(i).getContent()).getChildren().get(3)).setImage(new Image(getClass().getResourceAsStream("/img/assistant/" + otherPlayers[i].getPlayer().getDiscardPile().name().toLowerCase() + ".png")));
            } catch (NullPointerException e){
                ((ImageView)((AnchorPane)otherPlayersPane.getTabs().get(i).getContent()).getChildren().get(3)).setImage(blankImg());
            }

            //set entrance
            for (int j = 0; j < otherPlayers[i].getEntrance().length; j++) {
                ((ImageView)((AnchorPane)((AnchorPane)otherPlayersPane.getTabs().get(i).getContent()).getChildren().get(5)).getChildren().get(j)).setImage(displayStudent(otherPlayers[i].getEntrance()[j]));
            }

            //set towers on the schoolboard
            for (int j = 0; j < otherPlayers[i].getTowersLeft(); j++) {
                ((ImageView)((AnchorPane)((AnchorPane)otherPlayersPane.getTabs().get(i).getContent()).getChildren().get(4)).getChildren().get(j)).setImage(getTowerIcon(otherPlayers[i].getPlayer().getTowerColor()));
            }

            //set professor table
            for (int j = 0; j < otherPlayers[i].getProfessorTable().length; j++) {
                ((VBox)((AnchorPane)otherPlayersPane.getTabs().get(i).getContent()).getChildren().get(6)).getChildren().get(j).setVisible(otherPlayers[i].getProfessorTable()[j]);
            }

            //set coins if it's expert mode
            if (board.isExpertMode()) {
                ((Label) ((AnchorPane) ((AnchorPane) otherPlayersPane.getTabs().get(i).getContent()).getChildren().get(7)).getChildren().get(1)).setText("x" + otherPlayers[i].getPlayer().getCoins());
            } else {
                ((AnchorPane)otherPlayersPane.getTabs().get(i).getContent()).getChildren().get(7).setDisable(true);
                ((AnchorPane)otherPlayersPane.getTabs().get(i).getContent()).getChildren().get(7).setVisible(false);
            }

            //set dining room students
            for (int j = 0; j < otherPlayers[i].getDiningRoom().length; j++) {
                for (int k = 9; k >= otherPlayers[i].getDiningRoom()[j]; k--) {
                    ((HBox)((AnchorPane)((AnchorPane)otherPlayersPane.getTabs().get(i).getContent()).getChildren().get(8)).getChildren().get(j)).getChildren().get(k).setVisible(false);
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
            assistantBox.getChildren().get(i).setDisable(discardedAssistants.contains(availableAssistants.get(i)));
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
    }

    /**
     * Enables the school board.
     */
    public void enableSchoolBoard() {
        thisPlayerPane.setDisable(false);
    }

    /**
     * Enables the archipelagos.
     */
    public void enableArchipelagos() {
        archipelagosBox.setDisable(false);
    }

    /**
     * Enables the cloud box.
     */
    public void enableCloudBox() {
        cloudsBox.setDisable(false);
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
            String cloudId = event.getPickResult().getIntersectedNode().getId();
            int cloudIndex = Character.getNumericValue(cloudId.charAt(cloudId.length() - 1));
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
            boolean movingMN = gui.getClient().isMovingMN();
            String archiId = event.getPickResult().getIntersectedNode().getId();
            int archiIndex = Integer.parseInt(archiId.substring(archiId.length() - 2));

            if (movingMN) { //if I'm moving mother nature
                int currentMNIndex = findCurrentMNIndex(board.getArchipelagos());
                gui.getClient().sendMsgToServer(new MNStepsReply(archiIndex - currentMNIndex));
                gui.getClient().setMovingMN(false);
                archipelagosBox.setDisable(true);

            } else { //if I'm choosing freely any archipelago for other reasons
                gui.setArchiIndex(archiIndex);
                archipelagosBox.setDisable(true);
            }
        }
    }

    private int findCurrentMNIndex(List<LightArchi> archipelagos) {
        for(LightArchi a : archipelagos) {
            if (a.isMNPresent())
                return archipelagos.indexOf(a);
        }
        return -1;
    }

    /**
     * Returns an assistant given their name.
     *
     * @param name                the name of an Assistant.
     * @return                    the Assistant.
     */
    private Assistant getAssistantByName(String name) {
        switch (name.toUpperCase()) {
            case "DOG":
                return Assistant.DOG;
            case "CAT":
                return Assistant.CAT;
            case "OSTRICH":
                return Assistant.OSTRICH;
            case "EAGLE":
                return Assistant.EAGLE;
            case "ELEPHANT":
                return Assistant.ELEPHANT;
            case "TURTLE":
                return Assistant.TURTLE;
            case "CHETAAH":
                return Assistant.CHEETAH;
            case "LIZARD":
                return Assistant.LIZARD;
            case "OCTOPUS":
                return Assistant.OCTOPUS;
            case "FOX":
                return Assistant.FOX;
            default:
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
        LightCharacter selectedCharacter;
        int archiIndex = -1;
        int studentNumber = 0;
        Color[] studColors = null;

        if (event.getButton().equals(MouseButton.PRIMARY)) {
            String characterName = event.getPickResult().getIntersectedNode().getId();

            if(board.getSelectedCharacters()[getCharIndexByName(characterName)].getCost() > board.getCurrentPlayerSchoolBoard().getPlayer().getCoins()) {
                gui.warningDialog("Cannot choose this character card since you do not have enough coins. Try again.");
                return;
            } else if (characterName.equalsIgnoreCase("minstrel") && Arrays.stream(board.getCurrentPlayerSchoolBoard().getDiningRoom()).allMatch(t -> t == 0)) {
                gui.warningDialog("Cannot choose this character card since you do not have any students in your dining room. Try again.");
                return;

            }

            selectedCharacter = board.getSelectedCharacters()[getCharIndexByName(characterName)];
            int maxArchis = board.getArchipelagos().size();
            List<Color> availableColors;

            //switch case of all characters to ask the proper values
            switch (selectedCharacter.getName()) {
                case "Monk":
                    gui.infoDialog("Monk effect activated!");
                    studentNumber = 1;
                    studColors = new Color[studentNumber*2];
                    studColors[studentNumber-1] = gui.askColor(DataChores.getColorsByStudents(selectedCharacter.getStudents()), "Select the student you would like to take from the character card");

                    gui.infoDialog("Select the island where you would like to move the student on");
                    archiIndex = gui.askArchipelago(maxArchis);
                    break;

                case "Farmer":
                    gui.infoDialog("Farmer effect activated! You take control of any professor if there's a tie between players' students in the dining rooms.");
                    break;

                case "Herald":
                    gui.infoDialog("Herald effect activated! Select the island you would like to be resolved");
                    archiIndex = gui.askArchipelago(maxArchis);
                    break;

                case "Magicmailman":
                    gui.infoDialog("Magic Mailman effect activated! You may now move mother nature up to 2 additional islands");
                    break;

                case "Grannygrass":
                    gui.infoDialog("Granny Grass effect activated! Select the island here you would like to put the No Entry Tile on");
                    archiIndex = gui.askArchipelago(maxArchis);
                    break;

                case "Centaur":
                    gui.infoDialog("Centaur effect activated! Select the island to cancel its towers influence");
                    archiIndex = gui.askArchipelago(maxArchis);
                    break;

                case "Jester":
                    gui.infoDialog("Jester effect activated!");
                    studentNumber = studNumberDialog(3, "Select the number of students you would like to swap from the character card [up to 3]");
                    studColors = new Color[studentNumber*2];
                    availableColors = DataChores.getColorsByStudents(selectedCharacter.getStudents());

                    for (int i = 0; i < studentNumber; i++) {
                        studColors[i] = gui.askColor(availableColors, "Select the student you would like to take from the character card. " + (studentNumber-i) + " student(s) left");
                        DataChores.checkColorNumber(selectedCharacter.getStudents(), studColors, i, availableColors);
                    }
                    availableColors = DataChores.getColorsByStudents(board.getCurrentPlayerSchoolBoard().getEntrance());
                    askEntranceStudents(board, studentNumber, studColors, availableColors);

                    break;

                case "Knight":
                    gui.infoDialog("Knight effect ativated! You now have 2 more points of influence on islands during this turn.");
                    break;

                case "Mushroomman":
                    gui.infoDialog("Mushroom Man effect activated!");
                    studentNumber = 1;
                    studColors = new Color[studentNumber*2];
                    studColors[studentNumber-1] = gui.askColor(new ArrayList<>(Arrays.asList(Color.values())), "Select a color. During this turn, this color adds no influence.");
                    break;

                case "Minstrel":
                    gui.infoDialog("Minstrel effect activated!");

                    studentNumber = studNumberDialog(2, "Select the number of students you would like to swap from the dining room [up to 2]");
                    while (studentNumber > Arrays.stream(board.getCurrentPlayerSchoolBoard().getDiningRoom()).sum()) {
                        gui.errorDialog("Invalid student number. Select a valid number of students. Try again");
                        studentNumber = studNumberDialog(2, "Select the number of students you would like to swap from the dining room [up to 2]");
                    }
                    studColors = new Color[studentNumber*2];
                    availableColors = DataChores.getColorsByDR(board.getCurrentPlayerSchoolBoard().getDiningRoom());

                    for (int i = 0; i < studentNumber; i++) {
                        studColors[i] = gui.askColor(availableColors, "Select the student you would like to take from the dining room." + (studentNumber-i) + " student(s) left.");
                        DataChores.checkColorNumberDR(board.getCurrentPlayerSchoolBoard().getDiningRoom(), studColors, i, availableColors);
                    }

                    availableColors = DataChores.getColorsByStudents(board.getCurrentPlayerSchoolBoard().getEntrance());
                    askEntranceStudents(board, studentNumber, studColors, availableColors);

                    break;

                case "Spoiledprincess":
                    gui.infoDialog("Spoiled Princess effect activated!");
                    studentNumber = 1;
                    studColors = new Color[studentNumber*2];
                    studColors[studentNumber-1] = gui.askColor(DataChores.getColorsByStudents(selectedCharacter.getStudents()), "Select the student you would like to take from the character card and place in your dining room");
                    break;

                case "Thief":
                    gui.infoDialog("Thief effect activated!");
                    studentNumber = 1;
                    studColors = new Color[studentNumber*2];
                    studColors[studentNumber-1] = gui.askColor(new ArrayList<>(Arrays.asList(Color.values())), "Select a color. Every player, including yourself, will return 3 students of that color from the dining room to the bag.");
                    break;

                default:
                    break;
            }
            gui.getClient().sendMsgToServer(new CharacterReply(selectedCharacter, archiIndex, studentNumber, studColors));
            charactersBox.setDisable(true);
        }
    }

    private int getCharIndexByName(String characterName) {
        for (int i = 0; i < board.getSelectedCharacters().length; i++) {
            if (characterName.equalsIgnoreCase(board.getSelectedCharacters()[i].getName()))
                return i;
        }
        return -1;
    }

    private int studNumberDialog(int maxStudNumber, String text) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Student number selection");
        alert.setHeaderText("Press the right button");
        alert.setContentText(text);
        ((Stage)alert.getDialogPane().getScene().getWindow()).getIcons().add(new Image("/img/icon.png"));

        ButtonType button1 = new ButtonType("1");
        ButtonType button2 = new ButtonType("2");
        ButtonType button3 = new ButtonType("3");

        if (maxStudNumber == 2) {
            alert.getButtonTypes().setAll(button1, button2);
        } else
            alert.getButtonTypes().setAll(button1, button2, button3);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == button1){
            return 1;
        } else if (result.get() == button2) {
            return 2;
        } else if (result.get() == button3) {
            return 3;
        } else {
            gui.errorDialog("An error occurred.");
            return -1;
        }
    }

    /**
     * Asks the user which students they want to swap from their entrance.
     * Used in {@link CLI#askCharacter(LightBoard) askCharacter method} in order to
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
            studColors[studentNumber+i] = gui.askColor(availableColors, "Select now the student(s) you would like to swap from your entrance. " + (studentNumber-i) + " student(s) left.");
            DataChores.checkColorNumber(board.getCurrentPlayerSchoolBoard().getEntrance(), studColors, i, availableColors);
            System.out.print("\n");
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
