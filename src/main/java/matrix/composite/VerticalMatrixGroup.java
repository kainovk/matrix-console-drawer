package matrix.composite;

import matrix.DrawableMatrix;
import matrix.decorator.TransposeMatrixDecorator;
import matrix.drawer.handler.MatrixDrawHandler;
import matrix.observer.PassiveObservable;

public class VerticalMatrixGroup<T extends Number>
        implements DrawableMatrix<T>, PassiveObservable<T> {

    private Long idState = Long.MIN_VALUE;
    private final HorizontalMatrixGroup<T> horizontalGroup;
    private final TransposeMatrixDecorator<T> transposeDecorator;

    public VerticalMatrixGroup() {
        horizontalGroup = new HorizontalMatrixGroup<>();
        transposeDecorator = new TransposeMatrixDecorator<>(horizontalGroup);
    }

    public void addMatrix(DrawableMatrix<T> matrix) {
        horizontalGroup.addMatrix(new TransposeMatrixDecorator<>(matrix));
        idState++;
    }

    @Override
    public T get(int row, int col) {
        return transposeDecorator.get(row, col);
    }

    @Override
    public void set(int row, int col, T value) {
        transposeDecorator.set(row, col, value);
        idState++;
    }

    @Override
    public int getRowCount() {
        return transposeDecorator.getRowCount();
    }

    @Override
    public int getColumnCount() {
        return transposeDecorator.getColumnCount();
    }

    @Override
    public void draw(MatrixDrawHandler<T> drawHandler) {
        drawHandler.draw(transposeDecorator);
    }

    @Override
    public String getDrawableValue(int row, int col) {
        return transposeDecorator.getDrawableValue(row, col);
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
