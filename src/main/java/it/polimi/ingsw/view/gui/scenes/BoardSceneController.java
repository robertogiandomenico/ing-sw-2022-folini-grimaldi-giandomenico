package it.polimi.ingsw.view.gui.scenes;

import it.polimi.ingsw.model.Assistant;
import it.polimi.ingsw.model.Student;
import it.polimi.ingsw.model.TowerColor;
import it.polimi.ingsw.model.Wizard;
import it.polimi.ingsw.network.messages.clientMessages.ChooseAssistantReply;
import it.polimi.ingsw.view.gui.GUI;
import it.polimi.ingsw.view.utilities.MatrixOperations;
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
    private TabPane othersPlayerPane;
    private GUI gui;
    private LightBoard lightBoard;
    private LightSchoolBoard thisPlayerBoard;

    @FXML
    public void initialize() {
        initializeCharacters();
        /*initializeThisPlayer();
        initializeOtherPlayers();
        initializeArchipelagos();
        initializeClouds();
        initializeCoinsSupply();*/

        charactersBox.setDisable(true);
        assistantBox.setDisable(true);
        archipelagosBox.setDisable(true);
        cloudsBox.setDisable(true);
        thisPlayerPane.setDisable(true);
    }

    public void initializeCharacters() {
        try {
            LightCharacter[] selectedCharacters = lightBoard.getSelectedCharacters();

            for (int i = 0; i < 3; i++) {
                ((ImageView)((AnchorPane)charactersBox.getChildren().get(i)).getChildren().get(0)).setImage(new Image(getClass().getResourceAsStream("/img/characters/" + selectedCharacters[i].getName().toLowerCase() + ".png")));

                charactersBox.getChildren().get(i).setId(selectedCharacters[i].getName());

                switch (selectedCharacters[i].getName()) {
                    case "GrannyGrass":
                        ImageView noEntryTile = new ImageView("/img/tiles/noEntryTile.png");
                        Label noEntryTileLeft = new Label();

                        noEntryTile.setFitHeight(28);
                        noEntryTile.setFitWidth(28);
                        noEntryTile.setY(30);
                        noEntryTile.setX(10);
                        noEntryTileLeft.setLayoutX(45);
                        noEntryTileLeft.setLayoutY(30);

                        noEntryTileLeft.setText("x" + selectedCharacters[i].getNoEntryTiles());
                        ((AnchorPane)charactersBox.getChildren().get(i)).getChildren().add(noEntryTile);
                        ((AnchorPane)charactersBox.getChildren().get(i)).getChildren().add(noEntryTileLeft);
                        break;

                    case "Monk":
                        ImageView s1 = new ImageView(),
                                  s2 = new ImageView(),
                                  s3 = new ImageView(),
                                  s4 = new ImageView();
                        s1.setImage(displayStudent(selectedCharacters[i].getStudents()[0]));
                        s2.setImage(displayStudent(selectedCharacters[i].getStudents()[1]));
                        s3.setImage(displayStudent(selectedCharacters[i].getStudents()[2]));
                        s4.setImage(displayStudent(selectedCharacters[i].getStudents()[3]));
                        ((AnchorPane)charactersBox.getChildren().get(i)).getChildren().add(s1);
                        ((AnchorPane)charactersBox.getChildren().get(i)).getChildren().add(s2);
                        ((AnchorPane)charactersBox.getChildren().get(i)).getChildren().add(s3);
                        ((AnchorPane)charactersBox.getChildren().get(i)).getChildren().add(s4);
                        break;

                    case "Jester":
                        ImageView s5 = new ImageView(),
                                  s6 = new ImageView(),
                                  s7 = new ImageView(),
                                  s8 = new ImageView(),
                                  s9 = new ImageView(),
                                  s10 = new ImageView();
                        s5.setImage(displayStudent(selectedCharacters[i].getStudents()[0]));
                        s6.setImage(displayStudent(selectedCharacters[i].getStudents()[1]));
                        s7.setImage(displayStudent(selectedCharacters[i].getStudents()[2]));
                        s8.setImage(displayStudent(selectedCharacters[i].getStudents()[3]));
                        s9.setImage(displayStudent(selectedCharacters[i].getStudents()[4]));
                        s10.setImage(displayStudent(selectedCharacters[i].getStudents()[5]));
                        ((AnchorPane)charactersBox.getChildren().get(i)).getChildren().add(s5);
                        ((AnchorPane)charactersBox.getChildren().get(i)).getChildren().add(s6);
                        ((AnchorPane)charactersBox.getChildren().get(i)).getChildren().add(s7);
                        ((AnchorPane)charactersBox.getChildren().get(i)).getChildren().add(s8);
                        ((AnchorPane)charactersBox.getChildren().get(i)).getChildren().add(s9);
                        ((AnchorPane)charactersBox.getChildren().get(i)).getChildren().add(s10);
                        break;

                    case "SpoiledPrincess":
                        ImageView s11 = new ImageView(),
                                  s12 = new ImageView(),
                                  s13 = new ImageView(),
                                  s14 = new ImageView();
                        s11.setImage(displayStudent(selectedCharacters[i].getStudents()[0]));
                        s12.setImage(displayStudent(selectedCharacters[i].getStudents()[1]));
                        s13.setImage(displayStudent(selectedCharacters[i].getStudents()[2]));
                        s14.setImage(displayStudent(selectedCharacters[i].getStudents()[3]));
                        ((AnchorPane)charactersBox.getChildren().get(i)).getChildren().add(s11);
                        ((AnchorPane)charactersBox.getChildren().get(i)).getChildren().add(s12);
                        ((AnchorPane)charactersBox.getChildren().get(i)).getChildren().add(s13);
                        ((AnchorPane)charactersBox.getChildren().get(i)).getChildren().add(s14);
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

        ((Label) thisPlayerPane.getChildren().get(1)).setText(thisPlayerBoard.getPlayer().getNickname());
        ((ImageView) thisPlayerPane.getChildren().get(2)).setImage(getWizardIcon(thisPlayerBoard.getPlayer().getSelectedWizard()));

        try {
            ((ImageView) thisPlayerPane.getChildren().get(3)).setImage(new Image(getClass().getResourceAsStream("/img/assistant/" + thisPlayerBoard.getPlayer().getDiscardPile().name().toLowerCase() + ".png")));
        } catch (NullPointerException e) {
            ((ImageView) thisPlayerPane.getChildren().get(3)).setImage(new Image(getClass().getResourceAsStream("/img/blank.png")));
        }

        for (int i = 0; i < thisPlayerBoard.getEntrance().length; i++) {
            ((ImageView)((AnchorPane) thisPlayerPane.getChildren().get(4)).getChildren().get(i)).setImage(displayStudent(thisPlayerBoard.getEntrance()[i]));
        }

        for (int i = 0; i < thisPlayerBoard.getTowersLeft(); i++) {
            ((ImageView)((AnchorPane) thisPlayerPane.getChildren().get(5)).getChildren().get(i)).setImage(getTowerIcon(thisPlayerBoard.getPlayer().getTowerColor()));
        }

        for (int i = 0; i < thisPlayerBoard.getProfessorTable().length; i++) {
            ((VBox) thisPlayerPane.getChildren().get(6)).getChildren().get(i).setVisible(thisPlayerBoard.getProfessorTable()[i]);
        }

        ((Label)((AnchorPane)thisPlayerPane.getChildren().get(7)).getChildren().get(1)).setText("x" + thisPlayerBoard.getPlayer().getCoins());

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

    public void initializeArchipelagos() {
        for (int i = 0; i < lightBoard.getArchipelagos().size(); i++) {

            //set tower
            try {
                ((ImageView) ((AnchorPane) archipelagosBox.getChildren().get(i)).getChildren().get(1)).setImage(new Image(getClass().getResourceAsStream("/img/towers/" + lightBoard.getArchipelagos().get(i).getTowerColor().name().toLowerCase() + ".png")));
            } catch (NullPointerException e) {
                ((ImageView) ((AnchorPane) archipelagosBox.getChildren().get(i)).getChildren().get(1)).setImage(new Image(getClass().getResourceAsStream("/img/blank.png")));
            }

            //set mother nature
            ((AnchorPane) archipelagosBox.getChildren().get(i)).getChildren().get(2).setVisible(lightBoard.getArchipelagos().get(i).isMNPresent());

            //set no entry tile
            ((AnchorPane) archipelagosBox.getChildren().get(i)).getChildren().get(8).setVisible(lightBoard.getArchipelagos().get(i).isNoEntryTilePresent());

            //set students number and visibility
            for (int j = 0; j < 5; j++) {
                ((Label)((AnchorPane)((AnchorPane)archipelagosBox.getChildren().get(i)).getChildren().get(j+3)).getChildren().get(1)).setText(Integer.toString(MatrixOperations.columnSum(lightBoard.getArchipelagos().get(i).getIslands(), j)));

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
                //TODO: fix the 4th pawn (in scenebuilder) in case this works fine
            }
        }
    }

    public void initializeCoinsSupply() {
        if (lightBoard.getSelectedCharacters() != null) {  //if it's expert mode
            ((Label) coinsSupplyBox.getChildren().get(1)).setText("x" + lightBoard.getCoinsSupply());
        } else {
            coinsSupplyBox.setDisable(true);
            coinsSupplyBox.setVisible(false);
        }
    }

    public void initializeOtherPlayers() {
        LightSchoolBoard[] otherPlayers = new LightSchoolBoard[lightBoard.getSchoolBoards().size()-1];

        for (int i = 0; i < lightBoard.getSchoolBoards().size(); i++) {
            if (!lightBoard.getSchoolBoards().get(i).getPlayer().getNickname().equals(gui.getClient().getNickname())) {
                otherPlayers[i] = lightBoard.getSchoolBoards().get(i);
            }
        }

        for (int i = 0; i < otherPlayers.length; i++) {

            othersPlayerPane.getTabs().get(i).setText(otherPlayers[i].getPlayer().getNickname());

            ((Label)((AnchorPane)othersPlayerPane.getTabs().get(i).getContent()).getChildren().get(1)).setText(otherPlayers[i].getPlayer().getNickname());
            ((ImageView)((AnchorPane)othersPlayerPane.getTabs().get(i).getContent()).getChildren().get(2)).setImage(getWizardIcon(otherPlayers[i].getPlayer().getSelectedWizard()));

            try {
                ((ImageView)((AnchorPane)othersPlayerPane.getTabs().get(i).getContent()).getChildren().get(3)).setImage(new Image(getClass().getResourceAsStream("/img/assistant/" + otherPlayers[i].getPlayer().getDiscardPile().name().toLowerCase() + ".png")));
            } catch (NullPointerException e){
                ((ImageView)((AnchorPane)othersPlayerPane.getTabs().get(i).getContent()).getChildren().get(3)).setImage(new Image(getClass().getResourceAsStream("/img/blank.png")));
            }

            for (int j = 0; j < otherPlayers[i].getEntrance().length; j++) {
                ((ImageView)((AnchorPane)((AnchorPane)othersPlayerPane.getTabs().get(i).getContent()).getChildren().get(5)).getChildren().get(j)).setImage(displayStudent(otherPlayers[i].getEntrance()[j]));
            }

            for (int j = 0; j < otherPlayers[i].getTowersLeft(); j++) {
                ((ImageView)((AnchorPane)((AnchorPane)othersPlayerPane.getTabs().get(i).getContent()).getChildren().get(4)).getChildren().get(j)).setImage(getTowerIcon(otherPlayers[i].getPlayer().getTowerColor()));
            }

            for (int j = 0; j < otherPlayers[i].getProfessorTable().length; j++) {
                ((VBox)((AnchorPane)othersPlayerPane.getTabs().get(i).getContent()).getChildren().get(6)).getChildren().get(j).setVisible(otherPlayers[i].getProfessorTable()[j]);
            }

            ((Label)((AnchorPane)((AnchorPane)othersPlayerPane.getTabs().get(i).getContent()).getChildren().get(7)).getChildren().get(1)).setText("x" + otherPlayers[i].getPlayer().getCoins());

        }
    }

    private Image displayStudent(Student student) {
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

    private void chooseAssistant(MouseEvent event) {
        if (event.getButton().equals(MouseButton.PRIMARY)) {
            String selectedAssistant = event.getPickResult().getIntersectedNode().getId();
            gui.getClient().sendMsgToServer(new ChooseAssistantReply(getAssistantByName(selectedAssistant)));
            assistantBox.setDisable(true);
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
