package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.controller.actions.ActionType;
import it.polimi.ingsw.model.Assistant;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Wizard;
import it.polimi.ingsw.network.client.Client;
import it.polimi.ingsw.view.ViewInterface;
import it.polimi.ingsw.view.utilities.lightclasses.LightBoard;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
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
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/ConnectionScene.fxml"));
            Scene scene = new Scene(root);
            this.stage = stage;

            Font.loadFont(getClass().getResourceAsStream("/fonts/Roboto.ttf"), 13);
            Font.loadFont(getClass().getResourceAsStream("/fonts/Metamorphous.ttf"), 13);

            Media media = new Media(new File("src/main/resources/audio/Warm_Light.mp3").toURI().toString());
            mediaPlayer = new MediaPlayer(media);
            mediaPlayer.setVolume(40);
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

        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    @Override
    public Client askServerInfo() {
        return null;
    }

    @Override
    public void askNickname() {

    }

    @Override
    public void askGameName() {

    }

    @Override
    public void askGameMode() {

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
}