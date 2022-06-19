package it.polimi.ingsw.view.gui.scenes;

import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.view.gui.GUI;
import javafx.css.PseudoClass;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;

public class AskColorSceneController implements SceneControllerInterface {

    @FXML
    private HBox colorBox;
    @FXML
    private VBox greenBox;
    @FXML
    private VBox redBox;
    @FXML
    private VBox yellowBox;
    @FXML
    private VBox pinkBox;
    @FXML
    private VBox blueBox;
    @FXML
    private Button confirmButton;
    @FXML
    private Label label;

    private GUI gui;
    private Color studColor;
    private String chosenColorID;

    private static final PseudoClass focusedElement = PseudoClass.getPseudoClass("focused");

    @FXML
    private void initialize() {
        confirmButton.setDisable(true);
    }

    @FXML
    private void onColorClick(MouseEvent event) {
        if (event.getButton().equals(MouseButton.PRIMARY)) {
            Node selectedColorNode = event.getPickResult().getIntersectedNode().getParent();
            chosenColorID = selectedColorNode.getId();

            if (selectedColorNode.getPseudoClassStates().contains(focusedElement)) {
                selectedColorNode.pseudoClassStateChanged(focusedElement, false);
                confirmButton.setDisable(true);
            } else if (chosenColorID != null){
                selectedColorNode.pseudoClassStateChanged(focusedElement, true);
                confirmButton.setDisable(false);
            }

            try {
                switch (chosenColorID) {
                    case "greenBox":
                        redBox.pseudoClassStateChanged(focusedElement, false);
                        yellowBox.pseudoClassStateChanged(focusedElement, false);
                        pinkBox.pseudoClassStateChanged(focusedElement, false);
                        blueBox.pseudoClassStateChanged(focusedElement, false);
                        break;

                    case "redBox":
                        greenBox.pseudoClassStateChanged(focusedElement, false);
                        yellowBox.pseudoClassStateChanged(focusedElement, false);
                        pinkBox.pseudoClassStateChanged(focusedElement, false);
                        blueBox.pseudoClassStateChanged(focusedElement, false);
                        break;

                    case "yellowBox":
                        greenBox.pseudoClassStateChanged(focusedElement, false);
                        redBox.pseudoClassStateChanged(focusedElement, false);
                        pinkBox.pseudoClassStateChanged(focusedElement, false);
                        blueBox.pseudoClassStateChanged(focusedElement, false);
                        break;

                    case "pinkBox":
                        greenBox.pseudoClassStateChanged(focusedElement, false);
                        redBox.pseudoClassStateChanged(focusedElement, false);
                        yellowBox.pseudoClassStateChanged(focusedElement, false);
                        blueBox.pseudoClassStateChanged(focusedElement, false);
                        break;

                    case "blueBox":
                        greenBox.pseudoClassStateChanged(focusedElement, false);
                        redBox.pseudoClassStateChanged(focusedElement, false);
                        yellowBox.pseudoClassStateChanged(focusedElement, false);
                        pinkBox.pseudoClassStateChanged(focusedElement, false);
                        break;

                    default:
                        break;
                }
            } catch (Exception ignored) {}
        }

    }


    public void setAvailableColors(List<Color> availableColors) {
        if (!availableColors.contains(Color.GREEN))
            colorBox.getChildren().remove(greenBox);
        if (!availableColors.contains(Color.RED))
            colorBox.getChildren().remove(redBox);
        if (!availableColors.contains(Color.YELLOW))
            colorBox.getChildren().remove(yellowBox);
        if (!availableColors.contains(Color.PINK))
            colorBox.getChildren().remove(pinkBox);
        if (!availableColors.contains(Color.BLUE))
            colorBox.getChildren().remove(blueBox);
    }

    @FXML
    private void confirm() {
        try {
            studColor = getSelectedColor();
            if(studColor != null) {
                gui.setStudColor(studColor);

                synchronized (gui.getLock()) {
                    gui.getLock().notify();
                }

                disableAll();
                ((Stage) confirmButton.getScene().getWindow()).close();
            } else
                gui.warningDialog("No color is selected. Try again.");
        } catch (Exception e) {
            gui.warningDialog("No color is selected. Try again.");
        }
    }

    private Color getSelectedColor() {
        switch (chosenColorID) {
            case "greenBox":
                return Color.GREEN;
            case "redBox":
                return Color.RED;
            case "yellowBox":
                return Color.YELLOW;
            case "pinkBox":
                return Color.PINK;
            case "blueBox":
                return Color.BLUE;
            default:
                return null;
        }
    }

    public void setLabel(String text) {
        label.setText(text);
    }

    private void disableAll() {
        confirmButton.setDisable(true);
        colorBox.setDisable(true);
    }


    /**
     * @param gui
     */
    @Override
    public void setGUI(GUI gui) {
        this.gui = gui;
    }
}
