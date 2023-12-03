package matrix.command.commands;

import matrix.DrawableMatrix;
import matrix.command.AbstractUndoableCommand;

public class CommandChangeValue<T extends Number> extends AbstractUndoableCommand {

    private final DrawableMatrix<T> matrix;
    private final int[] coords;
    private T prevValue;
    private final T newValue;

    public CommandChangeValue(DrawableMatrix<T> matrix, int[] coords, T newValue) {
        this.matrix = matrix;
        this.newValue = newValue;
        this.coords = new int[2];
        System.arraycopy(
                coords, 0,
                this.coords, 0, coords.length);
    }

    @Override
    protected void doExecute() {
        if (matrix == null) return;
        prevValue = matrix.get(coords[0], coords[1]);
        matrix.set(coords[0], coords[1], newValue);
        System.out.println("Executed CommandChangeValue with args: "
                + coords[0] + ", " + coords[1] + ", " + newValue);
    }

    @Override
    protected void doUndo() {
        if (matrix == null) return;
        matrix.set(coords[0], coords[1], prevValue);
        System.out.println("Undo CommandChangeValue with args: "
                + coords[0] + ", " + coords[1] + ", " + prevValue);
    }
}
