package matrix.drawer;

import matrix.Matrix;

public interface Drawer<T extends Number> {

    void drawMatrix(Matrix<T> matrix);

    void drawBorders(int width, int height, int dx, int dy);

    void hideBorders(int width, int height, int dx, int dy);

    void moveCursor(int dx, int dy);
}
