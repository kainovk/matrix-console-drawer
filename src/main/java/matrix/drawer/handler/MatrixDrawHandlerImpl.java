package matrix.drawer.handler;

import matrix.DrawableMatrix;
import matrix.drawer.Drawer;

public class MatrixDrawHandlerImpl<T extends Number> implements MatrixDrawHandler<T> {

    private final Drawer<T> drawer;

    public MatrixDrawHandlerImpl(Drawer<T> drawer) {
        this.drawer = drawer;
    }

    @Override
    public void draw(DrawableMatrix<T> matrix) {
        drawBorders(matrix);
        drawElements(matrix);
    }

    private void drawBorders(DrawableMatrix<T> matrix) {
        drawer.drawBorders(matrix, 0, 0);
    }

    private void drawElements(DrawableMatrix<T> matrix) {
        for (int row = 0; row < matrix.getRowCount(); row++) {
            for (int col = 0; col < matrix.getColumnCount(); col++) {
                drawElement(matrix, row, col);
            }
            System.out.println();
        }
    }

    private void drawElement(DrawableMatrix<T> matrix, int row, int col) {
        drawer.drawElement(matrix, row, col);
    }
}
