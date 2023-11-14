package matrix;

import matrix.drawer.Drawer;
import vector.SparseVector;
import vector.Vector;

public class SparseMatrix<T extends Number> extends AbstractMatrix<T> {

    public SparseMatrix(int numRows, int numCols, Drawer<T> drawer) {
        super(numRows, numCols, drawer);
    }

    @Override
    public Vector<T> createVector(int size) {
        return new SparseVector<>(size);
    }
}
