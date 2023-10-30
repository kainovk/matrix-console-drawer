package matrix.drawer;

import matrix.AbstractMatrix;
import util.AnsiEscapeCodesAction;

public class ConsoleMatrixDrawer<T extends Number> implements Drawer<T> {

    @Override
    public void drawMatrix(AbstractMatrix<T> matrix) {
        int rowNum = matrix.getRowCount();
        int colNum = matrix.getColumnCount();

        System.out.print(AnsiEscapeCodesAction.getCursorUpCode(rowNum + 1));

        for (int i = 0; i < rowNum; i++) {
            System.out.print(AnsiEscapeCodesAction.getCursorRightCode(1));
            for (int j = 0; j < colNum - 1; j++) {
                String element = matrix.getElementToDraw(i, j);
                System.out.printf("%7s ", element);
            }
            String element = matrix.getElementToDraw(i, colNum - 1);
            System.out.printf("%7s", element);
            System.out.print(AnsiEscapeCodesAction.getCursorDownCode(1));
            System.out.print(AnsiEscapeCodesAction.getCursorAtColumnCode(1));
        }

        System.out.print(AnsiEscapeCodesAction.getCursorDownCode(1));
    }

    @Override
    public void drawBorders(int width, int height, int dx, int dy) {
        System.out.print(AnsiEscapeCodesAction.getCursorUpCode(dy));
        System.out.print(AnsiEscapeCodesAction.getCursorAtColumnCode(0));

        String border = createHorizontalBorderString(width, dx);
        System.out.print(border);

        System.out.print(AnsiEscapeCodesAction.getCursorAtColumnCode(1));
        System.out.print(AnsiEscapeCodesAction.getCursorDownCode(height + 1));

        System.out.println(border);
    }

    @Override
    public void hideBorders(int width, int height, int dx, int dy) {
        String clearUpperLineCode = AnsiEscapeCodesAction.getClearUpperLineCode();
        System.out.print(clearUpperLineCode);
        System.out.print(AnsiEscapeCodesAction.getCursorUpCode(dy - 2));
        System.out.print(clearUpperLineCode);

        System.out.print(AnsiEscapeCodesAction.getCursorAtColumnCode(1));
        System.out.print(AnsiEscapeCodesAction.getCursorDownCode(height));
    }

    private String createHorizontalBorderString(int width, int dx) {
        StringBuilder border = new StringBuilder();

        border.append(" ".repeat(dx));
        border.append("+-");
        for (int col = 0; col < width; col++) {
            border.append("-".repeat(7));
            border.append("+");
        }

        return border.toString();
    }
}
