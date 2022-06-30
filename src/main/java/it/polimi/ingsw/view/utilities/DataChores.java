package it.polimi.ingsw.view.utilities;

import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Student;
import it.polimi.ingsw.view.utilities.lightclasses.LightBoard;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This class contains utility methods for {@link it.polimi.ingsw.view.cli.CLI CLI}/{@link it.polimi.ingsw.view.gui.GUI GUI}.
 */
public class DataChores {

    /**
     * Returns the colors corresponding to those of the students present in the
     * given array.
     *
     * @param studentsArray        a Student Array.
     * @return                     the Color List of the Students in the Array.
     */
    public static List<Color> getColorsByStudents(Student[] studentsArray) {
        List<Color> availableColors = new ArrayList<>();

        for (Student s : studentsArray) {
            if (s != null && !availableColors.contains(s.getColor()))
                availableColors.add(s.getColor());
        }

        return availableColors.stream().sorted(Comparator.comparing(Enum::ordinal)).collect(Collectors.toList());
    }

    /**
     * Returns the colors corresponding to those of the students present in the
     * given dining room.
     *
     * @param drArray              the Array representing the dining room.
     * @return                     the Color List of the Students in the dining room.
     */
    public static List<Color> getColorsByDR(int[] drArray) {
        List<Color> availableColors = new ArrayList<>();

        for (int i = 0; i < drArray.length; i++) {
            if (drArray[i] > 0) {
                switch (i) {
                    case 0:
                        availableColors.add(Color.GREEN);
                        break;
                    case 1:
                        availableColors.add(Color.RED);
                        break;
                    case 2:
                        availableColors.add(Color.YELLOW);
                        break;
                    case 3:
                        availableColors.add(Color.PINK);
                        break;
                    case 4:
                        availableColors.add(Color.BLUE);
                        break;
                    default:    //not reachable
                        break;
                }
            }
        }

        return availableColors.stream().sorted(Comparator.comparing(Enum::ordinal)).collect(Collectors.toList());
    }

    /**
     * Checks the color number in the dining room.
     * Used in {@link it.polimi.ingsw.view.ViewInterface#askCharacter(LightBoard) askCharacter method} in order to
     * activate {@link it.polimi.ingsw.model.effects.MinstrelEffect MinstrelEffect}.
     *
     * @param dr                   the Array representing the current Player's dining room.
     * @param studColors           the Color Array of colors chosen by the user.
     * @param i                    the index of the newly added Color in the studColors Array.
     * @param startingIndex        the index from which to start to subList the studColors Array.
     * @param availableColors      the Color List of available Colors.
     */
    public static void checkColorNumberDR(int[] dr, Color[] studColors, int i, int startingIndex, List<Color> availableColors) {
        List<Color> currentStudColors = Arrays.asList(studColors).subList(startingIndex, studColors.length);
        int colorIntValue = studColors[i].ordinal();
        if (dr[colorIntValue] - currentStudColors.stream().filter(color -> color != null && color.ordinal() == colorIntValue).count() == 0){
            availableColors.remove(studColors[i]);
        }
    }

    /**
     * Checks the color number.
     * Used in {@link it.polimi.ingsw.view.ViewInterface#askCharacter(LightBoard) askCharacter method} in order to
     * activate {@link it.polimi.ingsw.model.effects.MinstrelEffect MinstrelEffect} and {@link it.polimi.ingsw.model.effects.JesterEffect JesterEffect}.
     *
     * @param studArray            a Student Array.
     * @param studColors           a Color Array of colors chosen by the user.
     * @param i                    the index of the newly added Color in the studColors Array.
     * @param startingIndex        the index from which to start to subList the studColors Array.
     * @param availableColors      the Color List of available Colors.
     */
    public static void checkColorNumber(Student[] studArray, Color[] studColors, int i, int startingIndex, List<Color> availableColors){
        List<Color> currentStudColors = Arrays.asList(studColors).subList(startingIndex, studColors.length);
        if(Arrays.stream(studArray).filter(s -> (s != null && s.getColor() == studColors[i])).count() - currentStudColors.stream().filter(c -> c == studColors[i]).count() == 0) {
            availableColors.remove(studColors[i]);
        }
    }
}