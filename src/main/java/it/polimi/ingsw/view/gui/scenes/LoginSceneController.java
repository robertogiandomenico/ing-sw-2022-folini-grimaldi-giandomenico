package it.polimi.ingsw.view.gui.scenes;

import it.polimi.ingsw.view.gui.GUI;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;

public class LoginSceneController implements SceneControllerInterface {

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
    private GUI gui;


    @FXML
    private void initialize() {
        confirmButton.setDisable(true);
        gameNameField.setDisable(true);
    }

    @FXML
    private void confirm() {
        if (!nicknameField.isDisable() && gameNameField.isDisable()) {
            nickname = nicknameField.getText();

            gui.getClient().sendMsgToServer(nickname);
        }

        if (nicknameField.isDisable() && !gameNameField.isDisable()) {
            gameName = gameNameField.getText();

            gui.getClient().sendMsgToServer(gameName);
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
    private void exit() {
        System.out.println("Exit");
        System.exit(0);
    }

    /**
     * @param gui
     */
    @Override
    public void setGUI(GUI gui) {
        this.gui= gui;
    }

    public void enableNextField() {
        nicknameField.setDisable(true);
        gameNameField.setDisable(false);
    }
}
