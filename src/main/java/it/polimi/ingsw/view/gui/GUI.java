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
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.IOException;
import java.util.List;

/**
 * This class is an implementation of the {@link ViewInterface}, used to give
 * the user the possibility to play the game via Graphical User Interface.
 */
public class GUI extends Application implements ViewInterface {
    private Client client;
    private Stage stage;
    protected static MediaPlayer mediaPlayer;
    private boolean firstPrintBoard = true;
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Starts the guided user interface.
     *
     * @param stage                the Stage to set.
     */
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

    /**
     * Asks server information (address and port) to the user and checks their
     * validity.
     *
     * @return                     the Client created.
     */
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

    /**
     * Asks a nickname to the user.
     */
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

    /**
     * Asks the name of a game to the user.
     */
    @Override
    public void askGameName() {
        Platform.runLater(() -> SceneController.switchScene(stage));
    }

    /**
     * Asks the game mode (easy/expert) to the user.
     */
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

    /**
     * Asks the number of players for the game to the user.
     */
    @Override
    public void askPlayerNumber() {
        Platform.runLater(() -> SceneController.switchScene(stage));
    }

    /**
     * Asks the user to choose a wizard among the available ones.
     *
     * @param availableWizards     a Wizard List of Wizards not taken yet.
     */
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

    /**
     * Asks the user to choose the assistant they want to play.
     *
     * @param availableAssistants  an Assistant List of available cards for the Player.
     * @param discardedAssistants  an Assistant List containing the cards chosen by others.
     */
    @Override
    public void askAssistant(List<Assistant> availableAssistants, List<Assistant> discardedAssistants) {
        SceneControllerInterface bsc = getBoardSceneController();

        Platform.runLater(() -> {
                ((BoardSceneController) bsc).setAssistants(availableAssistants, discardedAssistants);
                ((BoardSceneController) bsc).enableAssistantBox();
            });
    }

