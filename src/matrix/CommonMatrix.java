package matrix;

import vector.CommonVector;
import vector.Vector;

public class CommonMatrix<T extends Number> extends AbstractMatrix<T> {

    public CommonMatrix(int numRows, int numCols) {
        super(numRows, numCols);
    }

    @Override
    protected Vector<T> createVector(int size) {
        return new CommonVector<>(size);
    }
}
