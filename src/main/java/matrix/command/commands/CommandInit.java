package matrix.command.commands;

import matrix.command.AbstractCommand;

public class CommandInit extends AbstractCommand {

    @Override
    protected void doExecute() {
        System.out.println("Initial command...");
    }
}
