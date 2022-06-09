package it.polimi.ingsw.view.gui.scenes;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.network.messages.clientMessages.ChooseAssistantReply;
import it.polimi.ingsw.network.messages.clientMessages.CloudReply;
import it.polimi.ingsw.view.gui.GUI;
import it.polimi.ingsw.view.utilities.MatrixOperations;
import it.polimi.ingsw.view.utilities.lightclasses.LightArchi;
import it.polimi.ingsw.view.utilities.lightclasses.LightBoard;
import it.polimi.ingsw.view.utilities.lightclasses.LightCharacter;
import it.polimi.ingsw.view.utilities.lightclasses.LightSchoolBoard;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.List;

public class BoardSceneController implements SceneControllerInterface {
    @FXML
    private HBox charactersBox;
    @FXML
    private GridPane assistantBox;
    @FXML
    private GridPane archipelagosBox;
    @FXML
    private HBox cloudsBox;
    @FXML
    private AnchorPane coinsSupplyBox;
    @FXML
    private AnchorPane thisPlayerPane;
    @FXML
    private TabPane otherPlayersPane;
    private GUI gui;
    private LightBoard lightBoard;
    private LightSchoolBoard thisPlayerBoard;

    @FXML
    public void initialize() {
        initializeCharacters(lightBoard.getSelectedCharacters());
        initializeThisPlayer();
        initializeOtherPlayers();
        initializeArchipelagos(lightBoard.getArchipelagos());
        initializeClouds();
        initializeCoinsSupply(lightBoard.getCoinsSupply());

        charactersBox.setDisable(true);
        assistantBox.setDisable(true);
        archipelagosBox.setDisable(true);
        cloudsBox.setDisable(true);
        thisPlayerPane.setDisable(true);
    }

    public void initializeCharacters(LightCharacter[] selectedCharacters) {
        try {
            for (int i = 0; i < 3; i++) {
                ((ImageView)((AnchorPane)charactersBox.getChildren().get(i)).getChildren().get(0)).setImage(new Image(getClass().getResourceAsStream("/img/characters/" + selectedCharacters[i].getName().toLowerCase() + ".png")));

                charactersBox.getChildren().get(i).setId(selectedCharacters[i].getName());

                switch (selectedCharacters[i].getName()) {
                    case "GrannyGrass":
                        Label noEntryTileLeft = new Label();

                        noEntryTileLeft.setLayoutX(41);
                        noEntryTileLeft.setLayoutY(107);
                        noEntryTileLeft.setText("x" + selectedCharacters[i].getNoEntryTiles());
                        noEntryTileLeft.setStyle("-fx-font-family: \"Metamorphous\"");

                        ((AnchorPane)charactersBox.getChildren().get(i)).getChildren().add(noEntryTileLeft);
                        break;

                    case "Monk":

                    case "SpoiledPrincess":

                    case "Jester":
                        for (int j = 0; j < selectedCharacters[i].getStudents().length; j++) {
                            ((ImageView)((AnchorPane)((AnchorPane)charactersBox.getChildren().get(i)).getChildren().get(1)).getChildren().get(j)).setImage(displayStudent(selectedCharacters[i].getStudents()[j]));
                        }
                        break;

                    default:
                        break;
                }
            }
        } catch (NullPointerException e) {
            charactersBox.setDisable(true);
            charactersBox.setVisible(false);
        }
    }

