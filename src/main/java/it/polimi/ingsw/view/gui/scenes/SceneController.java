package it.polimi.ingsw.view.gui.scenes;

import it.polimi.ingsw.view.gui.GUI;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneController implements SceneControllerInterface {
    private GUI gui;
    private static Scene scene;
    private static Parent root;
    private static Stage window;
    private static FXMLLoader loader;
    private static SceneControllerInterface currentController;

    /**
     * Displays an error in a popup.
     */
    public static void displayError() {
        //TODO: implement displayError method
    }

    /**
     * Displays who won the game.
     */
    public static void displayWinner() {
        //TODO: implement displayWinner method
    }

    /**
     * Switches between scenes.
     * Takes different parameters based on the specific situation.
     */
    public static void switchScene(MouseEvent event, String fxmlFile) throws IOException {
        loader = new FXMLLoader(SceneController.class.getResource("/fxml/" + fxmlFile + ".fxml"));
        scene = new Scene(loader.load());

        window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        //window.show();
    }

    public static void switchScene(Stage window, String fxmlFile) throws IOException {
        loader = new FXMLLoader(SceneController.class.getResource("/fxml/" + fxmlFile + ".fxml"));
        scene = new Scene(loader.load());

        window.setScene(scene);
        //window.show();
    }

    /**
     * Returns the scene currently active.
     *
     * @return              the current Scene.
     */
    public static Scene getCurrentScene() {
        return scene;
    }

    /**
     * Returns the controller corresponding to the currently active scene.
     *
     * @return              the current SceneController.
     */
    public static SceneControllerInterface getCurrentController() {
        return currentController;
    }

    public static void setCurrentController(SceneControllerInterface currentController) {
        SceneController.currentController = currentController;
    }

    @Override
    public void setGUI(GUI gui) {
        this.gui = gui;
    }
}