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
}
