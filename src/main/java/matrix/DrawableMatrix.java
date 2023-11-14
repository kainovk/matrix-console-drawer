package matrix;

import matrix.drawer.Drawer;

public interface DrawableMatrix<T extends Number> extends Matrix<T> {

    void draw();

    void drawBorders(int dx, int dy);

    void hideBorders();

    default Drawer<T> getDrawer() {
        return null;
    }
}
