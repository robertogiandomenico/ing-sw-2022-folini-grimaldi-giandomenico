package it.polimi.ingsw.view.utilities;

import it.polimi.ingsw.view.cli.CLI;

public class MatrixOperations {
    /**
     * Sums the elements present in the column of a matrix.
     * Utility for {@link CLI} methods.
     *
     * @param matrix        a matrix.
     * @param mapIndex      the index of a column.
     * @return              the sum of the elements in that column.
     */
    public static int columnSum(int[][] matrix, int mapIndex) {
        int sum = 0;
        for (int[] a : matrix) {
            sum += a[mapIndex];
        }

        return sum;
    }
}
