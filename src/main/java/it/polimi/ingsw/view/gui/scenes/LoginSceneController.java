package it.polimi.ingsw.view.gui.scenes;

import javafx.beans.binding.BooleanBinding;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class LoginSceneController {

    @FXML
    private TextField nickname;
    @FXML
    private TextField gameName;
    @FXML
    private AnchorPane confirm;
    @FXML
    private AnchorPane exit;



    @FXML
    private void confirm() {
        BooleanBinding booleanBinding = nickname.textProperty().isEmpty().or(gameName.textProperty().isEmpty());
        if (nickname.getCharacters().toString().isEmpty() || gameName.getCharacters().toString().isEmpty()) {
            System.out.println("Fields are empty, insert values");
            confirm.disableProperty().bind(booleanBinding);
        } else {
            System.out.println("Confirm!");
        }
    }

    @FXML
    public void exit() {
        System.out.println("Exit");
        System.exit(0);
    }
}
