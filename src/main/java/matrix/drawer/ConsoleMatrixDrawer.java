package matrix.drawer;

import matrix.DrawableMatrix;
import util.AnsiEscapeCodesAction;

public class ConsoleMatrixDrawer<T extends Number> extends AbstractDrawer<T> {

    public ConsoleMatrixDrawer(boolean bordersActive) {
        super(bordersActive);
    }

    @Override
    public void drawBorders(DrawableMatrix<T> matrix, int dx, int dy) {
        int height = matrix.getRowCount();
        int cellWidth = getCellSize(matrix);
        int cellCount = matrix.getColumnCount();

        System.out.print(AnsiEscapeCodesAction.getCursorUpCode(dy));
        System.out.print(AnsiEscapeCodesAction.getCursorAtColumnCode(0));

        String border = createHorizontalBorderString(cellCount, cellWidth, dx);
        System.out.print(border);

        System.out.print(AnsiEscapeCodesAction.getCursorAtColumnCode(1));
        System.out.print(AnsiEscapeCodesAction.getCursorDownCode(height + 1));

        System.out.println(border);
    }

    @Override
    public void hideBorders(DrawableMatrix<T> matrix, int dx, int dy) {
        int height = matrix.getRowCount();

        String clearUpperLineCode = AnsiEscapeCodesAction.getClearUpperLineCode();
        System.out.print(clearUpperLineCode);
        System.out.print(AnsiEscapeCodesAction.getCursorUpCode(dy - 2));
        System.out.print(clearUpperLineCode);

        System.out.print(AnsiEscapeCodesAction.getCursorAtColumnCode(1));
        System.out.print(AnsiEscapeCodesAction.getCursorDownCode(height));
    }

    @Override
    public void moveCursor(int dx, int dy) {
        if (dx > 0) {
            System.out.print(AnsiEscapeCodesAction.getCursorRightCode(dx));
        } else if (dx < 0) {
            System.out.print(AnsiEscapeCodesAction.getCursorLeftCode(-dx));
        }

        if (dy > 0) {
            System.out.print(AnsiEscapeCodesAction.getCursorUpCode(dy));
        } else if (dy < 0) {
            System.out.print(AnsiEscapeCodesAction.getCursorDownCode(-dy));
        }
    }

    @Override
    public void drawElement(DrawableMatrix<T> matrix, int row, int col) {
        String element = matrix.getDrawableValue(row, col);
        int width = getCellSize(matrix);
        System.out.printf("%" + width + "s", element);
    }

    private String createHorizontalBorderString(int count, int width, int dx) {
        StringBuilder border = new StringBuilder();

        border.append(" ".repeat(dx));
        border.append("+-");
        for (int col = 0; col < count; col++) {
            border.append("-".repeat(width - 1));
            border.append("+");
        }

        return border.toString();
    }
}
