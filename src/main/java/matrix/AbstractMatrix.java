package matrix;

import border.BorderDrawer;
import vector.Vector;

public abstract class AbstractMatrix<T extends Number> implements Matrix<T> {

    private final Vector<T>[] rows;

    protected BorderDrawer borderDrawer;

    protected AbstractMatrix(int numRows, int numCols) {
        rows = new Vector[numRows];
        for (int i = 0; i < numRows; i++) {
            rows[i] = createVector(numCols);
        }
    }

    protected AbstractMatrix(int numRows, int numCols, BorderDrawer borderDrawer) {
        this(numRows, numCols);
        this.borderDrawer = borderDrawer;
    }

    protected abstract Vector<T> createVector(int size);

    public abstract void draw();

    public void drawBorders(int dx, int dy) {
        int width = rows[0].getSize();
        int height = rows.length;
        borderDrawer.drawBorders(width, 2 + height, dx, dy);
    }

    public void hideBorders() {
        int width = rows[0].getSize();
        int height = rows.length;
        borderDrawer.hideBorders(2 + width, 2 + height, 0, 2 + height);
    }

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
