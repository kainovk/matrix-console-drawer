package matrix;

import vector.SparseVector;
import vector.Vector;

public class SparseMatrix<T extends Number> extends AbstractMatrix<T> {

    public SparseMatrix(int numRows, int numCols) {
        super(numRows, numCols);
    }

    @Override
    public Vector<T> createVector(int size) {
        return new SparseVector<>(size);
    }

    @Override
    protected String getDrawableElement(int row, int col) {
        T value = get(row, col);

        if (value.doubleValue() == 0.0) {
            return " ";
        }

        if (value instanceof Integer) {
            return String.format("%d", value);
        } else {
            return String.format("%.2f", value);
        }
    }
}
