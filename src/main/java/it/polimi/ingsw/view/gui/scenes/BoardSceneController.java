package it.polimi.ingsw.view.gui.scenes;

import it.polimi.ingsw.model.Assistant;
import it.polimi.ingsw.model.Student;
import it.polimi.ingsw.network.messages.clientMessages.ChooseAssistantReply;
import it.polimi.ingsw.view.gui.GUI;
import it.polimi.ingsw.view.utilities.lightclasses.LightBoard;
import it.polimi.ingsw.view.utilities.lightclasses.LightCharacter;
import javafx.fxml.FXML;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

import java.util.List;

public class BoardSceneController implements SceneControllerInterface {
    @FXML
    private HBox charactersBox;
    @FXML
    private GridPane assistantBox;
    @FXML
    private GridPane worldBox;
    @FXML
    private AnchorPane thisPlayerPane;
    @FXML
    private TabPane othersPlayerPane;
    private MouseEvent event;
    private GUI gui;
    private LightBoard lightBoard;

    @FXML
    private void initialize() {
        initializeCharacters(lightBoard.getSelectedCharacters());
        //initializeThisPlayer();       how can I get access to exactly this player???
        initializeClouds(lightBoard);
    }

    private void initializeCharacters(LightCharacter[] selectedCharacters) {
        for (int i = 0; i < 3; i++) {
            ((ImageView)charactersBox.getChildren().get(i)).setImage(new Image(getClass().getResourceAsStream("/img/characters/" + selectedCharacters[i].getName().toLowerCase() + ".png")));

            charactersBox.getChildren().get(i).setId(selectedCharacters[i].getName().toLowerCase());
        }
    }

    private void initializeClouds(LightBoard lightBoard) {
        int cloudsNumber = lightBoard.getCloudsNumber();
        if (cloudsNumber == 2) {
            worldBox.getChildren().get(2).setDisable(true);
            worldBox.getChildren().get(2).setVisible(false);
        }

        for (int i = 0; i < cloudsNumber; i++) {
            for (int j = 0; j < 3; j++) {
                ((ImageView) ((AnchorPane) worldBox.getChildren().get(i)).getChildren().get(j+1)).setImage(new Image(getClass().getResourceAsStream(displayStudent(lightBoard.getCloud(i)[j]))));
            }
        }
    }

    private String displayStudent(Student student) {
        switch (student.getColor()) {
            case RED:
                return "/img/pawns/studentred.png";
            case BLUE:
                return "/img/pawns/studentblue.png";
            case YELLOW:
                return "/img/pawns/studentyellow.png";
            case GREEN:
                return "/img/pawns/studentgreen.png";
            case PINK:
                return "/img/pawns/studentpink.png";
            default:
                return "/img/blank.png";
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

    public void enableWorld() {
        worldBox.setDisable(false);
    }

    private void chooseAssistant() {
        String selectedAssistant = event.getPickResult().getIntersectedNode().getId();
        gui.getClient().sendMsgToServer(new ChooseAssistantReply(getAssistantByName(selectedAssistant)));
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
        String selectedCharacter = event.getPickResult().getIntersectedNode().getId();
        //....
        //gui.getClient().sendMsgToServer(new CharacterReply( , , , ,));
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