    public void initializeThisPlayer() {
        for (LightSchoolBoard lsb : lightBoard.getSchoolBoards()) {
            if (lsb.getPlayer().getNickname().equals(gui.getClient().getNickname())) {
                thisPlayerBoard = lsb;
                break;
            }
        }

        //set wizard image and nickname
        ((Label) thisPlayerPane.getChildren().get(1)).setText(thisPlayerBoard.getPlayer().getNickname());
        ((ImageView) thisPlayerPane.getChildren().get(2)).setImage(getWizardIcon(thisPlayerBoard.getPlayer().getSelectedWizard()));

        //set available assistants
        try {
            ((ImageView) thisPlayerPane.getChildren().get(3)).setImage(new Image(getClass().getResourceAsStream("/img/assistant/" + thisPlayerBoard.getPlayer().getDiscardPile().name().toLowerCase() + ".png")));
        } catch (NullPointerException e) {
            ((ImageView) thisPlayerPane.getChildren().get(3)).setImage(new Image(getClass().getResourceAsStream("/img/blank.png")));
        }

        //set entrance
        for (int i = 0; i < thisPlayerBoard.getEntrance().length; i++) {
            ((ImageView)((AnchorPane) thisPlayerPane.getChildren().get(4)).getChildren().get(i)).setImage(displayStudent(thisPlayerBoard.getEntrance()[i]));
        }

        //set towers left
        for (int i = 0; i < thisPlayerBoard.getTowersLeft(); i++) {
            ((ImageView)((AnchorPane) thisPlayerPane.getChildren().get(5)).getChildren().get(i)).setImage(getTowerIcon(thisPlayerBoard.getPlayer().getTowerColor()));
        }

        //set professor table
        for (int i = 0; i < thisPlayerBoard.getProfessorTable().length; i++) {
            ((VBox) thisPlayerPane.getChildren().get(6)).getChildren().get(i).setVisible(thisPlayerBoard.getProfessorTable()[i]);
        }

        //set coins
        if(lightBoard.getSelectedCharacters() != null) {
            ((Label)((AnchorPane)thisPlayerPane.getChildren().get(7)).getChildren().get(1)).setText("x" + thisPlayerBoard.getPlayer().getCoins());
        } else {
            thisPlayerPane.getChildren().get(7).setDisable(true);
            thisPlayerPane.getChildren().get(7).setVisible(false);
        }

    }

    private Image getTowerIcon(TowerColor towerColor) {
        switch (towerColor) {
            case GREY:
                return new Image(getClass().getResourceAsStream("/img/towers/upgrey.png"));
            case BLACK:
                return new Image(getClass().getResourceAsStream("/img/towers/upblack.png"));
            case WHITE:
                return new Image(getClass().getResourceAsStream("/img/towers/upwhite.png"));
            default:
                return new Image(getClass().getResourceAsStream("/img/blank.png"));
        }
    }

    private Image getWizardIcon(Wizard wizard) {
        switch (wizard) {
            case SKYWIZARD:
                return new Image(getClass().getResourceAsStream("/img/wizards/s_icon.png"));
            case ARTICWIZARD:
                return new Image(getClass().getResourceAsStream("/img/wizards/a_icon.png"));
            case DESERTWIZARD:
                return new Image(getClass().getResourceAsStream("/img/wizards/d_icon.png"));
            case FORESTWIZARD:
                return new Image(getClass().getResourceAsStream("/img/wizards/f_icon.png"));
            default:
                return new Image(getClass().getResourceAsStream("/img/blank.png"));
        }
    }

    public void initializeArchipelagos(List<LightArchi> archipelagos) {
        for (int i = 0; i < archipelagos.size(); i++) {

            //set tower
            try {
                ((ImageView) ((AnchorPane) archipelagosBox.getChildren().get(i)).getChildren().get(1)).setImage(new Image(getClass().getResourceAsStream("/img/towers/" + archipelagos.get(i).getTowerColor().name().toLowerCase() + ".png")));
            } catch (NullPointerException e) {
                ((ImageView) ((AnchorPane) archipelagosBox.getChildren().get(i)).getChildren().get(1)).setImage(new Image(getClass().getResourceAsStream("/img/blank.png")));
            }

            //set mother nature
            ((AnchorPane) archipelagosBox.getChildren().get(i)).getChildren().get(2).setVisible(archipelagos.get(i).isMNPresent());

            //set no entry tile
            ((AnchorPane) archipelagosBox.getChildren().get(i)).getChildren().get(8).setVisible(archipelagos.get(i).isNoEntryTilePresent());

            //set students number and visibility
            for (int j = 0; j < 5; j++) {
                ((Label)((AnchorPane)((AnchorPane)archipelagosBox.getChildren().get(i)).getChildren().get(j+3)).getChildren().get(1)).setText(Integer.toString(MatrixOperations.columnSum(archipelagos.get(i).getIslands(), j)));

                if (((Label)((AnchorPane)((AnchorPane)archipelagosBox.getChildren().get(i)).getChildren().get(j+3)).getChildren().get(1)).getText().equals("0"))
                    ((AnchorPane)archipelagosBox.getChildren().get(i)).getChildren().get(j+3).setVisible(false);
                else
                    ((AnchorPane)archipelagosBox.getChildren().get(i)).getChildren().get(j+3).setVisible(true);
            }

        }
    }

