package it.polimi.ingsw.view.gui.scenes;

import it.polimi.ingsw.view.gui.GUI;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

/**
 * This class contains methods for generic scenes and to switch between them.
 */
public class SceneController implements SceneControllerInterface {
    private GUI gui;
    private static Scene scene;
    private static FXMLLoader loader;
    private static SceneControllerInterface currentController;

    /**
     * Switches between scenes.
     *
     * @param stage             a Stage.
     * @param fxmlFile          the name of the fxml corresponding to the new scene.
     * @param sci               the SceneControllerInterface corresponding to the new scene.
     * @throws IOException      IOException.
     */
    public static void switchScene(Stage stage, String fxmlFile, SceneControllerInterface sci) throws IOException {
        loader = new FXMLLoader(SceneController.class.getResource("/fxml/" + fxmlFile + ".fxml"));
        loader.setController(sci);
        scene = new Scene(loader.load());
        stage.setScene(scene);
    }

    public static void popUpScene(Stage stage, String fxmlFile, SceneControllerInterface sci) throws IOException {
        loader = new FXMLLoader(SceneController.class.getResource("/fxml/" + fxmlFile + ".fxml"));
        loader.setController(sci);
        scene = new Scene(loader.load());
        stage.setScene(scene);

        stage.initStyle(StageStyle.UTILITY);
        stage.setResizable(false);
        stage.setAlwaysOnTop(true);

        stage.show();
    }

    /**
     * Sets and shows the scene.
     *
     * @param stage             a Stage.
     */
    public static void switchScene(Stage stage) {
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Returns the scene currently active.
     *
     * @return                  the current Scene.
     */
    public static Scene getCurrentScene() {
        return scene;
    }

    /**
     * Returns the controller corresponding to the currently active scene.
     *
     * @return                  the current scene controller.
     */
    public static SceneControllerInterface getCurrentController() {
        return currentController;
    }

    /**
     * Sets the controller corresponding to the currently active scene.
     *
     * @param currentController the current scene controller.
     */
    public static void setCurrentController(SceneControllerInterface currentController) {
        SceneController.currentController = currentController;
    }

    /**
     * Sets the GUI.
     *
     * @param gui               a GUI.
     */
    @Override
    public void setGUI(GUI gui) {
        this.gui = gui;
    }
}