package matrix.drawer;

import matrix.DrawableMatrix;

public interface Drawer<T extends Number> {

    void drawBorders(DrawableMatrix<T> matrix, int dx, int dy);

    void hideBorders(DrawableMatrix<T> matrix, int dx, int dy);

    void moveCursor(int dx, int dy);

    void drawElement(DrawableMatrix<T> matrix, int row, int col);
}
