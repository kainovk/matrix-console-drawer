package util;

// https://en.wikipedia.org/wiki/ANSI_escape_code
public class AnsiEscapeCodesAction {

    private static final String CURSOR_MOVE = "\033[";
    private static final String CURSOR_START_PREVIOUS_LINE = "\033[1F\33[K";

    public static String getCursorUpCode(int n) {
        if (n > 0) {
            return CURSOR_MOVE + n + "A";
        }
        return "";
    }

    public static String getCursorDownCode(int n) {
        if (n > 0) {
            return CURSOR_MOVE + n + "B";
        }
        return "";
    }

    public static String getCursorRightCode(int n) {
        if (n > 0) {
            return CURSOR_MOVE + n + "C";
        }
        return "";
    }

    public static String getCursorLeftCode(int n) {
        if (n > 0) {
            System.out.print(CURSOR_MOVE + n + "D");
        }
        return "";
    }

    public static String getClearUpperLineCode() {
        return CURSOR_START_PREVIOUS_LINE;
    }

    public static String getCursorAtColumnCode(int n) {
        return CURSOR_MOVE + n + "G";
    }

    public static String saveCurrentCursorPosition() {
        return CURSOR_MOVE + "s";
    }

    public static String restoreSavedCursorPosition() {
        return CURSOR_MOVE + "u";
    }
}
