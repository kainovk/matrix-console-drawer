package matrix;

import matrix.drawer.Drawer;
import vector.CommonVector;
import vector.Vector;

public class CommonMatrix<T extends Number> extends AbstractMatrix<T> {

    public CommonMatrix(int numRows, int numCols, Drawer<T> drawer) {
        super(numRows, numCols, drawer);
    }

    @Override
    public Vector<T> createVector(int size) {
        return new CommonVector<>(size);
    }
}