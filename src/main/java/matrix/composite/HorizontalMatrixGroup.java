package matrix.composite;

import matrix.DrawableMatrix;
import matrix.drawer.handler.MatrixDrawHandler;
import matrix.observer.PassiveObservable;

import java.util.LinkedList;
import java.util.List;

public class HorizontalMatrixGroup<T extends Number>
        implements DrawableMatrix<T>, PassiveObservable<T> {

    private Long idState = Long.MIN_VALUE;
    private int matrixGroupIndex = -1;
    private final List<DrawableMatrix<T>> matrices;

    public HorizontalMatrixGroup() {
        matrices = new LinkedList<>();
    }

    public void addMatrix(DrawableMatrix<T> matrix) {
        matrices.add(matrix);
        idState++;
    }

    @Override
    public T get(int row, int col) {
        validateIndexOrElseThrow(row, col);
        col = findMatrixColumnIndex(row, col);

        if (matrixGroupIndex == -1) {
            return getDefaultValue();
        }

        return matrices.get(matrixGroupIndex).get(row, col);
    }

    @Override
    public void set(int row, int col, T value) {
        validateIndexOrElseThrow(row, col);
        col = findMatrixColumnIndex(row, col);

        if (matrixGroupIndex == -1) {
            return;
        }

        matrices.get(matrixGroupIndex).set(row, col, value);
        idState++;
    }

    @Override
    public int getRowCount() {
        return matrices.stream()
                .map(DrawableMatrix::getRowCount)
                .max(Integer::compareTo)
                .orElse(0);
    }

    @Override
    public int getColumnCount() {
        return matrices.stream()
                .map(DrawableMatrix::getColumnCount)
                .reduce(Integer::sum)
                .orElse(0);
    }

    @Override
    public void draw(MatrixDrawHandler<T> drawHandler) {
        drawHandler.draw(this);
    }

    @Override
    public String getDrawableValue(int row, int col) {
        validateIndexOrElseThrow(row, col);
        col = findMatrixColumnIndex(row, col);

        if (matrixGroupIndex == -1) {
            return "";
        }

        return matrices.get(matrixGroupIndex).getDrawableValue(row, col);
    }

    @Override
    public Long getIdState() {
        return idState;
    }

    @Override
    public DrawableMatrix<T> getState() {
        return this;
    }

    private T getDefaultValue() {
        Number n = 0.0;
        return (T) n;
    }

    private void validateIndexOrElseThrow(int row, int col) {
        if (row < 0 || row >= getRowCount()) {
            throw new IndexOutOfBoundsException("row index out of bounds: " + row);
        }
        if (col < 0 || col >= getColumnCount()) {
            throw new IndexOutOfBoundsException("col index out of bounds: " + col);
        }
    }

    private int findMatrixColumnIndex(int row, int col) {
        try {
            validateIndexOrElseThrow(row, col);
        } catch (IndexOutOfBoundsException e) {
            matrixGroupIndex = -1;
            return -1;
        }

        matrixGroupIndex = 0;
        for (DrawableMatrix<T> mtx : matrices) {
            if (col < mtx.getColumnCount()) {
                break;
            }
            matrixGroupIndex++;
            col -= mtx.getColumnCount();
        }
        if (row >= matrices.get(matrixGroupIndex).getRowCount()) {
            matrixGroupIndex = -1;
        }

        return col;
    }
}
