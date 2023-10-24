package matrix.util;

import matrix.Matrix;

import java.text.DecimalFormat;

public class MatrixPrinter {

    public static <T extends Number> void print(Matrix<T> matrix) {
        int numRows = matrix.getRowCount();
        int numCols = matrix.getColumnCount();

        DecimalFormat format = new DecimalFormat("0.00");

        int maxCharWidth = 0;
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                String formattedValue = format.format(matrix.get(i, j));
                maxCharWidth = Math.max(maxCharWidth, formattedValue.length());
            }
        }

        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                String formattedValue = format.format(matrix.get(i, j));
                System.out.printf("%-" + (maxCharWidth + 2) + "s", formattedValue);
            }
            System.out.println();
        }
    }
}
