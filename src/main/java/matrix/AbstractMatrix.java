package matrix;

import matrix.drawer.handler.MatrixDrawHandler;
import matrix.observer.PassiveObservable;
import vector.Vector;

public abstract class AbstractMatrix<T extends Number>
        implements DrawableMatrix<T>, PassiveObservable<T> {

    private Long idState = Long.MIN_VALUE;
    protected Vector<T>[] rows;

    protected AbstractMatrix(int numRows, int numCols) {
        rows = new Vector[numRows];
        for (int i = 0; i < numRows; i++) {
            rows[i] = createVector(numCols);
        }
    }

    public abstract Vector<T> createVector(int size);

    protected abstract String getDrawableElement(int row, int col);

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
        idState++;
    }

    @Override
    public void draw(MatrixDrawHandler<T> drawHandler) {
        drawHandler.draw(this);
    }

    @Override
    public String getDrawableValue(int row, int col) {
        return getDrawableElement(row, col);
    }

    @Override
    public Long getIdState() {
        return idState;
    }

    @Override
    public DrawableMatrix<T> getState() {
        return this;
    }
}
