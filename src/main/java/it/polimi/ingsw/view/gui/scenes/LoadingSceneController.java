package it.polimi.ingsw.view.gui.scenes;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class LoadingSceneController {

    @FXML
    private Label label;

    //when receiving a textMessage
    @FXML
    private void changeLabel(String textMessage) {
        label.setText(textMessage);
    }

}