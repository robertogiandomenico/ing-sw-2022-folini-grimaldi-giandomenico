package it.polimi.ingsw.view.utilities;

import it.polimi.ingsw.model.Color;
import it.polimi.ingsw.model.Student;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

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
}
