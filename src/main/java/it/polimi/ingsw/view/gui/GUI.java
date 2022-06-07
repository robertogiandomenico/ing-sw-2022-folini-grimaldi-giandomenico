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
    private boolean firstPrintBoard = true;
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/StartScene.fxml"));
            Scene scene = new Scene(loader.load());
            this.stage = stage;
            ( (SceneControllerInterface)loader.getController() ).setGUI(this);

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
                SceneController.switchScene(stage, "ConnectionScene", csc);
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

        Platform.runLater(() -> {
            try {
                SceneController.switchScene(stage, "LoginScene", lsc);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public void askGameName() {
        Platform.runLater(() -> SceneController.switchScene(stage));
    }

    @Override
    public void askGameMode() {
        SceneControllerInterface ngsc = new NewGameSceneController();
        SceneController.setCurrentController(ngsc);
        ngsc.setGUI(this);

        Platform.runLater(() -> {
            try {
                SceneController.switchScene(stage, "NewGameScene", ngsc);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public void askPlayerNumber() {
        Platform.runLater(() -> SceneController.switchScene(stage));
    }

    @Override
    public void askWizard(List<Wizard> availableWizards) {
        SceneControllerInterface wsc = new WizardSceneController();
        SceneController.setCurrentController(wsc);
        wsc.setGUI(this);

        Platform.runLater(() -> {
            try {
                SceneController.switchScene(stage, "WizardScene", wsc);
                ((WizardSceneController) wsc).setAvailableWizards(availableWizards);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public void askAssistant(List<Assistant> availableAssistants, List<Assistant> discardedAssistants) {
        SceneControllerInterface bsc = getBoardSceneController();

        Platform.runLater(() -> {
                ((BoardSceneController) bsc).setAssistants(availableAssistants, discardedAssistants);
                ((BoardSceneController) bsc).enableAssistantBox();
            });
    }

    @Override
    public void askAction(List<ActionType> possibleActions) {

    }

    @Override
    public void askStudent(List<Color> availableColors) {
        Platform.runLater(() -> SceneController.switchScene(stage));
        ((BoardSceneController)SceneController.getCurrentController()).enableSchoolBoard();
        //...
    }

    @Override
    public void askPlace(int maxArchis) {

    }

    @Override
    public int askArchipelago(int maxArchis) {
        Platform.runLater(() -> SceneController.switchScene(stage));
        ((BoardSceneController)SceneController.getCurrentController()).enableArchipelagos();
        //...
        return -1;
    }

    @Override
    public void askCharacter(LightBoard board) {
        Platform.runLater(() -> SceneController.switchScene(stage));
        ((BoardSceneController)SceneController.getCurrentController()).enableCharactersBox();
    }

    @Override
    public void askMNSteps(int maxMNSteps) {

    }

    @Override
    public void askCloud(List<Integer> availableClouds) {
        SceneControllerInterface bsc = getBoardSceneController();

        Platform.runLater(() -> {
            ((BoardSceneController) bsc).enableCloudBox();
        });
    }

    @Override
    public void displayMessage(String message) {
        SceneControllerInterface lsc = new LoadingSceneController();
        SceneController.setCurrentController(lsc);
        lsc.setGUI(this);

        Platform.runLater(() -> {
            try {
                SceneController.switchScene(stage, "LoadingScene", lsc);
                ((LoadingSceneController)lsc).changeLabel(message);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public void displayDisconnectionMessage(String disconnectedNickname, String message) {
        errorDialog(disconnectedNickname + message);
    }

    @Override
    public void displayErrorAndExit(String message) {
        errorDialog(message);
        System.exit(1);
    }

    @Override
    public void printBoard(LightBoard board) {
        SceneControllerInterface bsc = getBoardSceneController();
        SceneController.setCurrentController(bsc);
        bsc.setGUI(this);
        ((BoardSceneController) bsc).setLightBoard(board);

        Platform.runLater(() -> {
            try {
                SceneController.switchScene(stage, "BoardScene", bsc);

                if (firstPrintBoard) {
                    stage.setMaximized(true);
                    //stage.setFullScreenExitHint("Press ESC to exit fullscreen");
                    stage.setMinHeight(720);
                    stage.setMinWidth(1280);
                    stage.setResizable(true);
                    firstPrintBoard = false;
                }

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public void displayEndgameResult(String winner, String condition) {

    }

    public static MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }

    public void errorDialog(String error) {
        Alert errorDialog = new Alert(Alert.AlertType.ERROR);
        errorDialog.setTitle("Game Error");
        errorDialog.setHeaderText("Error!");
        errorDialog.setContentText(error);
        errorDialog.showAndWait();
    }

    public void closeWindow(Stage stage) {
        Alert alertDialog = new Alert(Alert.AlertType.CONFIRMATION);
        alertDialog.setTitle("Exiting");
        alertDialog.setHeaderText("You're about to exit");
        alertDialog.setContentText("Do you want to close the game?");

        if (alertDialog.showAndWait().get() == ButtonType.OK) {
            System.out.println("Exit confirmed.");
            stage.close();
            System.exit(0);                 //FIXME: idk if this is necessary
        }
    }

    private BoardSceneController getBoardSceneController() {
        BoardSceneController bsc;
        try {
            bsc = (BoardSceneController) SceneController.getCurrentController();
        } catch (ClassCastException e) {
            bsc = new BoardSceneController();
            BoardSceneController finalBsc = bsc;
            Platform.runLater(() -> {
                try {
                    SceneController.switchScene(stage, "BoardScene", finalBsc);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            });
        }
        return bsc;
    }


    public void setClient(Client client) {
        this.client = client;
    }

    public Client getClient() {
        return client;
    }

    public Stage getStage() {
        return stage;
    }
}