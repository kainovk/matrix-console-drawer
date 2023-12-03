package matrix.command.commands;

import matrix.DrawableMatrix;
import matrix.command.AbstractCommand;

public class CommandChangeValue<T extends Number> extends AbstractCommand {

    private final DrawableMatrix<T> matrix;
    private final int[] coords;
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
        matrix.set(coords[0], coords[1], newValue);
        System.out.println("Executed CommandChangeValue with args: "
                + coords[0] + ", " + coords[1] + ", " + newValue);
    }
}
