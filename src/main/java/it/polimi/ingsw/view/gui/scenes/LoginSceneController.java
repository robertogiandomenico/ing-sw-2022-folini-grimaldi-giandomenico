package it.polimi.ingsw.view.gui.scenes;

import it.polimi.ingsw.network.messages.clientMessages.GameNameReply;
import it.polimi.ingsw.network.messages.clientMessages.NicknameReply;
import it.polimi.ingsw.view.gui.GUI;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

/**
 * This class represents the controller for the login scene.
 */
public class LoginSceneController implements SceneControllerInterface {
    @FXML
    private TextField nicknameField;
    @FXML
    private TextField gameNameField;
    @FXML
    private Button confirmButton;
    @FXML
    private Button exitButton;
    private boolean firstGameName = true;
    private boolean firstNickname = true;
    private String nickname;
    private String gameName;
    private GUI gui;

    /**
     * Initializes buttons and fields.
     */
    @FXML
    private void initialize() {
        confirmButton.setDisable(true);
        gameNameField.setDisable(true);
    }

    /**
     * Confirms the inserted nickname or game name and sends them to the server.
     */
    @FXML
    private void confirm() {
        if (!nicknameField.isDisable() && gameNameField.isDisable()) {
            nickname = nicknameField.getText();
            gameNameField.setDisable(false);
            nicknameField.setDisable(true);
            confirmButton.setDisable(true);

            gui.getClient().setNickname(nickname);
            gui.getClient().sendMsgToServer(new NicknameReply(nickname));
            return;
        }

        if (nicknameField.isDisable() && !gameNameField.isDisable()) {
            gameName = gameNameField.getText();
            gui.getClient().sendMsgToServer(new GameNameReply(gameName));
        }
    }

    /**
     * Checks the validity of the inserted nickname.
     *
     * @param e              the pressed key.
     */
    @FXML
    private void nicknameCheckProperty(KeyEvent e) {
        nickname = nicknameField.getText();
        confirmButton.setDisable( nickname.trim().isEmpty() || nickname.length()<3 );

        if (e.getCode().toString().equals("ENTER") && !confirmButton.isDisable())
            confirm();
    }

    /**
     * Checks the validity of the inserted game name.
     *
     * @param e              the pressed key.
     */
    @FXML
    private void gameNameCheckProperty(KeyEvent e) {
        gameName = gameNameField.getText();
        confirmButton.setDisable( gameName.trim().isEmpty() || gameName.length()<3 );

        if (e.getCode().toString().equals("ENTER") && !confirmButton.isDisable())
            confirm();
    }

    /**
     * Closes the window.
     */
    @FXML
    private void exit() {
        gui.closeWindow(gui.getStage());
    }

    /**
     * Sets the GUI.
     *
     * @param gui            a GUI.
     */
    @Override
    public void setGUI(GUI gui) {
        this.gui = gui;
    }
}
