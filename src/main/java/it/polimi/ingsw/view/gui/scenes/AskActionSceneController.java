package it.polimi.ingsw.view.gui.scenes;

import it.polimi.ingsw.controller.actions.ActionType;
import it.polimi.ingsw.network.messages.clientMessages.ActionReply;
import it.polimi.ingsw.view.gui.GUI;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class AskActionSceneController implements SceneControllerInterface {

    @FXML
    private Label actionLabel;
    @FXML
    private ListView<String> actionList;
    @FXML
    private Button confirmButton;

    private List<String> possibleActionList = new ArrayList<>();
    private String selectedAction;
    private GUI gui;

    @FXML
    private void initialize() {
        confirmButton.setDisable(true);
        confirmButton.requestFocus();
    }

    @FXML
    private void confirm() {
        gui.getClient().sendMsgToServer(new ActionReply(possibleActionList.indexOf(selectedAction)));
        confirmButton.setDisable(true);
        ((Stage) confirmButton.getScene().getWindow()).close();
    }

    @FXML
    private void clickOnList() {
        selectedAction = actionList.getSelectionModel().selectedItemProperty().get();
        actionLabel.setText(selectedAction);
        confirmButton.setDisable(false);
    }

    public void setPossibleActions(List<ActionType> possibleActions) {
        String action;

        for (int i = 0; i < possibleActions.size(); i++) {
            action = possibleActions.get(i).getAction().replace("_", " ").replace("ACTION", "");

            if (!possibleActions.contains(action))
                possibleActionList.add(action);
        }

        actionList.getItems().addAll(possibleActionList);
    }

    /**
     * @param gui
     */
    @Override
    public void setGUI(GUI gui) {
        this.gui = gui;
    }
}