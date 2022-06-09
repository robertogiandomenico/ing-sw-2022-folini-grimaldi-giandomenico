package it.polimi.ingsw.view.utilities.lightclasses;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.effects.GrannyGrassEffect;
import it.polimi.ingsw.model.effects.MonkEffect;
import it.polimi.ingsw.model.effects.SpoiledPrincessEffect;
import it.polimi.ingsw.model.effects.JesterEffect;

import java.io.Serializable;

/**
 * This class is useful to contain character's information.
 * ({@link it.polimi.ingsw.view.cli.CLI CLI}/{@link it.polimi.ingsw.view.gui.GUI GUI})
 */
public class LightCharacter implements Serializable {
    private int cost;
    private final String name;
    private int noEntryTiles = -1;
    private Student[] students;

    /**
     * Class constructor.
     * Initializes the character's features (name and coins as well as
     * students or no entry tiles, if present).
     *
     * @param c     the GameCharacter to simplify.
     */
    LightCharacter(GameCharacter c){
        cost = c.getCost();
        name = c.getName();

        switch (name){
            case "GrannyGrass":
                noEntryTiles = ((GrannyGrassEffect)c.getEffect()).getNoEntryTiles();
                break;
            case "SpoiledPrincess":
                students = ((SpoiledPrincessEffect)c.getEffect()).getStudents();
                break;
            case "Monk":
                students = ((MonkEffect)c.getEffect()).getStudents();
                break;
            case "Jester":
                students = ((JesterEffect)c.getEffect()).getStudents();
                break;
            default: break;
        }
    }


    /**
     * Returns the cost of this character.
     *
     * @return      the cost of the card.
     */
    public int getCost() {
        return cost;
    }

    /**
     * Returns the name of this character.
     *
     * @return      the name of the character.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the No Entry Tiles on this character card.
     *
     * @return      the number of No Entry Tiles.
     */
    public int getNoEntryTiles() {
        return noEntryTiles;
    }

    /**
     * Returns the students on this character card.
     *
     * @return      a Student Array.
     */
    public Student[] getStudents() {
        return students;
    }
}
