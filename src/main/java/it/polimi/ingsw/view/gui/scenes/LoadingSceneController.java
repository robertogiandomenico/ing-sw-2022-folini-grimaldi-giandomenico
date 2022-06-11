package it.polimi.ingsw.view.gui.scenes;

import it.polimi.ingsw.view.gui.GUI;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

/**
 * This class represents the controller for the loading scene.
 */
public class LoadingSceneController implements SceneControllerInterface {
    @FXML
    private Label label;
    private GUI gui;

    /**
     * Changes the label setting the given text message.
     *
     * @param textMessage      the message.
     */
    //when receiving a textMessage
    @FXML
    public void changeLabel(String textMessage) {
        label.setText(textMessage);
    }

    /**
     * Sets the GUI.
     *
     * @param gui              a GUI.
     */
    @Override
    public void setGUI(GUI gui) {
        this.gui = gui;
    }
}