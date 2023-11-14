package matrix.decorator;

import matrix.DrawableMatrix;
import matrix.drawer.Drawer;

public class TransposeMatrixDecorator<T extends Number> implements DrawableMatrix<T> {

    private final DrawableMatrix<T> originalMatrix;

    public TransposeMatrixDecorator(DrawableMatrix<T> originalMatrix) {
        this.originalMatrix = originalMatrix;
    }

    @Override
    public T get(int row, int col) {
        return originalMatrix.get(col, row);
    }

    @Override
    public void set(int row, int col, T value) {
        originalMatrix.set(col, row, value);
    }

    @Override
    public int getRowCount() {
        return originalMatrix.getColumnCount();
    }

    @Override
    public int getColumnCount() {
        return originalMatrix.getRowCount();
    }

    @Override
    public void draw() {
        Drawer<T> drawer = originalMatrix.getDrawer();
        if (drawer == null) {
            return;
        }

        drawer.drawMatrix(this);
    }

    @Override
    public void drawBorders(int dx, int dy) {
        originalMatrix.drawBorders(dx, dy);
    }

    @Override
    public void hideBorders() {
        originalMatrix.hideBorders();
    }
}
