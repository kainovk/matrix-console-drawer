package matrix;

import matrix.drawer.ConsoleDrawer;
import matrix.util.MatrixInitializer;
import util.AnsiEscapeCodesAction;

import java.util.Scanner;

public class MatrixUI<T extends Number> {

    private AbstractMatrix<T> matrix;
    private final int numRows;
    private final int numCols;
    private final T maxValue;
    private boolean bordersActive;
    private String lastAction;

    public MatrixUI(MatrixParameters<T> params) {
        this.numRows = params.getNumRows();
        this.numCols = params.getNumCols();
        this.maxValue = params.getMaxValue();
        this.matrix = null;
        this.bordersActive = true;
        this.lastAction = null;
    }

    public void run() {
        int actionCount = 3;
        System.out.print("\n".repeat(numRows + actionCount));
        System.out.println("1. Print COMMON_MATRIX");
        System.out.println("2. Print SPARSE_MATRIX");
        System.out.println("3. Exit");

        Scanner sc = new Scanner(System.in);

        while (true) {
            String action = sc.nextLine().trim();
            System.out.print(AnsiEscapeCodesAction.getClearUpperLineCode());

            if (action.equals("1") || action.equals("2")) {
                handleMatrixAction(action, actionCount);
            } else if (action.equals("3")) {
                System.exit(0);
            }
        }
    }

    private void handleMatrixAction(String action, int actionCount) {
        System.out.print(AnsiEscapeCodesAction.getCursorUpCode(actionCount + 1));

        if (matrix == null || !action.equals(lastAction)) {
            if (action.equals("1")) {
                matrix = new CommonMatrix<>(numRows, numCols, new ConsoleDrawer<>());
            } else {
                matrix = new SparseMatrix<>(numRows, numCols, new ConsoleDrawer<>());
            }
            MatrixInitializer.initialize(matrix, (int) (numRows * numCols * 0.75), maxValue);
            bordersActive = true;
            matrix.drawBorders(0, 2 + matrix.getRowCount());
            System.out.print(AnsiEscapeCodesAction.getCursorUpCode(actionCount+1));
            matrix.draw();
            System.out.print(AnsiEscapeCodesAction.getCursorDownCode(1));
        } else {
            bordersActive = !bordersActive;
            drawOrHideBorders(matrix, bordersActive);
        }

        lastAction = action;
        System.out.print(AnsiEscapeCodesAction.getCursorDownCode(actionCount + 1));
    }

    private void drawOrHideBorders(AbstractMatrix<T> matrix, boolean bordersActive) {
        if (bordersActive) {
            matrix.drawBorders(0, 2 + matrix.getRowCount());
        } else {
            matrix.hideBorders();
        }
    }
}