    public void initializeClouds() {
        int cloudsNumber = lightBoard.getCloudsNumber();
        if (cloudsNumber == 2) {
            cloudsBox.getChildren().get(2).setDisable(true);
            cloudsBox.getChildren().get(2).setVisible(false);
        }

        for (int i = 0; i < cloudsNumber; i++) {
            for (int j = 0; j < cloudsNumber+1; j++) {
                ((ImageView) ((AnchorPane) cloudsBox.getChildren().get(i)).getChildren().get(j+1)).setImage(displayStudent(lightBoard.getCloud(i)[j]));
                cloudsBox.getChildren().get(i).setId(String.valueOf(i));
            }
        }
    }

    public void initializeCoinsSupply(int coinsSupply) {
        if (lightBoard.getSelectedCharacters() != null) {  //if it's expert mode
            ((Label) coinsSupplyBox.getChildren().get(1)).setText("x" + coinsSupply);
        } else {
            coinsSupplyBox.setDisable(true);
            coinsSupplyBox.setVisible(false);
        }
    }

    public void initializeOtherPlayers() {
        LightSchoolBoard[] otherPlayers = new LightSchoolBoard[lightBoard.getSchoolBoards().size()-1];

        if (lightBoard.getSchoolBoards().size() == 2) {
            otherPlayersPane.getTabs().get(1).setDisable(true);
            otherPlayersPane.getTabs().remove(1);
        }

        for (int i = 0, j = 0; i < lightBoard.getSchoolBoards().size(); i++) {
            if (!lightBoard.getSchoolBoards().get(i).getPlayer().getNickname().equals(gui.getClient().getNickname())) {
                otherPlayers[j] = lightBoard.getSchoolBoards().get(i);
                j++;
            }
        }

        for (int i = 0; i < otherPlayers.length; i++) {

            otherPlayersPane.getTabs().get(i).setText(otherPlayers[i].getPlayer().getNickname());

            ((Label)((AnchorPane)otherPlayersPane.getTabs().get(i).getContent()).getChildren().get(1)).setText(otherPlayers[i].getPlayer().getNickname());
            ((ImageView)((AnchorPane)otherPlayersPane.getTabs().get(i).getContent()).getChildren().get(2)).setImage(getWizardIcon(otherPlayers[i].getPlayer().getSelectedWizard()));

            try {
                ((ImageView)((AnchorPane)otherPlayersPane.getTabs().get(i).getContent()).getChildren().get(3)).setImage(new Image(getClass().getResourceAsStream("/img/assistant/" + otherPlayers[i].getPlayer().getDiscardPile().name().toLowerCase() + ".png")));
            } catch (NullPointerException e){
                ((ImageView)((AnchorPane)otherPlayersPane.getTabs().get(i).getContent()).getChildren().get(3)).setImage(new Image(getClass().getResourceAsStream("/img/blank.png")));
            }

            for (int j = 0; j < otherPlayers[i].getEntrance().length; j++) {
                ((ImageView)((AnchorPane)((AnchorPane)otherPlayersPane.getTabs().get(i).getContent()).getChildren().get(5)).getChildren().get(j)).setImage(displayStudent(otherPlayers[i].getEntrance()[j]));
            }

            for (int j = 0; j < otherPlayers[i].getTowersLeft(); j++) {
                ((ImageView)((AnchorPane)((AnchorPane)otherPlayersPane.getTabs().get(i).getContent()).getChildren().get(4)).getChildren().get(j)).setImage(getTowerIcon(otherPlayers[i].getPlayer().getTowerColor()));
            }

            for (int j = 0; j < otherPlayers[i].getProfessorTable().length; j++) {
                ((VBox)((AnchorPane)otherPlayersPane.getTabs().get(i).getContent()).getChildren().get(6)).getChildren().get(j).setVisible(otherPlayers[i].getProfessorTable()[j]);
            }

            if (lightBoard.getSelectedCharacters() != null) {
                ((Label) ((AnchorPane) ((AnchorPane) otherPlayersPane.getTabs().get(i).getContent()).getChildren().get(7)).getChildren().get(1)).setText("x" + otherPlayers[i].getPlayer().getCoins());
            } else {
                ((AnchorPane)otherPlayersPane.getTabs().get(i).getContent()).getChildren().get(7).setDisable(true);
                ((AnchorPane)otherPlayersPane.getTabs().get(i).getContent()).getChildren().get(7).setVisible(false);
            }
        }
    }

