package it.polimi.ingsw.view.gui.scenes;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;

public class LoginSceneController {

    @FXML
    private TextField nicknameField;
    @FXML
    private TextField gameNameField;
    @FXML
    private AnchorPane confirmButton;
    @FXML
    private AnchorPane exitButton;
    private boolean validNickname = false;
    private boolean firstGameName = true;
    private String nickname;
    private String gameName;


    @FXML
    public void initialize() {
        confirmButton.setDisable(true);
        gameNameField.setDisable(true);
    }

    @FXML
    private void confirm() {
        nickname = nicknameField.getText();

        if(!validNickname) {
            if (nickname.isEmpty()) {
                System.out.println("Nickname is missing");
            } else {
                System.out.println("Sending nickname '" + nickname + "' for checking");
                validNickname = true;
            }
        }

        if (validNickname) {
            confirmButton.setDisable(true);
            nicknameField.setDisable(true);
            gameNameField.setDisable(false);

            gameName = gameNameField.getText();

            if (gameName.isEmpty() && !firstGameName)
                System.out.println("gameName is missing");

            if (!gameName.isEmpty())
                System.out.println("Sending gameName '" + gameName + "' for checking");

            firstGameName = false;
        }
    }

    @FXML
    private void nicknameCheckProperty(KeyEvent e) {
        nickname = nicknameField.getText();
        confirmButton.setDisable( nickname.trim().isEmpty() || nickname.length()<3 );

        if (e.getCode().toString().equals("ENTER") && !confirmButton.isDisable())
            confirm();
    }

    @FXML
    private void gameNameCheckProperty(KeyEvent e) {
        gameName = gameNameField.getText();
        confirmButton.setDisable( gameName.trim().isEmpty() || gameName.length()<3 );

        if (e.getCode().toString().equals("ENTER") && !confirmButton.isDisable())
            confirm();
    }

    @FXML
    public void exit() {
        System.out.println("Exit");
        System.exit(0);
    }
}
