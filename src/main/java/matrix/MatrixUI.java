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
import matrix.observer.SummingRowObserver;
import matrix.util.MatrixInitializer;
import util.AnsiEscapeCodesAction;

import java.util.Random;
import java.util.Scanner;

public class MatrixUI<T extends Number> {

    private static final CM cm = CM.instance();

    private SummingRowObserver<Integer> summingPassiveObserver;
    private CommonMatrix<Integer> matrix;
    private final int numRows;
    private final int numCols;
    private final T maxValue;
    private boolean bordersActive;
    private String lastAction;
    private VerticalMatrixGroup<Integer> verticalMatrixGroup = null;
    private Random rand = new Random();

    public MatrixUI(MatrixParameters<T> params) {
        MatrixUI.cm.registry(new CommandInit());
        this.numRows = params.getNumRows();
        this.numCols = params.getNumCols();
        this.maxValue = params.getMaxValue();
        this.matrix = null;
        this.bordersActive = true;
        this.lastAction = null;
    }

    public void runPassiveObserverExample() {
        boolean bordersActive = true;
        MatrixDrawHandler<Integer> matrixDrawHandler = new MatrixDrawHandlerImpl<>(
                new ConsoleMatrixDrawer<>(bordersActive)
        );

        matrix = new CommonMatrix<>(4, 3);
        summingPassiveObserver = new SummingRowObserver<>(matrix);

        Scanner sc = new Scanner(System.in);
        String action = "";
        while (!action.equals("exit")) {
            printCommands();
            action = sc.nextLine();
            handleMatrixActionMatrix(action, matrixDrawHandler);
        }
    }

    private void handleMatrixActionMatrix(String action,
                                          MatrixDrawHandler<Integer> matrixDrawHandler) {
        switch (action) {
            case "1":
                matrix = new CommonMatrix<>(4, 3);
                MatrixInitializer.initialize(matrix, 1000, 20);
                summingPassiveObserver.attach(matrix);
                break;
            case "2":
                int newValue = rand.nextInt(1000) + 1000;
                changeRandomCellMatrix(newValue);
                break;
            case "3":
                cm.undo();
                break;
            default:
                return;
        }
        matrix.draw(matrixDrawHandler);
        System.out.println("Summing matrix:");
        summingPassiveObserver.getState().draw(matrixDrawHandler);

        lastAction = action;
    }

    public void runPassiveObserverExampleComposite() {
        boolean bordersActive = true;
        MatrixDrawHandler<Integer> matrixDrawHandler = new MatrixDrawHandlerImpl<>(
                new ConsoleMatrixDrawer<>(bordersActive)
        );

        int maxValue = 9999;
        int nonZeroCount = Integer.MAX_VALUE;

        verticalMatrixGroup = prepareVerticalMatrixGroup2(50, 1000);
        summingPassiveObserver = new SummingRowObserver<>(verticalMatrixGroup);

        Scanner sc = new Scanner(System.in);
        String action = "";
        while (!action.equals("exit")) {
            printCommands();
            action = sc.nextLine();
            handleMatrixAction3(action, matrixDrawHandler);
        }
    }

    private void handleMatrixAction3(String action,
                                     MatrixDrawHandler<Integer> matrixDrawHandler) {
        switch (action) {
            case "1":
                verticalMatrixGroup = prepareVerticalMatrixGroup2(50, 1000);
                summingPassiveObserver = new SummingRowObserver<>(verticalMatrixGroup);
                break;
            case "2":
                int newValue = rand.nextInt(500) + 100;
                changeRandomCell(newValue);
                break;
            case "3":
                cm.undo();
                break;
            default:
                return;
        }
        verticalMatrixGroup.draw(matrixDrawHandler);
        System.out.println("Summing matrix:");
        DrawableMatrix<Integer> state = summingPassiveObserver.getState();
        state.draw(matrixDrawHandler);

        lastAction = action;
    }

    public void runUndoExample() {
        boolean bordersActive = true;
        MatrixDrawHandler<Integer> matrixDrawHandler = new MatrixDrawHandlerImpl<>(
                new ConsoleMatrixDrawer<>(bordersActive)
        );

        int maxValue = 9999;
        int nonZeroCount = Integer.MAX_VALUE;

        verticalMatrixGroup = prepareVerticalMatrixGroup(maxValue, nonZeroCount);

        Scanner sc = new Scanner(System.in);
        String action = "";
        while (!action.equals("exit")) {
            printCommands();
            action = sc.nextLine();
            handleMatrixAction2(action, matrixDrawHandler);
        }
    }

    private void handleMatrixAction2(String action, MatrixDrawHandler<Integer> matrixDrawHandler) {
        switch (action) {
            case "1":
                verticalMatrixGroup = prepareVerticalMatrixGroup(1000, 1000);
                verticalMatrixGroup.draw(matrixDrawHandler);
                break;
            case "2":
                int newValue = rand.nextInt(1000) + 1000;
                changeRandomCell(newValue);
                verticalMatrixGroup.draw(matrixDrawHandler);
                break;
            case "3":
                cm.undo();
                verticalMatrixGroup.draw(matrixDrawHandler);
                break;
            default:
                return;
        }

        lastAction = action;
    }

    private void changeRandomCellMatrix(int newValue) {
        int rowCount = matrix.getRowCount();
        int columnCount = matrix.getColumnCount();

        int rowIndex = rand.nextInt(rowCount);
        int columnIndex = rand.nextInt(columnCount);

        CommandChangeValue<Integer> changeValue =
                new CommandChangeValue<>(matrix, new int[]{rowIndex, columnIndex}, newValue);
        cm.registry(changeValue);
    }

    private void changeRandomCell(int newValue) {
        int rowCount = verticalMatrixGroup.getRowCount();
        int columnCount = verticalMatrixGroup.getColumnCount();

        int rowIndex = rand.nextInt(rowCount);
        int columnIndex = rand.nextInt(columnCount);

        CommandChangeValue<Integer> changeValue =
                new CommandChangeValue<>(verticalMatrixGroup, new int[]{rowIndex, columnIndex}, newValue);
        cm.registry(changeValue);
    }

    private void printCommands() {
        System.out.println("1. Создать новую матрицу");
        System.out.println("2. Изменить случайное значение матрицы");
        System.out.println("3. Отменить");
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

    private VerticalMatrixGroup<Integer> prepareVerticalMatrixGroup2(int maxValue, int nonZeroCount) {
        DrawableMatrix<Integer> commonMatrix11 = new CommonMatrix<>(2, 2);
        MatrixInitializer.initialize(commonMatrix11, nonZeroCount, maxValue);

        DrawableMatrix<Integer> commonMatrix12 = new CommonMatrix<>(2, 1);
        MatrixInitializer.initialize(commonMatrix12, 4 * 3 / 2, maxValue);

        // добавить одну сбоку последней в горизонтальную группу

        VerticalMatrixGroup<Integer> verticalMatrixGroupMain = new VerticalMatrixGroup<>();
        verticalMatrixGroupMain.addMatrix(commonMatrix11);
        verticalMatrixGroupMain.addMatrix(commonMatrix12);
        return verticalMatrixGroupMain;
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

    /*private void handleMatrixAction(String action, int actionCount) {
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
    }*/
}