    private Image displayStudent(Student student) {
        try {
            switch (student.getColor()) {
                case RED:
                    return new Image(getClass().getResourceAsStream("/img/pawns/studentred.png"));
                case BLUE:
                    return new Image(getClass().getResourceAsStream("/img/pawns/studentblue.png"));
                case YELLOW:
                    return new Image(getClass().getResourceAsStream("/img/pawns/studentyellow.png"));
                case GREEN:
                    return new Image(getClass().getResourceAsStream("/img/pawns/studentgreen.png"));
                case PINK:
                    return new Image(getClass().getResourceAsStream("/img/pawns/studentpink.png"));
                default:
                    return null;
            }
        } catch (NullPointerException e) {
            return new Image(getClass().getResourceAsStream("/img/blank.png"));
        }
    }

    public void setAssistants(List<Assistant> availableAssistants, List<Assistant> discardedAssistants) {
        int i;
        for (i = 0; i < availableAssistants.size(); i++) {
            ((ImageView) assistantBox.getChildren().get(i)).setImage(new Image(getClass().getResourceAsStream("/img/assistants/" + availableAssistants.get(i).name().toLowerCase() + ".png")));

            assistantBox.getChildren().get(i).setId(availableAssistants.get(i).name().toLowerCase());
            assistantBox.getChildren().get(i).setDisable(discardedAssistants.contains(availableAssistants.get(i)));
        }
        while (i != 10) {
            assistantBox.getChildren().get(i).setDisable(true);
            assistantBox.getChildren().get(i).setVisible(false);
            i++;
        }
    }

    public void enableAssistantBox() {
        assistantBox.setDisable(false);
    }

    public void enableCharactersBox() {
        charactersBox.setDisable(false);
    }

    public void enableSchoolBoard() {
        thisPlayerPane.setDisable(false);
    }

    public void enableArchipelagos() {
        archipelagosBox.setDisable(false);
    }

    public void enableCloudBox() {
        cloudsBox.setDisable(false);
    }

    @FXML
    private void chooseAssistant(MouseEvent event) {
        if (event.getButton().equals(MouseButton.PRIMARY)) {
            String selectedAssistant = event.getPickResult().getIntersectedNode().getId();
            gui.getClient().sendMsgToServer(new ChooseAssistantReply(getAssistantByName(selectedAssistant)));
            assistantBox.setDisable(true);
        }
    }

    @FXML
    private void chooseCloud(MouseEvent event) {
        if (event.getButton().equals(MouseButton.PRIMARY)) {
            int cloudIndex = Integer.parseInt(event.getPickResult().getIntersectedNode().getId());
            gui.getClient().sendMsgToServer(new CloudReply(cloudIndex));
            cloudsBox.setDisable(true);
        }
    }


    private Assistant getAssistantByName(String name) {
        switch (name.toUpperCase()) {
            case "DOG":
                return Assistant.DOG;
            case "CAT":
                return Assistant.CAT;
            case "OSTRICH":
                return Assistant.OSTRICH;
            case "EAGLE":
                return Assistant.EAGLE;
            case "ELEPHANT":
                return Assistant.ELEPHANT;
            case "TURTLE":
                return Assistant.TURTLE;
            case "CHETAAH":
                return Assistant.CHEETAH;
            case "LIZARD":
                return Assistant.LIZARD;
            case "OCTOPUS":
                return Assistant.OCTOPUS;
            case "FOX":
                return Assistant.FOX;
            default:
                return null;
        }
    }

    @FXML
    private void chooseCharacter(MouseEvent event) {
        if (event.getButton().equals(MouseButton.PRIMARY)) {
            String selectedCharacter = event.getPickResult().getIntersectedNode().getId();
            //....
            //gui.getClient().sendMsgToServer(new CharacterReply( , , , ,));
            charactersBox.setDisable(true);
        }
    }




    /**
     * @param gui
     */
    @Override
    public void setGUI(GUI gui) {
        this.gui = gui;
    }

    public void setLightBoard(LightBoard lightBoard) {
        this.lightBoard = lightBoard;
    }
}
