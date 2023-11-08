package matrix;

import vector.Vector;

public class TransposeMatrixDecorator<T extends Number> extends AbstractMatrix<T> {

    private final AbstractMatrix<T> originalMatrix;

    public TransposeMatrixDecorator(AbstractMatrix<T> matrix) {
        super(matrix.getDrawer());
        this.originalMatrix = matrix;

        rows = new Vector[matrix.getColumnCount()];
        for (int i = 0; i < matrix.getColumnCount(); i++) {
            rows[i] = createVector(matrix.getRowCount());
        }
    }

    @Override
    protected Vector<T> createVector(int size) {
        return originalMatrix.createVector(size);
    }

    @Override
    public T get(int row, int col) {
        return originalMatrix.get(col, row);
    }

    @Override
    public void set(int row, int col, T value) {
        originalMatrix.set(col, row, value);
    }
}
