package it.polimi.ingsw.view.gui.scenes;

import it.polimi.ingsw.view.gui.GUI;

public class BoardSceneController implements SceneControllerInterface {
    private GUI gui;

    /**
     * @param gui
     */
    @Override
    public void setGUI(GUI gui) {
        this.gui = gui;
    }
}
