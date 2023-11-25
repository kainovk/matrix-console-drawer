package matrix.decorator;

import matrix.DrawableMatrix;

public class TransposeMatrixDecorator<T extends Number> extends AbstractMatrixDecorator<T> {

    public TransposeMatrixDecorator(DrawableMatrix<T> originalMatrix) {
        super(originalMatrix);
    }

    @Override
    public T get(int row, int col) {
        return matrix.get(col, row);
    }

    @Override
    public void set(int row, int col, T value) {
        matrix.set(col, row, value);
    }

    @Override
    public int getRowCount() {
        return matrix.getColumnCount();
    }

    @Override
    public int getColumnCount() {
        return matrix.getRowCount();
    }

    @Override
    public String getDrawableValue(int row, int col) {
        return matrix.getDrawableValue(col, row);
    }
}
