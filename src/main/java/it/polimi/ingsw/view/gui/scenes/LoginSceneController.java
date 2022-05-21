package it.polimi.ingsw.view.gui.scenes;

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
    private boolean validNickname = false;
    private boolean firstGameName = true;


    @FXML
    public void initialize() {
        gameName.setDisable(true);
    }

    @FXML
    private void confirm() {

        if(!validNickname) {
            if (nickname.getCharacters().toString().isEmpty()) {
                System.out.println("Nickname is missing");
            } else {
                System.out.println("Sending nickname for checking");
                validNickname = true;
            }
        }

        if (validNickname) {
            nickname.setDisable(true);
            gameName.setDisable(false);


            if (gameName.getCharacters().toString().isEmpty() && !firstGameName)
                System.out.println("gameName is missing");

            if (!gameName.getCharacters().toString().isEmpty())
                System.out.println("Sending gameName for checking");

            firstGameName = false;
        }


        /*
        BooleanBinding booleanBinding = nickname.textProperty().isEmpty().or(gameName.textProperty().isEmpty());
        if (nickname.getCharacters().toString().isEmpty() || gameName.getCharacters().toString().isEmpty()) {
            System.out.println("Fields are empty, insert values");
            confirm.disableProperty().bind(booleanBinding);
        } else {
            System.out.println("Confirm!");
        }
        */
    }

    @FXML
    public void exit() {
        System.out.println("Exit");
        System.exit(0);
    }
}
