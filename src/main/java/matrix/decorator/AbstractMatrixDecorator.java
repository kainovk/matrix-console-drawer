package matrix.decorator;

import matrix.DrawableMatrix;
import matrix.drawer.handler.MatrixDrawHandler;

public abstract class AbstractMatrixDecorator<T extends Number> implements DrawableMatrix<T> {

    protected DrawableMatrix<T> matrix;

    public AbstractMatrixDecorator(DrawableMatrix<T> matrix) {
        this.matrix = matrix;
    }

    @Override
    public void draw(MatrixDrawHandler<T> drawHandler) {
        drawHandler.draw(this);
    }
}
