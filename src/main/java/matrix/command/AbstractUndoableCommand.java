package matrix.command;

public abstract class AbstractUndoableCommand implements UndoableCommand {

    @Override
    public void execute() {
        this.doExecute();
    }

    @Override
    public void undo() {
        this.doUndo();
    }

    protected abstract void doExecute();

    protected abstract void doUndo();
}
