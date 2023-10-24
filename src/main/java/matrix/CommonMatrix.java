package matrix;

import border.BorderDrawer;
import util.AnsiEscapeCodesAction;
import vector.CommonVector;
import vector.Vector;

import java.util.Formatter;

public class CommonMatrix<T extends Number> extends AbstractMatrix<T> {

    public CommonMatrix(int numRows, int numCols, BorderDrawer borderDrawer) {
        super(numRows, numCols, borderDrawer);
    }

    @Override
    protected Vector<T> createVector(int size) {
        return new CommonVector<>(size);
    }

    @Override
    public void draw() {
        int numRows = this.getRowCount();
        int numCols = this.getColumnCount();

        StringBuilder result = new StringBuilder();
        result.append(AnsiEscapeCodesAction.getCursorUpCode(1));
        result.append(AnsiEscapeCodesAction.getClearUpperLineCode().repeat(numRows));

        Formatter formatter = new Formatter(result);
        String cursorRightCode = AnsiEscapeCodesAction.getCursorRightCode(2);

        for (int row = 0; row < numRows; row++) {
            result.append(cursorRightCode);

            for (int col = 0; col < numCols; col++) {
                T value = this.get(row, col);
                formatValue(formatter, value);
            }

            result.append("\n");
        }

        formatter.close();
        System.out.println(result);
    }

    private void formatValue(Formatter formatter, T value) {
        if (value instanceof Integer) {
            formatter.format("%6d  ", value);
        } else {
            formatter.format("%6.2f  ", value);
        }
    }
}