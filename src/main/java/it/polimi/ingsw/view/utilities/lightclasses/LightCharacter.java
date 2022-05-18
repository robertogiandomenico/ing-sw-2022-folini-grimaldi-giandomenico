package it.polimi.ingsw.view.utilities.lightclasses;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.effects.GrannyGrassEffect;
import it.polimi.ingsw.model.effects.MonkEffect;
import it.polimi.ingsw.model.effects.SpoiledPrincessEffect;
import it.polimi.ingsw.model.effects.JesterEffect;

import java.io.Serializable;

public class LightCharacter implements Serializable {
    private int cost;
    private final String name;
    private int noEntryTiles = -1;
    private Student[] students;

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


    public int getCost() {
        return cost;
    }

    public String getName() {
        return name;
    }

    public int getNoEntryTiles() {
        return noEntryTiles;
    }

    public Student[] getStudents() {
        return students;
    }
}
