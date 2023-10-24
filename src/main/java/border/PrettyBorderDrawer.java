package border;

import util.AnsiEscapeCodesAction;

public class PrettyBorderDrawer implements BorderDrawer {

    // TODO: add vertical borders
    @Override
    public void drawBorders(int width, int height, int dx, int dy) {
        String cursorUpCode = AnsiEscapeCodesAction.getCursorUpCode(dy);
        System.out.print(cursorUpCode);
        String cursorAtColumnCode = AnsiEscapeCodesAction.getCursorAtColumnCode(0);
        System.out.print(cursorAtColumnCode);

        String border = createHorizontalBorderString(width, dx);
        System.out.print(border);

        String cursorAtColumn1Code = AnsiEscapeCodesAction.getCursorAtColumnCode(1);
        System.out.print(cursorAtColumn1Code);
        String cursorDownCode = AnsiEscapeCodesAction.getCursorDownCode(height - 1);
        System.out.print(cursorDownCode);

        System.out.println(border);
    }

    @Override
    public void hideBorders(int width, int height, int dx, int dy) {
        String clearUpperLineCode = AnsiEscapeCodesAction.getClearUpperLineCode();
        System.out.print(clearUpperLineCode);
        String cursorUpCode = AnsiEscapeCodesAction.getCursorUpCode(dy-2);
        System.out.print(cursorUpCode);

        System.out.print(clearUpperLineCode);

        String cursorAtColumn1Code = AnsiEscapeCodesAction.getCursorAtColumnCode(1);
        System.out.print(cursorAtColumn1Code);
        String cursorDownCode = AnsiEscapeCodesAction.getCursorDownCode(height);
        System.out.print(cursorDownCode);
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
