package matrix.decorator;

import matrix.DrawableMatrix;
import matrix.drawer.handler.MatrixDrawHandler;
import matrix.observer.PassiveObservable;

public abstract class AbstractMatrixDecorator<T extends Number>
        implements DrawableMatrix<T>, PassiveObservable<T> {

    protected Long idState = Long.MIN_VALUE;
    protected DrawableMatrix<T> matrix;

    public AbstractMatrixDecorator(DrawableMatrix<T> matrix) {
        this.matrix = matrix;
    }

    @Override
    public void draw(MatrixDrawHandler<T> drawHandler) {
        drawHandler.draw(this);
    }

    @Override
    public Long getIdState() {
        return idState;
    }

    @Override
    public DrawableMatrix<T> getState() {
        return this;
    }
}
