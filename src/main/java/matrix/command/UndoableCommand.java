package matrix.command;

public interface UndoableCommand extends Command {

    void undo();
}
