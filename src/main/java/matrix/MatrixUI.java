package matrix;

import matrix.drawer.ConsoleMatrixDrawer;
import matrix.drawer.HtmlMatrixDrawer;
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
        int actionCount = 5;
        System.out.print("\n".repeat(numRows + 3));
        System.out.println("1. Print COMMON_MATRIX in console");
        System.out.println("2. Print COMMON_MATRIX in HTML file");
        System.out.println("3. Print SPARSE_MATRIX in console");
        System.out.println("4. Print SPARSE_MATRIX in HTML file");
        System.out.println("5. Exit");

        Scanner sc = new Scanner(System.in);

        while (true) {
            String action = sc.nextLine().trim();
            System.out.print(AnsiEscapeCodesAction.getClearUpperLineCode());

            if (action.compareTo("1") >= 0 && action.compareTo("4") <= 0) {
                handleMatrixAction(action, actionCount);
            } else if (action.equals("5")) {
                System.exit(0);
            }
        }
    }

    public void runDecoratorExample() {
        AbstractMatrix<Integer> commonMatrix = new CommonMatrix<>(3, 3, new ConsoleMatrixDrawer<>());
        MatrixInitializer.initialize(commonMatrix, 7, 100);

        System.out.println("Original matrix:");
        commonMatrix.draw();
        System.out.println();

        EnumeratedMatrixDecorator<Integer> decorator = new EnumeratedMatrixDecorator<>(commonMatrix);
        decorator.swapRows(1, 2);
        decorator.swapColumns(0, 1);
        AbstractMatrix<Integer> decoratedMatrix = decorator.getDecoratedMatrix();

        System.out.println("Decorated matrix:");
        decoratedMatrix.draw();
    }

    public void runHorizontalMatrixCompositeExample() {
        System.out.println("\n".repeat(15));
        AbstractMatrix<Integer> commonMatrix11 = new CommonMatrix<>(2, 2, new ConsoleMatrixDrawer<>());
        MatrixInitializer.initialize(commonMatrix11, 4, 2);

        AbstractMatrix<Integer> commonMatrix12 = new CommonMatrix<>(3, 4, new ConsoleMatrixDrawer<>());
        MatrixInitializer.initialize(commonMatrix12, 12, 4);

        AbstractMatrix<Integer> commonMatrix13 = new CommonMatrix<>(3, 1, new ConsoleMatrixDrawer<>());
        MatrixInitializer.initialize(commonMatrix13, 3, 6);

        VerticalMatrixGroup<Integer> verticalMatrixGroup = new VerticalMatrixGroup<>(8, 4);
        verticalMatrixGroup.addMatrix(commonMatrix11);
        verticalMatrixGroup.addMatrix(commonMatrix12);
        //verticalMatrixGroup.addMatrix(commonMatrix13);
        HorizontalMatrixGroup<Integer> horizontalMatrixGroup1 = new HorizontalMatrixGroup<>(verticalMatrixGroup);


        AbstractMatrix<Integer> commonMatrix21 = new CommonMatrix<>(4, 2, new ConsoleMatrixDrawer<>());
        MatrixInitializer.initialize(commonMatrix21, 8, 8);

        AbstractMatrix<Integer> commonMatrix22 = new CommonMatrix<>(3, 2, new ConsoleMatrixDrawer<>());
        MatrixInitializer.initialize(commonMatrix22, 6, 10);

        verticalMatrixGroup = new VerticalMatrixGroup<>(7, 2);
        verticalMatrixGroup.addMatrix(commonMatrix21);
        verticalMatrixGroup.addMatrix(commonMatrix22);
        HorizontalMatrixGroup<Integer> horizontalMatrixGroup2 = new HorizontalMatrixGroup<>(verticalMatrixGroup);


        AbstractMatrix<Integer> commonMatrix31 = new CommonMatrix<>(1, 1, new ConsoleMatrixDrawer<>());
        MatrixInitializer.initialize(commonMatrix31, 1, 12);

        VerticalMatrixGroup<Integer> verticalMatrixGroupMain = new VerticalMatrixGroup<>(7, 8);
        verticalMatrixGroupMain.addMatrix(horizontalMatrixGroup1);
        //verticalMatrixGroupMain.addMatrix(horizontalMatrixGroup2);
        //verticalMatrixGroupMain.addMatrix(commonMatrix31);

        verticalMatrixGroupMain.draw();

        Scanner sc = new Scanner(System.in);
        sc.next();
        //System.out.println(AnsiEscapeCodesAction.getCursorDownCode(15));
    }

    private void handleMatrixAction(String action, int actionCount) {
        System.out.print(AnsiEscapeCodesAction.getCursorUpCode(actionCount + 1));

        if (matrix == null || !action.equals(lastAction)) {
            switch (action) {
                case "1":
                    matrix = new CommonMatrix<>(numRows, numCols, new ConsoleMatrixDrawer<>());
                    break;
                case "2":
                    matrix = new CommonMatrix<>(numRows, numCols, new HtmlMatrixDrawer<>());
                    break;
                case "3":
                    matrix = new SparseMatrix<>(numRows, numCols, new ConsoleMatrixDrawer<>());
                    break;
                case "4":
                    matrix = new SparseMatrix<>(numRows, numCols, new HtmlMatrixDrawer<>());
                    break;
                default:
                    return;
            }
            MatrixInitializer.initialize(matrix, (int) (numRows * numCols * 0.75), maxValue);
            bordersActive = true;
            matrix.drawBorders(0, 2 + matrix.getRowCount());
            matrix.draw();
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
