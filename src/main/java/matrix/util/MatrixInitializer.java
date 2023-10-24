package matrix.util;

import matrix.Matrix;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class MatrixInitializer {

    private static final Random rand = new Random();

    public static <T extends Number> void initialize(Matrix<T> matrix, int nonZeroCount, T maxValue) {
        int numRows = matrix.getRowCount();
        int numCols = matrix.getColumnCount();
        int totalElements = numRows * numCols;

        List<T> values = new ArrayList<>(totalElements);

        for (int i = 0; i < nonZeroCount; i++) {
            double randomValue = rand.nextDouble() * maxValue.doubleValue();
            if (maxValue instanceof Integer) {
                values.add((T) Integer.valueOf((int) randomValue));
            } else if (maxValue instanceof Float) {
                values.add((T) Float.valueOf((float) randomValue));
            } else if (maxValue instanceof Double) {
                values.add((T) Double.valueOf(randomValue));
            }
        }

        for (int i = nonZeroCount; i < totalElements; i++) {
            if (maxValue instanceof Integer) {
                values.add((T) Integer.valueOf(0));
            } else if (maxValue instanceof Float) {
                values.add((T) Float.valueOf(0));
            } else if (maxValue instanceof Double) {
                values.add((T) Double.valueOf(0));
            }
        }

        Collections.shuffle(values);

        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                matrix.set(i, j, values.get(i + j));
            }
        }
    }
}
