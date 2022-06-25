package it.polimi.ingsw.view.gui.scenes;

import it.polimi.ingsw.controller.actions.ActionType;
import it.polimi.ingsw.network.messages.clientMessages.ActionReply;
import it.polimi.ingsw.view.gui.GUI;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AskActionSceneController implements SceneControllerInterface {

    @FXML
    private Label actionLabel;
    @FXML
    private ListView<String> actionList;
    @FXML
    private Button confirmButton;
    List<ActionType> possibleActions;
    private List<String> possibleActionList = new ArrayList<>();
    private String selectedAction;
    private GUI gui;

    @FXML
    private void initialize() {
        confirmButton.setDisable(true);
        actionList.setStyle("-fx-font-family: \"Roboto\"");
        actionList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                selectedAction = actionList.getSelectionModel().getSelectedItem();
                actionLabel.setText(selectedAction);
                confirmButton.setDisable(false);
            }
        });
    }

    @FXML
    private void confirm() {
        int index = possibleActions.stream().map(ActionType::getAction).collect(Collectors.toList()).indexOf(selectedAction);
        gui.getClient().sendMsgToServer(new ActionReply(index));
        confirmButton.setDisable(true);
        ((Stage) confirmButton.getScene().getWindow()).close();
    }

    @FXML
    private void typeOnList(KeyEvent e) {
        if (e.getCode().toString().equals("ENTER") && !confirmButton.isDisable())
            confirm();
    }

    public void setPossibleActions(List<ActionType> possibleActions) {
        this.possibleActions = new ArrayList<>(possibleActions);
        String action;

        for (ActionType possibleAction : possibleActions) {
            action = possibleAction.getAction().replace("_", " ").replace("ACTION", "");

            if (!possibleActionList.contains(action))
                possibleActionList.add(action);
        }

        actionList.getItems().addAll(possibleActionList);
    }

    /**
     * @param gui           a GUI
     */
    @Override
    public void setGUI(GUI gui) {
        this.gui = gui;
    }
}