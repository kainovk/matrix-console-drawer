package matrix;

import matrix.drawer.Drawer;
import vector.Vector;

public abstract class AbstractMatrix<T extends Number> implements Matrix<T> {

    private final Vector<T>[] rows;

    private Drawer<T> drawer;

    protected AbstractMatrix(int numRows, int numCols) {
        rows = new Vector[numRows];
        for (int i = 0; i < numRows; i++) {
            rows[i] = createVector(numCols);
        }
    }

    protected AbstractMatrix(int numRows, int numCols, Drawer<T> drawer) {
        this(numRows, numCols);
        this.drawer = drawer;
    }

    protected abstract Vector<T> createVector(int size);

    protected Drawer<T> getDrawer() {
        return drawer;
    }

    public String getElementToDraw(int row, int col) {
        T value = get(row, col);

        if (value instanceof Integer) {
            return String.format("%d", value);
        } else {
            return String.format("%.2f", value);
        }
    }

    public void draw() {
        drawer.drawMatrix(this);
    }

    public void drawBorders(int dx, int dy) {
        int width = rows[0].getSize();
        int height = rows.length;
        drawer.drawBorders(width, height, dx, dy);
    }

    public void hideBorders() {
        int width = rows[0].getSize();
        int height = rows.length;
        drawer.hideBorders(2 + width, 2 + height, 0, 2 + height);
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
