package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.controller.actions.ActionType;
import it.polimi.ingsw.model.Assistant;
import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Wizard;
import it.polimi.ingsw.network.client.Client;
import it.polimi.ingsw.network.messages.clientMessages.StudentReply;
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
    private BoardSceneController bsc = new BoardSceneController();
    private Color studColor;
    private int archiIndex;
    private boolean firstPrintBoard = true;
    private final Object lock = new Object();
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
        Platform.runLater(() -> {
                bsc.setAssistants(availableAssistants, discardedAssistants);
                infoDialog("It's your turn to choose the assistant!");
                bsc.enableAssistantBox();
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

                SceneController.popUpScene(actionStage, "AskActionScene", aasc);
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
        askColor(availableColors, "Select the color of the student you would like to move");
        getClient().sendMsgToServer(new StudentReply(studColor));
    }

    public void askColor(List<Color> availableColors, String text) {
        SceneControllerInterface acsc = new AskColorSceneController();
        SceneController.setCurrentController(acsc);
        acsc.setGUI(this);

        Platform.runLater(() -> {
            try {
                Stage colorStage = new Stage();
                colorStage.initStyle(StageStyle.UTILITY);
                colorStage.setResizable(false);
                colorStage.setAlwaysOnTop(true);

                SceneController.popUpScene(colorStage, "AskColorScene", acsc);
                ((AskColorSceneController) acsc).setAvailableColors(availableColors);
                ((AskColorSceneController) acsc).setLabel(text);

                colorStage.setOnCloseRequest(event -> {
                    warningDialog("You have to choose the color first in order to close this panel");
                    event.consume();
                });

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        synchronized (lock) {
            try {
                lock.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Asks the user where they want to move the student.
     *
     * @param maxArchis            the number of Archipelagos.
     */
    @Override
    public void askPlace(int maxArchis) {
        SceneControllerInterface apsc = new AskPlaceSceneController();
        SceneController.setCurrentController(apsc);
        apsc.setGUI(this);

        Platform.runLater(() -> {
            try {
                Stage placeStage = new Stage();
                placeStage.initStyle(StageStyle.UTILITY);
                placeStage.setResizable(false);
                placeStage.setAlwaysOnTop(true);

                SceneController.popUpScene(placeStage, "AskPlaceScene", apsc);

                placeStage.setOnCloseRequest(event -> {
                    warningDialog("You have to choose the place first in order to close this panel");
                    event.consume();
                });

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    /**
     * Enables the archipelagos.
     */
    public void enableArchiBox() {
        Platform.runLater(() -> {
            bsc.enableArchipelagos();
        });
    }

    /**
     * Asks the user the character they want to play. Then, based on the selected
     * one, asks the proper values in order to activate their effect.
     *
     * @param board                a LightBoard to access the available characters.
     */
    @Override
    public void askCharacter(LightBoard board) {
        Platform.runLater(() -> bsc.enableCharactersBox());
        //BoardSceneController takes care of sending the reply
    }

    /**
     * Asks the user the number of steps they want Mother Nature to take.
     *
     * @param maxMNSteps           the limit of steps.
     */
    @Override
    public void askMNSteps(int maxMNSteps) {
        Platform.runLater(() -> bsc.enableArchisForMN(maxMNSteps));
        //BoardSceneController takes care of sending the reply
    }

    /**
     * Asks the user the cloud they want to draw from.
     *
     * @param availableClouds      a List of available Clouds.
     */
    @Override
    public void askCloud(List<Integer> availableClouds) {
        Platform.runLater(() -> bsc.enableCloudBox());
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

        synchronized (lock) {
            try {
                lock.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.exit(0);
    }

    /**
     * Displays an error message and exits.
     *
     * @param message              the error message to be displayed.
     */
    @Override
    public void displayErrorAndExit(String message) {
        errorDialog(message);

        synchronized (lock) {
            try {
                lock.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.exit(0);
    }

    /**
     * Prints the current board (archipelagos, clouds, characters, school boards).
     *
     * @param board                the LightBoard representing the current status.
     */
    @Override
    public void printBoard(LightBoard board) {
        SceneController.setCurrentController(bsc);
        bsc.setGUI(this);
        bsc.setBoard(board);

        Platform.runLater(() -> {
            try {
                if (firstPrintBoard) {
                    stage.setX(Screen.getPrimary().getVisualBounds().getMinX());
                    stage.setY(Screen.getPrimary().getVisualBounds().getMinY());
                    stage.setWidth(Screen.getPrimary().getVisualBounds().getWidth());
                    stage.setHeight(Screen.getPrimary().getVisualBounds().getHeight());
                    stage.setMinHeight(700);
                    stage.setMinWidth(1250);
                    stage.sizeToScene();
                    stage.centerOnScreen();
                    stage.setResizable(true);
                    stage.setMaximized(true);

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
        SceneControllerInterface rsc = new ResultSceneController();
        SceneController.setCurrentController(rsc);
        rsc.setGUI(this);

        Platform.runLater(() -> {
            try {
                Stage resultStage = new Stage();
                resultStage.initStyle(StageStyle.UTILITY);
                resultStage.setResizable(false);
                resultStage.setAlwaysOnTop(true);

                SceneController.popUpScene(resultStage, "ResultScene", rsc);
                if (getClient().getNickname().equals(winner)) {
                    ((ResultSceneController)rsc).setWinner("Congratulations, you WON!");
                } else {
                    ((ResultSceneController)rsc).setWinner("You lost!");
                    ((ResultSceneController)rsc).setSubtitle(winner + "WINS! " + condition);
                }

                resultStage.setOnCloseRequest(event -> {
                    closeWindow(resultStage);
                });

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
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
        Platform.runLater(() -> {
            Alert errorDialog = new Alert(Alert.AlertType.ERROR);
            errorDialog.setTitle("Game Error");
            errorDialog.setHeaderText("Error!");
            errorDialog.setContentText(error);
            ((Stage) errorDialog.getDialogPane().getScene().getWindow()).setAlwaysOnTop(true);
            errorDialog.showAndWait();

            synchronized (lock) {
                lock.notify();
            }

        });
    }

    /**
     * Shows a warning dialog to the user.
     *
     * @param warning              the specific warning.
     */
    public void warningDialog(String warning) {
        Alert warningDialog = new Alert(Alert.AlertType.WARNING);
        warningDialog.setTitle("Game Warning");
        warningDialog.setHeaderText("Careful");
        warningDialog.setContentText(warning);
        ((Stage)warningDialog.getDialogPane().getScene().getWindow()).setAlwaysOnTop(true);
        ((Stage)warningDialog.getDialogPane().getScene().getWindow()).getIcons().add(new Image("/img/icon.png"));
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
        infoDialog.setHeaderText("Your next step");
        infoDialog.setContentText(info);
        ((Stage)infoDialog.getDialogPane().getScene().getWindow()).setAlwaysOnTop(true);
        ((Stage)infoDialog.getDialogPane().getScene().getWindow()).getIcons().add(new Image("/img/icon.png"));
        infoDialog.showAndWait();
    }

    public void charInfoDialog(String charInfo) {
        Platform.runLater(() -> {
            infoDialog(charInfo);

            synchronized (lock) {
                lock.notify();
            }
        });

        synchronized (lock) {
            try {
                lock.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
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
        ((Stage)alertDialog.getDialogPane().getScene().getWindow()).setAlwaysOnTop(true);
        ((Stage)alertDialog.getDialogPane().getScene().getWindow()).getIcons().add(new Image("/img/icon.png"));

        if (alertDialog.showAndWait().get() == ButtonType.OK) {
            System.out.println("Exit confirmed.");
            stage.close();
            System.exit(0);                 //FIXME: idk if this is necessary
        }
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

    public void setStudColor(Color studColor) {
        this.studColor = studColor;
    }

    public Color getStudColor() {
        return studColor;
    }

    public Object getLock() {
        return lock;
    }

    public void setArchiIndex(int archiIndex) {
        this.archiIndex = archiIndex;
    }
}