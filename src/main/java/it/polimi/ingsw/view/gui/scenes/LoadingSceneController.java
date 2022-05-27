package it.polimi.ingsw.view.gui.scenes;

import it.polimi.ingsw.view.gui.GUI;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class LoadingSceneController implements SceneControllerInterface {

    @FXML
    private Label label;
    private GUI gui;

    //when receiving a textMessage
    @FXML
    public void changeLabel(String textMessage) {
        label.setText(textMessage);
    }

    /**
     * @param gui
     */
    @Override
    public void setGUI(GUI gui) {
        this.gui = gui;
    }
}