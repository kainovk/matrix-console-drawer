package matrix;

import vector.Vector;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class HorizontalMatrixGroup<T extends Number> extends AbstractMatrix<T> {

    private final List<AbstractMatrix<T>> matrices = new LinkedList<>();

    public HorizontalMatrixGroup(VerticalMatrixGroup<T> verticalMatrixGroup) {
        super(verticalMatrixGroup.getColumnCount(), verticalMatrixGroup.getRowCount());
        List<AbstractMatrix<T>> horizontalMatricesList = verticalMatrixGroup.getMatrices().stream()
                .map(matrix -> new TransposeMatrixDecorator<>(matrix))
                .collect(Collectors.toList());
        matrices.addAll(horizontalMatricesList);
    }

    public void addMatrix(AbstractMatrix<T> matrix) {
        matrices.add(matrix);
    }

    @Override
    public void draw() {
        for (AbstractMatrix<T> subMatrix : matrices) {
            // TODO: draw() draws borders too. maybe takes argument "boolean isBordersActive"
            subMatrix.draw();
            subMatrix.getDrawer().moveCursor(subMatrix.getColumnCount() * 7, subMatrix.getRowCount() + 1);
        }
    }

    public AbstractMatrix<T> getMatrix() {
        return new AbstractMatrix<>(getRowCount(), getColumnCount()) {

            @Override
            protected Vector<T> createVector(int size) {
                return null;
            }
        };
    }

    @Override
    public T get(int row, int col) {
        int currentRow = 0;
        for (AbstractMatrix<T> matrix : matrices) {
            int rowCount = matrix.getRowCount();
            int colCount = matrix.getColumnCount();

            if (row >= currentRow && row < currentRow + rowCount && col >= 0 && col < colCount) {
                return matrix.get(row, col - currentRow);
            }

            currentRow += rowCount;
        }

        return null;
    }

    @Override
    public void set(int row, int col, T value) {
        int currentRow = 0;
        for (AbstractMatrix<T> matrix : matrices) {
            int rowCount = matrix.getRowCount();
            int colCount = matrix.getColumnCount();

            if (row >= currentRow && row < currentRow + rowCount && col >= 0 && col < colCount) {
                matrix.set(row, col - currentRow, value);
                return;
            }

            currentRow += rowCount;
        }
    }

    @Override
    protected Vector<T> createVector(int size) {
        return null;
    }

    @Override
    public int getRowCount() {
        return matrices.stream()
                .map(AbstractMatrix::getRowCount)
                .reduce(Integer::sum)
                .orElse(0);
    }

    @Override
    public int getColumnCount() {
        return matrices.stream()
                .map(AbstractMatrix::getColumnCount)
                .max(Integer::compareTo)
                .orElse(0);
    }
}
