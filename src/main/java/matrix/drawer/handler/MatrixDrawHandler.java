package matrix.drawer.handler;

import matrix.DrawableMatrix;

public interface MatrixDrawHandler<T extends Number> {

    void draw(DrawableMatrix<T> matrix);
}
