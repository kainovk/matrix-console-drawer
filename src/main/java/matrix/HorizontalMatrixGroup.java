package matrix;

import matrix.decorator.TransposeMatrixDecorator;
import matrix.drawer.Drawer;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class HorizontalMatrixGroup<T extends Number> implements DrawableMatrix<T> {

    private final List<DrawableMatrix<T>> matrices = new LinkedList<>();

    public HorizontalMatrixGroup() {
    }

    public HorizontalMatrixGroup(VerticalMatrixGroup<T> verticalMatrixGroup) {
        List<DrawableMatrix<T>> horizontalMatricesList = verticalMatrixGroup.getMatrices().stream()
                .map(matrix -> new TransposeMatrixDecorator<>(matrix))
                .collect(Collectors.toList());
        matrices.addAll(horizontalMatricesList);
    }

    public List<DrawableMatrix<T>> getMatrices() {
        return matrices;
    }

    public void addMatrix(DrawableMatrix<T> matrix) {
        matrices.add(matrix);
    }

    @Override
    public void draw() {
        for (DrawableMatrix<T> subMatrix : matrices) {
            subMatrix.draw();
            Drawer<T> drawer = subMatrix.getDrawer();
            if (drawer != null) {
                drawer.moveCursor(subMatrix.getColumnCount() * 7, subMatrix.getRowCount() + 1);
            }
        }
    }

    @Override
    public void drawBorders(int dx, int dy) {
        matrices.forEach(matrix -> matrix.drawBorders(dx, dy));
    }

    @Override
    public void hideBorders() {
        matrices.forEach(DrawableMatrix::hideBorders);
    }

    @Override
    public T get(int row, int col) {
        int currentRow = 0;
        for (DrawableMatrix<T> matrix : matrices) {
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
        for (DrawableMatrix<T> matrix : matrices) {
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
}
