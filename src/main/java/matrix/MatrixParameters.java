package matrix;

public class MatrixParameters<T extends Number> {

    private final int numRows;
    private final int numCols;
    private final T maxValue;

    public MatrixParameters(int numRows, int numCols, T maxValue) {
        this.numRows = numRows;
        this.numCols = numCols;
        this.maxValue = maxValue;
    }

    public int getNumRows() {
        return numRows;
    }

    public int getNumCols() {
        return numCols;
    }

    public T getMaxValue() {
        return maxValue;
    }
}
