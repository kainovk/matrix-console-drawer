package matrix;

import matrix.drawer.handler.MatrixDrawHandler;

public interface DrawableMatrix<T extends Number> extends Matrix<T> {

    void draw(MatrixDrawHandler<T> drawHandler);

    String getDrawableValue(int row, int col);
}
