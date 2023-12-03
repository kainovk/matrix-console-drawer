package matrix.command;

public abstract class AbstractCommand implements Command {

    @Override
    public void execute() {
        this.doExecute();
    }

    protected abstract void doExecute();
}
