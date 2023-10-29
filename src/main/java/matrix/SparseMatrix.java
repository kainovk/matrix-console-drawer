package matrix;

import matrix.drawer.Drawer;
import vector.SparseVector;
import vector.Vector;

public class SparseMatrix<T extends Number> extends AbstractMatrix<T> {

    public SparseMatrix(int numRows, int numCols, Drawer<T> drawer) {
        super(numRows, numCols, drawer);
    }

    @Override
    protected Vector<T> createVector(int size) {
        return new SparseVector<>(size);
    }

    @Override
    public String getElementToDraw(int row, int col) {
        T value = get(row, col);

        if (value.doubleValue() == 0.00) {
            return " ";
        }

        if (value instanceof Integer) {
            return String.format("%d", value);
        } else {
            return String.format("%.2f", value);
        }
    }
}
