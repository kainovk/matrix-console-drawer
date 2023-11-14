package matrix.util;

import matrix.Matrix;
import matrix.SparseMatrix;

public class DrawerFormatter {

    public static <T extends Number> String getElementToDraw(Matrix<T> matrix, int row, int col) {
        T value = matrix.get(row, col);

        if (matrix instanceof SparseMatrix && value.doubleValue() == 0.00) {
            return " ";
        }

        if (value instanceof Integer) {
            return String.format("%d", value);
        } else {
            return String.format("%.2f", value);
        }

    }
}
