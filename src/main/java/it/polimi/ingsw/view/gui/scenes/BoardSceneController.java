package it.polimi.ingsw.view.gui.scenes;

import it.polimi.ingsw.model.Assistant;
import it.polimi.ingsw.model.Student;
import it.polimi.ingsw.model.TowerColor;
import it.polimi.ingsw.model.Wizard;
import it.polimi.ingsw.network.messages.clientMessages.ChooseAssistantReply;
import it.polimi.ingsw.view.gui.GUI;
import it.polimi.ingsw.view.utilities.MatrixOperations;
import it.polimi.ingsw.view.utilities.lightclasses.LightBoard;
import it.polimi.ingsw.view.utilities.lightclasses.LightCharacter;
import it.polimi.ingsw.view.utilities.lightclasses.LightSchoolBoard;
import javafx.fxml.FXML;
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

import java.util.List;

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
    private TabPane othersPlayerPane;
    private MouseEvent event;
    private GUI gui;
    private LightBoard lightBoard;
    private LightSchoolBoard thisPlayer;

    @FXML
    private void initialize() {
        initializeCharacters(lightBoard.getSelectedCharacters());
        initializeThisPlayer();
        initializeOtherPlayers();
        initializeArchipelagos();
        initializeClouds();
        initializeCoinsSupply(lightBoard.getCoinsSupply());
    }

    private void initializeCharacters(LightCharacter[] selectedCharacters) {
        for (int i = 0; i < 3; i++) {
            ((ImageView)charactersBox.getChildren().get(i)).setImage(new Image(getClass().getResourceAsStream("/img/characters/" + selectedCharacters[i].getName().toLowerCase() + ".png")));

            charactersBox.getChildren().get(i).setId(selectedCharacters[i].getName().toLowerCase());
        }
    }

    private void initializeThisPlayer() {
        for (LightSchoolBoard lsb : lightBoard.getSchoolBoards()) {
            if (lsb.getPlayer().getNickname().equals(gui.getClient().getNickname())) {
                thisPlayer = lsb;
                break;
            }
        }

        ((Label) thisPlayerPane.getChildren().get(1)).setText(thisPlayer.getPlayer().getNickname());
        ((ImageView) thisPlayerPane.getChildren().get(2)).setImage(getWizardIcon(thisPlayer.getPlayer().getSelectedWizard()));

        try {
            ((ImageView) thisPlayerPane.getChildren().get(3)).setImage(new Image(getClass().getResourceAsStream("/img/assistant/" + thisPlayer.getPlayer().getDiscardPile().name().toLowerCase() + ".png")));
        } catch (NullPointerException e) {
            ((ImageView) thisPlayerPane.getChildren().get(3)).setImage(new Image(getClass().getResourceAsStream("/img/blank.png")));
        }

        for (int i = 0; i < thisPlayer.getEntrance().length; i++) {
            ((ImageView)((AnchorPane) thisPlayerPane.getChildren().get(4)).getChildren().get(i)).setImage(displayStudent(thisPlayer.getEntrance()[i]));
        }

        for (int i = 0; i < thisPlayer.getTowersLeft(); i++) {
            ((ImageView)((AnchorPane) thisPlayerPane.getChildren().get(5)).getChildren().get(i)).setImage(getTowerIcon(thisPlayer.getPlayer().getTowerColor()));
        }

        for (int i = 0; i < thisPlayer.getProfessorTable().length; i++) {
            ((VBox) thisPlayerPane.getChildren().get(6)).getChildren().get(i).setVisible(thisPlayer.getProfessorTable()[i]);
        }

        ((Label)((AnchorPane)thisPlayerPane.getChildren().get(7)).getChildren().get(1)).setText("x" + thisPlayer.getPlayer().getCoins());

    }

    private Image getTowerIcon(TowerColor towerColor) {
        switch (towerColor) {
            case GREY:
                return new Image(getClass().getResourceAsStream("/img/towers/upgrey.png"));
            case BLACK:
                return new Image(getClass().getResourceAsStream("/img/towers/upblack.png"));
            case WHITE:
                return new Image(getClass().getResourceAsStream("/img/towers/upwhite.png"));
            default:
                return new Image(getClass().getResourceAsStream("/img/blank.png"));
        }
    }

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
                return new Image(getClass().getResourceAsStream("/img/blank.png"));
        }
    }

    private void initializeArchipelagos() {
        for (int i = 0; i < lightBoard.getArchipelagos().size(); i++) {

            //set tower
            try {
                ((ImageView) ((AnchorPane) archipelagosBox.getChildren().get(i)).getChildren().get(1)).setImage(new Image(getClass().getResourceAsStream("/img/towers/" + lightBoard.getArchipelagos().get(i).getTowerColor().name().toLowerCase() + ".png")));
            } catch (NullPointerException e) {
                ((ImageView) ((AnchorPane) archipelagosBox.getChildren().get(i)).getChildren().get(1)).setImage(new Image(getClass().getResourceAsStream("/img/blank.png")));
            }

            //set mother nature
            ((AnchorPane) archipelagosBox.getChildren().get(i)).getChildren().get(2).setVisible(lightBoard.getArchipelagos().get(i).isMNPresent());

            //set no entry tile
            ((AnchorPane) archipelagosBox.getChildren().get(i)).getChildren().get(8).setVisible(lightBoard.getArchipelagos().get(i).isNoEntryTilePresent());

            //set students number and visibility
            for (int j = 0; j < 5; j++) {
                ((Label)((AnchorPane)((AnchorPane)archipelagosBox.getChildren().get(i)).getChildren().get(j+3)).getChildren().get(1)).setText(Integer.toString(MatrixOperations.columnSum(lightBoard.getArchipelagos().get(i).getIslands(), j)));

                if (((Label)((AnchorPane)((AnchorPane)archipelagosBox.getChildren().get(i)).getChildren().get(j+3)).getChildren().get(1)).getText().equals("0"))
                    ((AnchorPane)archipelagosBox.getChildren().get(i)).getChildren().get(j+3).setVisible(false);
                else
                    ((AnchorPane)archipelagosBox.getChildren().get(i)).getChildren().get(j+3).setVisible(true);
            }

        }
    }

    private void initializeClouds() {
        int cloudsNumber = lightBoard.getCloudsNumber();
        if (cloudsNumber == 2) {
            cloudsBox.getChildren().get(2).setDisable(true);
            cloudsBox.getChildren().get(2).setVisible(false);
        }

        for (int i = 0; i < cloudsNumber; i++) {
            for (int j = 0; j < 3; j++) {
                ((ImageView) ((AnchorPane) cloudsBox.getChildren().get(i)).getChildren().get(j+1)).setImage(displayStudent(lightBoard.getCloud(i)[j]));
            }
        }
    }

    private void initializeCoinsSupply(int coinsSupply) {
        ((Label) coinsSupplyBox.getChildren().get(1)).setText("x" + coinsSupply);
    }

    private void initializeOtherPlayers() {
        LightSchoolBoard[] otherPlayers = new LightSchoolBoard[lightBoard.getSchoolBoards().size()-1];

        for (int i = 0; i < lightBoard.getSchoolBoards().size(); i++) {
            if (!lightBoard.getSchoolBoards().get(i).getPlayer().getNickname().equals(gui.getClient().getNickname())) {
                otherPlayers[i] = lightBoard.getSchoolBoards().get(i);
            }
        }

        for (int i = 0; i < otherPlayers.length; i++) {

            othersPlayerPane.getTabs().get(i).setText(otherPlayers[i].getPlayer().getNickname());

            ((Label)((AnchorPane)othersPlayerPane.getTabs().get(i).getContent()).getChildren().get(1)).setText(otherPlayers[i].getPlayer().getNickname());
            ((ImageView)((AnchorPane)othersPlayerPane.getTabs().get(i).getContent()).getChildren().get(2)).setImage(getWizardIcon(otherPlayers[i].getPlayer().getSelectedWizard()));

            try {
                ((ImageView)((AnchorPane)othersPlayerPane.getTabs().get(i).getContent()).getChildren().get(3)).setImage(new Image(getClass().getResourceAsStream("/img/assistant/" + otherPlayers[i].getPlayer().getDiscardPile().name().toLowerCase() + ".png")));
            } catch (NullPointerException e){
                ((ImageView)((AnchorPane)othersPlayerPane.getTabs().get(i).getContent()).getChildren().get(3)).setImage(new Image(getClass().getResourceAsStream("/img/blank.png")));
            }

            for (int j = 0; j < otherPlayers[i].getEntrance().length; j++) {
                ((ImageView)((AnchorPane)((AnchorPane)othersPlayerPane.getTabs().get(i).getContent()).getChildren().get(5)).getChildren().get(j)).setImage(displayStudent(otherPlayers[i].getEntrance()[j]));
            }

            for (int j = 0; j < otherPlayers[i].getTowersLeft(); j++) {
                ((ImageView)((AnchorPane)((AnchorPane)othersPlayerPane.getTabs().get(i).getContent()).getChildren().get(4)).getChildren().get(j)).setImage(getTowerIcon(otherPlayers[i].getPlayer().getTowerColor()));
            }

            for (int j = 0; j < otherPlayers[i].getProfessorTable().length; j++) {
                ((VBox)((AnchorPane)othersPlayerPane.getTabs().get(i).getContent()).getChildren().get(6)).getChildren().get(j).setVisible(otherPlayers[i].getProfessorTable()[j]);
            }

            ((Label)((AnchorPane)((AnchorPane)othersPlayerPane.getTabs().get(i).getContent()).getChildren().get(7)).getChildren().get(1)).setText("x" + otherPlayers[i].getPlayer().getCoins());

        }
    }

    private Image displayStudent(Student student) {
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
                return new Image(getClass().getResourceAsStream("/img/blank.png"));
        }
    }

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

    public void enableAssistantBox() {
        assistantBox.setDisable(false);
        chooseAssistant();
        assistantBox.setDisable(true);
    }

    public void enableCharactersBox() {
        charactersBox.setDisable(false);
        chooseCharacter();
        charactersBox.setDisable(true);
    }

    public void enableSchoolBoard() {
        thisPlayerPane.setDisable(false);
    }

    public void enableArchipelagos() {
        archipelagosBox.setDisable(false);
    }

    private void chooseAssistant() {
        if (event.getButton().equals(MouseButton.PRIMARY)) {
            String selectedAssistant = event.getPickResult().getIntersectedNode().getId();
            gui.getClient().sendMsgToServer(new ChooseAssistantReply(getAssistantByName(selectedAssistant)));
        }
    }

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

    private void chooseCharacter() {
        if (event.getButton().equals(MouseButton.PRIMARY)) {
            String selectedCharacter = event.getPickResult().getIntersectedNode().getId();
            //....
            //gui.getClient().sendMsgToServer(new CharacterReply( , , , ,));
        }
    }




    /**
     * @param gui
     */
    @Override
    public void setGUI(GUI gui) {
        this.gui = gui;
    }

    public void setLightBoard(LightBoard lightBoard) {
        this.lightBoard = lightBoard;
    }
}
