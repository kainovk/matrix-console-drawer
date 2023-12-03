package matrix;

import matrix.command.CM;
import matrix.command.commands.CommandChangeValue;
import matrix.command.commands.CommandInit;
import matrix.composite.HorizontalMatrixGroup;
import matrix.composite.VerticalMatrixGroup;
import matrix.decorator.EnumeratedMatrixDecorator;
import matrix.drawer.ConsoleMatrixDrawer;
import matrix.drawer.handler.MatrixDrawHandler;
import matrix.drawer.handler.MatrixDrawHandlerImpl;
import matrix.util.MatrixInitializer;
import util.AnsiEscapeCodesAction;

import java.time.Duration;

public class MatrixUI<T extends Number> {

    private static final CM cm = CM.instance();

    private DrawableMatrix<T> matrix;
    private final int numRows;
    private final int numCols;
    private final T maxValue;
    private boolean bordersActive;
    private String lastAction;

    public MatrixUI(MatrixParameters<T> params) {
        MatrixUI.cm.registry(new CommandInit());
        this.numRows = params.getNumRows();
        this.numCols = params.getNumCols();
        this.maxValue = params.getMaxValue();
        this.matrix = null;
        this.bordersActive = true;
        this.lastAction = null;
    }

    public void runCommandExample() throws InterruptedException {
        boolean bordersActive = true;
        MatrixDrawHandler<Integer> consoleDrawer = new MatrixDrawHandlerImpl<>(
                new ConsoleMatrixDrawer<>(bordersActive)
        );

        int maxValue = 9999;
        int nonZeroCount = Integer.MAX_VALUE;
        VerticalMatrixGroup<Integer> verticalMatrixGroup = prepareVerticalMatrixGroup(maxValue, nonZeroCount);
        verticalMatrixGroup.draw(consoleDrawer);

        //Thread.sleep(Duration.ofSeconds(5).toMillis());
        cm.registry(new CommandChangeValue<>(verticalMatrixGroup, new int[]{1, 2}, 7331));
        cm.registry(new CommandChangeValue<>(verticalMatrixGroup, new int[]{3, 3}, 7332));

        System.out.println("After changing:");
        verticalMatrixGroup.draw(consoleDrawer);

        //Thread.sleep(Duration.ofSeconds(5).toMillis());
        cm.undo();

        System.out.println("After undo last command:");
        verticalMatrixGroup.draw(consoleDrawer);
    }

    public void runDecoratorExample() {
        boolean bordersActive = true;
        MatrixDrawHandler<Integer> numberMatrixDrawHandler = new MatrixDrawHandlerImpl<>(
                new ConsoleMatrixDrawer<>(bordersActive)
        );

        DrawableMatrix<Integer> commonMatrix11 = new SparseMatrix<>(2, 2);
        MatrixInitializer.initialize(commonMatrix11, 4, 2);

        DrawableMatrix<Integer> commonMatrix12 = new CommonMatrix<>(3, 4);
        MatrixInitializer.initialize(commonMatrix12, 12, 4);

        DrawableMatrix<Integer> commonMatrix13 = new SparseMatrix<>(2, 1);
        MatrixInitializer.initialize(commonMatrix13, 2, 6);

        VerticalMatrixGroup<Integer> verticalMatrixGroup1 = new VerticalMatrixGroup<>();
        verticalMatrixGroup1.addMatrix(commonMatrix11);
        verticalMatrixGroup1.addMatrix(commonMatrix12);
        verticalMatrixGroup1.addMatrix(commonMatrix13);

        System.out.println("Original matrix:");
        verticalMatrixGroup1.draw(numberMatrixDrawHandler);
        System.out.println();

        EnumeratedMatrixDecorator<Integer> enumeratedMatrix = new EnumeratedMatrixDecorator<>(verticalMatrixGroup1);
        enumeratedMatrix.swapRows(1, 0);
        enumeratedMatrix.swapColumns(0, 1);
        System.out.println("Decorated matrix:");
        enumeratedMatrix.draw(numberMatrixDrawHandler);
    }

    public void runGroupMatrixExample() {
        boolean bordersActive = true;
        MatrixDrawHandler<Integer> matrixDrawHandler = new MatrixDrawHandlerImpl<>(
                new ConsoleMatrixDrawer<>(bordersActive)
        );

        int maxValue = 9999;
        int nonZeroCount = Integer.MAX_VALUE;

        VerticalMatrixGroup<Integer> verticalMatrixGroup = prepareVerticalMatrixGroup(maxValue, nonZeroCount);

        verticalMatrixGroup.draw(matrixDrawHandler);
    }

    private VerticalMatrixGroup<Integer> prepareVerticalMatrixGroup(int maxValue, int nonZeroCount) {
        DrawableMatrix<Integer> commonMatrix11 = new CommonMatrix<>(2, 2);
        MatrixInitializer.initialize(commonMatrix11, nonZeroCount, maxValue);

        DrawableMatrix<Integer> commonMatrix12 = new SparseMatrix<>(4, 3);
        MatrixInitializer.initialize(commonMatrix12, 4 * 3 / 2, maxValue);

        DrawableMatrix<Integer> commonMatrix13 = new SparseMatrix<>(1, 3);
        MatrixInitializer.initialize(commonMatrix13, nonZeroCount, maxValue);

        HorizontalMatrixGroup<Integer> horizontalMatrixGroup1 = new HorizontalMatrixGroup<>();
        horizontalMatrixGroup1.addMatrix(commonMatrix11);
        horizontalMatrixGroup1.addMatrix(commonMatrix12);
        horizontalMatrixGroup1.addMatrix(commonMatrix13);


        DrawableMatrix<Integer> commonMatrix21 = new CommonMatrix<>(2, 4);
        MatrixInitializer.initialize(commonMatrix21, nonZeroCount, maxValue);

        DrawableMatrix<Integer> commonMatrix22 = new CommonMatrix<>(2, 3);
        MatrixInitializer.initialize(commonMatrix22, nonZeroCount, maxValue);

        HorizontalMatrixGroup<Integer> horizontalMatrixGroup2 = new HorizontalMatrixGroup<>();
        horizontalMatrixGroup2.addMatrix(commonMatrix21);
        horizontalMatrixGroup2.addMatrix(commonMatrix22);


        DrawableMatrix<Integer> commonMatrix31 = new CommonMatrix<>(1, 1);
        MatrixInitializer.initialize(commonMatrix31, 1, maxValue);

        VerticalMatrixGroup<Integer> verticalMatrixGroupMain = new VerticalMatrixGroup<>();
        verticalMatrixGroupMain.addMatrix(horizontalMatrixGroup1);
        verticalMatrixGroupMain.addMatrix(horizontalMatrixGroup2);
        verticalMatrixGroupMain.addMatrix(commonMatrix31);
        return verticalMatrixGroupMain;
    }

    private void handleMatrixAction(String action, int actionCount) {
        System.out.print(AnsiEscapeCodesAction.getCursorUpCode(actionCount + 1));

        if (matrix == null || !action.equals(lastAction)) {
            switch (action) {
                case "1":
                    matrix = new CommonMatrix<>(numRows, numCols);
                    break;
                case "2":
                    matrix = new SparseMatrix<>(numRows, numCols);
                    break;
                default:
                    return;
            }
            MatrixInitializer.initialize(matrix, (int) (numRows * numCols * 0.75), maxValue);

            lastAction = action;
            System.out.print(AnsiEscapeCodesAction.getCursorDownCode(actionCount + 1));
        }
    }
}
