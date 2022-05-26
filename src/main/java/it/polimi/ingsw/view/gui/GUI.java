package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.controller.actions.ActionType;
import it.polimi.ingsw.model.Assistant;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Wizard;
import it.polimi.ingsw.network.client.Client;
import it.polimi.ingsw.view.ViewInterface;
import it.polimi.ingsw.view.gui.scenes.*;
import it.polimi.ingsw.view.utilities.lightclasses.LightBoard;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.List;

public class GUI extends Application implements ViewInterface {
    private Client client;
    private Stage stage;
    protected static MediaPlayer mediaPlayer;
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/StartScene.fxml"));
            Scene scene = new Scene(loader.load());
            this.stage = stage;
            ((SceneControllerInterface) loader.getController()).setGUI(this);

            Font.loadFont(getClass().getResourceAsStream("/fonts/Roboto.ttf"), 13);
            Font.loadFont(getClass().getResourceAsStream("/fonts/Metamorphous.ttf"), 13);

            Media media = new Media(getClass().getClassLoader().getResource("audio/Warm_Light.mp3").toString());
            mediaPlayer = new MediaPlayer(media);
            mediaPlayer.setVolume(30);
            mediaPlayer.setAutoPlay(true);
            mediaPlayer.setOnEndOfMedia(() -> {
                mediaPlayer.seek(Duration.ZERO);
                mediaPlayer.play();
            });

            Image icon = new Image("/img/icon.png");
            stage.getIcons().add(icon);
            stage.setTitle("Eriantys");
            stage.setResizable(true);
            stage.setMinHeight(630);
            stage.setMinWidth(610);
            stage.setResizable(false);

            stage.setScene(scene);
            stage.show();

            stage.setOnCloseRequest(event -> {
                event.consume();
                closeWindow(stage);
            });

        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    @Override
    public Client askServerInfo() {
        SceneControllerInterface csc = new ConnectionSceneController();
        SceneController.setCurrentController(csc);
        csc.setGUI(this);

        Platform.runLater(() -> {
            try {
                SceneController.switchScene(stage, "ConnectionScene");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        return null;
    }

    @Override
    public void askNickname() {
        SceneControllerInterface lsc = new LoginSceneController();
        SceneController.setCurrentController(lsc);
        lsc.setGUI(this);

        //is it okay to have all this static class and methods? Others made an instance (GUI, SceneController, CLI???)

//should I set lsc as current scene controller in SceneController in order to get this one controller in the next askGameName()?
        //SceneController.setCurrentController(lsc);     should i cast the variable? is it possible?
    /*    Platform.runLater(() -> {
            try {
                SceneController.switchScene(stage, "NicknameScene");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });       */
        //what happens when i click "next" button? how do decide to pass variables here on that click? (listeners?)
        //client.sendMsgToServer(new NicknameReply( lsc.getNickname() ));

    }

    @Override
    public void askGameName() {
        SceneControllerInterface lsc = SceneController.getCurrentController();
        lsc.setGUI(this);

        //lsc = SceneController.getCurrentController();
        //lsc.disable(nickname);
        //lsc.enable(gameName);
    }

    @Override
    public void askGameMode() {
        NewGameSceneController ngsc = new NewGameSceneController();
        //sceneController.setController(ngsc);
        //ngsc.disable(nPlayers)
        //ngsc.enable(expertModeSection)
        try {
            SceneController.switchScene(stage, "NewGameScene");
        } catch (IOException e) {
            System.out.println("Error in opening this scene");
        }

    }

    @Override
    public void askPlayerNumber() {

    }

    @Override
    public void askWizard(List<Wizard> availableWizards) {
        /*
            do things...
         */

        stage.setFullScreen(true);
    }

    @Override
    public void askAssistant(List<Assistant> availableAssistants, List<Assistant> chosenAssistants) {

    }

    @Override
    public void askAction(List<ActionType> possibleActions) {

    }

    @Override
    public void askStudent(List<Color> availableColors) {

    }

    @Override
    public void askPlace(int maxArchis) {

    }

    @Override
    public int askArchipelago(int maxArchis) {
        return 0;
    }

    @Override
    public void askCharacter(LightBoard board) {

    }

    @Override
    public void askMNSteps(int maxMNSteps) {

    }

    @Override
    public void askCloud(List<Integer> availableClouds) {

    }

    @Override
    public void displayMessage(String message) {

    }

    @Override
    public void displayDisconnectionMessage(String disconnectedNickname, String message) {

    }

    @Override
    public void displayErrorAndExit(String message) {

    }

    @Override
    public void printBoard(LightBoard board) {

    }

    @Override
    public void displayEndgameResult(String winner) {

    }

    public static MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }

    private void errorDialog(String error) {
        Alert errorDialog = new Alert(Alert.AlertType.ERROR);
        errorDialog.setTitle("Game Error");
        errorDialog.setHeaderText("Error!");
        errorDialog.setContentText(error);
        errorDialog.showAndWait();
    }

    private void closeWindow(Stage stage) {
        Alert alertDialog = new Alert(Alert.AlertType.CONFIRMATION);
        alertDialog.setTitle("Exiting");
        alertDialog.setHeaderText("You're about to exit");
        alertDialog.setContentText("Do you want to close the game?");

        if (alertDialog.showAndWait().get() == ButtonType.OK) {
            System.out.println("Exit confirmed.");
            stage.close();
        }
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Client getClient() {
        return client;
    }
}