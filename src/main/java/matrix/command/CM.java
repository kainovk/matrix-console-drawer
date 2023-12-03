package matrix.command;

import java.util.Stack;

public class CM {

    private static CM INSTANCE;

    private final Stack<Command> commandsHistory;
    private volatile boolean block;

    private CM() {
        commandsHistory = new Stack<>();
    }

    public static CM instance() {
        if (INSTANCE == null) {
            INSTANCE = new CM();
        }
        return INSTANCE;
    }

    public void registry(Command command) {
        if (block) return;

        command.execute();
        commandsHistory.push(command);
    }

    public void undo() {
        // first command is CommandInit
        if (commandsHistory.size() <= 1) return;

        block = true;
        Command lastCommand = commandsHistory.peek();
        if (!(lastCommand instanceof UndoableCommand)) return;

        commandsHistory.pop();
        ((UndoableCommand) lastCommand).undo();
        block = false;
    }
}
