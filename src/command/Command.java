package command;

/**
 * <code>Command</code> is the abstract class the Command classes must adhere to.
 *
 * It must also implement both <code>execute</code> methods as per the Command
 * design pattern.
 *
 * This code was originally written by hbe and has been modified to fit the goal
 * of this project.
 *
 * @see ConnectCommand
 * @see AcceptAckCommand
 * @see AdminSwipeCommand
 * @see CardSwipedCommand
 * @see UnknownCommand
 *
 * @author Mads, Lasse
 * @version $Id: Command.java 483 2010-12-16 08:26:14Z mads $
 */
public abstract class Command {
    /**
     * The execute method with the RFIDEventManagerSimple as an argument is invoked
     * when receiving commands over the RS232 link.
     *
     * @param rFIDEventManagerSimple
     */
    public abstract void execute(String receivedMessage);

    /**
     * The execute method with the RFIDEventManagerTest as an argument is only used
     * in the unit tests. This lets us test without using RS232 functionality.
     *
     * @param rFIDEventManagerTest
     * @return boolean which returns true if the operation is succesfull.
     */
    //public abstract boolean execute(RFIDEventManagerTest rFIDEventManagerTest);
}