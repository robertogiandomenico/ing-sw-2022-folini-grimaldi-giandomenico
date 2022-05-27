package it.polimi.ingsw.view.gui.scenes;

import it.polimi.ingsw.network.messages.clientMessages.GameNameReply;
import it.polimi.ingsw.network.messages.clientMessages.NicknameReply;
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
    private boolean firstGameName = true;
    private boolean firstNickname = true;
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
            gameNameField.setDisable(false);
            nicknameField.setDisable(true);
            confirmButton.setDisable(true);

            gui.getClient().sendMsgToServer(new NicknameReply(nickname));
            return;
        }

        if (nicknameField.isDisable() && !gameNameField.isDisable()) {
            gameName = gameNameField.getText();
            gui.getClient().sendMsgToServer(new GameNameReply(gameName));
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
        gui.closeWindow(gui.getStage());
        System.exit(0);
    }

    /**
     * @param gui
     */
    @Override
    public void setGUI(GUI gui) {
        this.gui= gui;
    }
}
