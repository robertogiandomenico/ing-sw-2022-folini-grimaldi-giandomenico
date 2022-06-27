package it.polimi.ingsw.view.gui.scenes;

import it.polimi.ingsw.view.gui.GUI;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ResultSceneController implements SceneControllerInterface {

    private GUI gui;
    @FXML
    private Label resultLabel;
    @FXML
    private Label subtitleLabel;

    /**
     * Sets the GUI.
     *
     * @param gui               a GUI.
     */
    @Override
    public void setGUI(GUI gui) {
        this.gui = gui;
    }

    public void setWinner(String result) {
        resultLabel.setText(result);
    }

    public void setSubtitle(String subtitle) {
        subtitleLabel.setText(subtitle);
    }
}
