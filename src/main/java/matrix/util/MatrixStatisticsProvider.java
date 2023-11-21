package matrix.util;

import matrix.Matrix;

public class MatrixStatisticsProvider<T extends Number> {

    private final Matrix<T> matrix;

    public MatrixStatisticsProvider(Matrix<T> matrix) {
        this.matrix = matrix;
    }

    public T getSum() {
        T sum = (T) Double.valueOf(0);
        for (int i = 0; i < matrix.getRowCount(); i++) {
            for (int j = 0; j < matrix.getColumnCount(); j++) {
                sum = add(sum, matrix.get(i, j));
            }
        }
        return sum;
    }

    public T getAverage() {
        T sum = getSum();
        int totalCount = matrix.getRowCount() * matrix.getColumnCount();
        return divide(sum, totalCount);
    }

    public T getMax() {
        T max = matrix.get(0, 0);
        for (int i = 0; i < matrix.getRowCount(); i++) {
            for (int j = 0; j < matrix.getColumnCount(); j++) {
                max = compare(max, matrix.get(i, j)) > 0 ? max : matrix.get(i, j);
            }
        }
        return max;
    }

    public T getMin() {
        T min = matrix.get(0, 0);
        for (int i = 0; i < matrix.getRowCount(); i++) {
            for (int j = 0; j < matrix.getColumnCount(); j++) {
                min = compare(min, matrix.get(i, j)) < 0 ? min : matrix.get(i, j);
            }
        }
        return min;
    }

    public int getNonZeroCount() {
        int count = 0;
        for (int i = 0; i < matrix.getRowCount(); i++) {
            for (int j = 0; j < matrix.getColumnCount(); j++) {
                if (!isZero(matrix.get(i, j))) {
                    count++;
                }
            }
        }
        return count;
    }

    private T add(T a, T b) {
        return (T) Double.valueOf(a.doubleValue() + b.doubleValue());
    }

    private T divide(T a, int b) {
        return (T) Double.valueOf(a.doubleValue() / b);
    }

    private int compare(T a, T b) {
        return Double.compare(a.doubleValue(), b.doubleValue());
    }

    private boolean isZero(T a) {
        return Double.compare(a.doubleValue(), 0.0) == 0;
    }
}
