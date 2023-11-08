package matrix.drawer;

import matrix.AbstractMatrix;

public interface Drawer<T extends Number> {

    void drawMatrix(AbstractMatrix<T> matrix);

    void drawBorders(int width, int height, int dx, int dy);

    void hideBorders(int width, int height, int dx, int dy);

    void moveCursor(int dx, int dy);
}
