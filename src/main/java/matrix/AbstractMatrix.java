package matrix;

import matrix.drawer.Drawer;
import vector.Vector;

public abstract class AbstractMatrix<T extends Number> implements DrawableMatrix<T> {

    protected Vector<T>[] rows;

    private Drawer<T> drawer;

    protected AbstractMatrix(Drawer<T> drawer) {
        this.drawer = drawer;
    }

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

    public abstract Vector<T> createVector(int size);

    @Override
    public void draw() {
        drawer.drawMatrix(this);
    }

    @Override
    public void drawBorders(int dx, int dy) {
        int width = rows[0].getSize();
        int height = rows.length;
        drawer.drawBorders(width, height, dx, dy);
    }

    @Override
    public void hideBorders() {
        int width = rows[0].getSize();
        int height = rows.length;
        drawer.hideBorders(2 + width, 2 + height, 0, 2 + height);
    }

    @Override
    public Drawer<T> getDrawer() {
        return drawer;
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