    /**
     * Asks the user to declare which action they want to go for next.
     *
     * @param possibleActions      an ActionType List of possible actions for the Player.
     */
    @Override
    public void askAction(List<ActionType> possibleActions) {
        SceneControllerInterface aasc = new AskActionSceneController();
        SceneController.setCurrentController(aasc);
        aasc.setGUI(this);

        Platform.runLater(() -> {
            try {
                Stage actionStage = new Stage();
                actionStage.initStyle(StageStyle.UTILITY);
                actionStage.setResizable(false);
                actionStage.setAlwaysOnTop(true);

                SceneController.switchScene(actionStage, "AskActionScene", aasc);
                ((AskActionSceneController) aasc).setPossibleActions(possibleActions);

                actionStage.setOnCloseRequest(event -> {
                    warningDialog("You have to choose the action first in order to close this panel");
                    event.consume();
                });

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    /**
     * Asks the user which student they want to move.
     *
     * @param availableColors      a Color List representing the Students that can be moved.
     */
    @Override
    public void askStudent(List<Color> availableColors) {
        SceneControllerInterface acsc = new AskColorSceneController();
        SceneController.setCurrentController(acsc);
        acsc.setGUI(this);

        Platform.runLater(() -> {
            try {
                Stage colorStage = new Stage();
                colorStage.initStyle(StageStyle.UTILITY);
                colorStage.setResizable(false);
                colorStage.setAlwaysOnTop(true);

                SceneController.switchScene(colorStage, "AskColorScene", acsc);
                ((AskColorSceneController) acsc).setAvailableColors(availableColors);

                colorStage.setOnCloseRequest(event -> {
                    warningDialog("You have to choose the color first in order to close this panel");
                    event.consume();
                });

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    /**
     * Asks the user where they want to move the student.
     *
     * @param maxArchis            the number of Archipelagos.
     */
    @Override
    public void askPlace(int maxArchis) {

    }

    /**
     * Asks the user the index of the archipelago they want to move the
     * student on.
     *
     * @param maxArchis            the number of Archipelagos.
     * @return                     the index of the chosen Archipelago.
     */
    @Override
    public int askArchipelago(int maxArchis) {
        SceneControllerInterface bsc = getBoardSceneController();
        Platform.runLater(() -> ((BoardSceneController) bsc).enableArchipelagos());
        return -1; //this return value is useless, the true value should be returned by the BoardSceneController chooseArchi()
    }

    /**
     * Asks the user the character they want to play. Then, based on the selected
     * one, asks the proper values in order to activate their effect.
     *
     * @param board                a LightBoard to access the available characters.
     */
    @Override
    public void askCharacter(LightBoard board) {
        SceneControllerInterface bsc = getBoardSceneController();
        Platform.runLater(() -> ((BoardSceneController) bsc).enableCharactersBox());
        //TODO: switch case
    }

    /**
     * Asks the user the number of steps they want Mother Nature to take.
     *
     * @param maxMNSteps           the limit of steps.
     */
    @Override
    public void askMNSteps(int maxMNSteps) {
        SceneControllerInterface bsc = getBoardSceneController();
        Platform.runLater(() -> ((BoardSceneController) bsc).enableArchisForMN(maxMNSteps));
    }

    /**
     * Asks the user the cloud they want to draw from.
     *
     * @param availableClouds      a List of available Clouds.
     */
    @Override
    public void askCloud(List<Integer> availableClouds) {
        SceneControllerInterface bsc = getBoardSceneController();
        Platform.runLater(() -> ((BoardSceneController) bsc).enableCloudBox());
    }

    /**
     * Displays a message to the user.
     *
     * @param message              the message to be displayed.
     */
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

    /**
     * Displays a message stating that some player disconnected.
     *
     * @param disconnectedNickname the nickname of the disconnected Player.
     * @param message              the message to be displayed.
     */
    @Override
    public void displayDisconnectionMessage(String disconnectedNickname, String message) {
        errorDialog(disconnectedNickname + message);
    }

    /**
     * Displays an error message and exits.
     *
     * @param message              the error message to be displayed.
     */
    @Override
    public void displayErrorAndExit(String message) {
        errorDialog(message);
        System.exit(1);
    }

    /**
     * Prints the current board (archipelagos, clouds, characters, school boards).
     *
     * @param board                the LightBoard representing the current status.
     */
    @Override
    public void printBoard(LightBoard board) {
        SceneControllerInterface bsc = getBoardSceneController();
        SceneController.setCurrentController(bsc);
        bsc.setGUI(this);
        ((BoardSceneController) bsc).setLightBoard(board);

        Platform.runLater(() -> {
            try {
                if (firstPrintBoard) {
                    stage.setMaximized(true);
                    stage.setX(Screen.getPrimary().getVisualBounds().getMinX());
                    stage.setY(Screen.getPrimary().getVisualBounds().getMinY());
                    stage.setWidth(Screen.getPrimary().getVisualBounds().getWidth());
                    stage.setHeight(Screen.getPrimary().getVisualBounds().getHeight());

                    //stage.setFullScreenExitHint("Press ESC to exit fullscreen");
                    stage.setMinHeight(720);
                    stage.setMinWidth(1280);
                    stage.setResizable(true);
                    firstPrintBoard = false;
                }

                SceneController.switchScene(stage, "BoardScene", bsc);

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    /**
     * Displays the results of the game.
     *
     * @param winner               the nickname of the winner.
     * @param condition            the condition to print.
     */
    @Override
    public void displayEndgameResult(String winner, String condition) {
        //popup victory
        //changeLabel()
        //create ResultController
    }

    /**
     * Returns the media player.
     *
     * @return                     the MediaPlayer of this GUI.
     */
    public static MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }

    /**
     * Shows an error dialog to the user.
     *
     * @param error                the specific game error.
     */
    public void errorDialog(String error) {
        Alert errorDialog = new Alert(Alert.AlertType.ERROR);
        errorDialog.setTitle("Game Error");
        errorDialog.setHeaderText("Error!");
        errorDialog.setContentText(error);
        errorDialog.showAndWait();
    }

    /**
     * Shows a warning dialog to the user.
     *
     * @param warning              the specific warning.
     */
    public void warningDialog(String warning) {
        Alert warningDialog = new Alert(Alert.AlertType.WARNING);
        warningDialog.setTitle("Game Warning");
        warningDialog.setHeaderText("Be careful");
        warningDialog.setContentText(warning);
        ((Stage)warningDialog.getDialogPane().getScene().getWindow()).setAlwaysOnTop(true);
        warningDialog.showAndWait();
    }

    /**
     * Shows an information dialog to the user.
     *
     * @param info                the specific information.
     */
    public void infoDialog(String info) {
        Alert infoDialog = new Alert(Alert.AlertType.INFORMATION);
        infoDialog.setTitle("Game Info");
        infoDialog.setHeaderText("Look, new info");
        infoDialog.setContentText(info);
        infoDialog.showAndWait();
    }

    /**
     * Closes the window notifying the user.
     *
     * @param stage                the Stage to close.
     */
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

    /**
     * Returns a Board Scene Controller, creating one if not already instantiated.
     *
     * @return                     a BoardSceneController.
     */
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

    /**
     * Sets the client.
     *
     * @param client               the Client to set.
     */
    public void setClient(Client client) {
        this.client = client;
    }

    /**
     * Returns the client.
     *
     * @return                     this GUI's Client.
     */
    public Client getClient() {
        return client;
    }

    /**
     * Returns the stage.
     *
     * @return                     this GUI's Stage.
     */
    public Stage getStage() {
        return stage;
    }
}