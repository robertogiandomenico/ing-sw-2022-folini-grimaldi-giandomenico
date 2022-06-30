package it.polimi.ingsw.view.utilities;

/**
 * This class contains utility methods regarding matrix
 * operations to do for {@link it.polimi.ingsw.view.cli.CLI CLI}
 * and {@link it.polimi.ingsw.view.gui.GUI GUI}.
 */
public class MatrixOperations {

    /**
     * Sums the elements present in the column of a matrix.
     *
     * @param matrix        a matrix.
     * @param mapIndex      the index of a column.
     * @return              the sum of the elements in that column.
     */
    public static int columnSum(int[][] matrix, int mapIndex) {
        int sum = 0;
        for (int[] a : matrix)
            sum += a[mapIndex];

        return sum;
    }
}
