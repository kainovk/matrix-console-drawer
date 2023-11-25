package matrix.decorator;

import matrix.DrawableMatrix;

public class EnumeratedMatrixDecorator<T extends Number> extends AbstractMatrixDecorator<T> {

    private final int[] rowIndices;
    private final int[] colIndices;

    public EnumeratedMatrixDecorator(DrawableMatrix<T> matrix) {
        super(matrix);
        this.rowIndices = new int[matrix.getRowCount()];
        this.colIndices = new int[matrix.getColumnCount()];
        init(rowIndices);
        init(colIndices);
    }

    public void swapRows(int row1, int row2) {
        int tmp = rowIndices[row1];
        rowIndices[row1] = rowIndices[row2];
        rowIndices[row2] = tmp;
    }

    public void swapColumns(int col1, int col2) {
        int tmp = colIndices[col1];
        colIndices[col1] = colIndices[col2];
        colIndices[col2] = tmp;
    }

    @Override
    public T get(int row, int col) {
        int rowIndex = rowIndices[row];
        int colIndex = colIndices[col];
        return matrix.get(rowIndex, colIndex);
    }

    @Override
    public void set(int row, int col, T value) {
        int rowIndex = rowIndices[row];
        int colIndex = colIndices[col];
        matrix.set(rowIndex, colIndex, value);
    }

    @Override
    public int getRowCount() {
        return matrix.getRowCount();
    }

    @Override
    public int getColumnCount() {
        return matrix.getColumnCount();
    }

    private void init(int[] array) {
        for (int i = 0; i < array.length; i++) {
            array[i] = i;
        }
    }

    @Override
    public String getDrawableValue(int row, int col) {
        int rowIndex = rowIndices[row];
        int colIndex = colIndices[col];
        return matrix.getDrawableValue(rowIndex, colIndex);
    }
}
