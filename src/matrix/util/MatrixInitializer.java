package matrix.util;

import matrix.Matrix;

import java.util.Random;

public class MatrixInitializer {

    private static final Random rand = new Random();

    public static <T extends Number> void initialize(Matrix<T> matrix, int nonZeroCount, T maxValue) {
        int numRows = matrix.getRowCount();
        int numCols = matrix.getColumnCount();
        int totalElements = numRows * numCols;

        int[] allIndices = new int[totalElements];
        for (int i = 0; i < totalElements; i++) {
            allIndices[i] = i;
        }

        for (int i = totalElements - 1; i > 0; i--) {
            int j = rand.nextInt(i + 1);
            int temp = allIndices[i];
            allIndices[i] = allIndices[j];
            allIndices[j] = temp;
        }

        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                int randomIndex = allIndices[i * numCols + j];
                if (nonZeroCount > 0) {
                    double randomValue = rand.nextDouble() * maxValue.doubleValue();
                    matrix.set(randomIndex / numCols, randomIndex % numCols, (T) Double.valueOf(randomValue));
                    nonZeroCount--;
                } else {
                    matrix.set(randomIndex / numCols, randomIndex % numCols, (T) Double.valueOf(0));
                }
            }
        }
    }
}
