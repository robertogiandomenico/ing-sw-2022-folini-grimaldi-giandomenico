package it.polimi.ingsw.view.gui.scenes;

import it.polimi.ingsw.model.Student;
import it.polimi.ingsw.view.gui.GUI;
import it.polimi.ingsw.view.utilities.lightclasses.LightBoard;
import it.polimi.ingsw.view.utilities.lightclasses.LightCharacter;
import javafx.fxml.FXML;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

public class BoardSceneController implements SceneControllerInterface {
    @FXML
    private HBox selectedCharactersBox;
    @FXML
    private GridPane availableAssistantBox;
    @FXML
    private GridPane worldBox;
    @FXML
    private AnchorPane thisPlayerPane;
    @FXML
    private TabPane othersPlayerPane;
    private GUI gui;
    private LightBoard lightBoard;

    @FXML
    private void initialize() {
        initializeCharacters(lightBoard.getSelectedCharacters());
        //initializeAssistants();       how can I get assistants if I only got them when a message is received???
        //initializeThisPlayer();       how can I get access to exactly this player???
        initializeClouds(lightBoard);
    }

    private void initializeCharacters(LightCharacter[] selectedCharacters) {
        for (int i = 0; i < 3; i++) {
            ((ImageView)selectedCharactersBox.getChildren().get(i)).setImage(new Image(getClass().getResourceAsStream("/img/characters/" + selectedCharacters[i].getName().toLowerCase())));
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
