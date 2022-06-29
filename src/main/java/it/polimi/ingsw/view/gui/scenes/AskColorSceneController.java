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

/**
 * This class represents the controller for the scene in which the user is
 * asked to select a color.
 */
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

    /**
     * Initializes the scene.
     */
    @FXML
    private void initialize() {
        confirmButton.setDisable(true);
    }

    /**
     * Puts the focus on the clicked color if it wasn't already focused.
     *
     * @param event             a MouseEvent representing a click.
     */
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
            } catch (NullPointerException ignored) {}
        }

    }


    /**
     * Sets the boxes of the available colors removing those of the colors that
     * can't be chosen.
     *
     * @param availableColors   the Color List of available ones.
     */
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

    /**
     * Confirms the choice setting the selected color.
     */
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

    /**
     * Returns the selected color according to the clicked box.
     *
     * @return                  the selected Color.
     */
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

    /**
     * Sets a label.
     *
     * @param text              the text to be set as label.
     */
    public void setLabel(String text) {
        label.setText(text);
    }

    /**
     * Disables buttons and boxes.
     */
    private void disableAll() {
        confirmButton.setDisable(true);
        colorBox.setDisable(true);
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
