package matrix;

import vector.Vector;

public abstract class AbstractMatrix<T extends Number> implements Matrix<T> {

    private final Vector<T>[] rows;

    protected AbstractMatrix(int numRows, int numCols) {
        rows = new Vector[numRows];
        for (int i = 0; i < numRows; i++) {
            rows[i] = createVector(numCols);
        }
    }

    protected abstract Vector<T> createVector(int size);

    @Override
    public int getRowCount() {
        return rows.length;
    }

    @Override
    public int getColumnCount() {
        return rows[0].getSize();
    }

    @Override
    public T get(int row, int col) {
        return rows[row].get(col);
    }

    @Override
    public void set(int row, int col, T value) {
        rows[row].set(col, value);
    }
}
