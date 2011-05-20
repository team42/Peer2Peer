package command;

import java.net.DatagramPacket;

/**
 * <code>UnknownCommand</code> is invoked when an unknown command is received
 * from the Card Reader.
 *
 *
 * @see Command
 *
 * @author Nicolai, Lasse
 */
public class UnknownCommand extends Command {

	/**
	 * When an unknown command is received an simple "Unknown Command",
	 * the message and the command is printed.
	 * 
	 * @param receivedMessage - The received message
	 * @param receivePacket - The packet containing IP etc of sender
	 */
    public void execute(String receivedMessage, DatagramPacket receivePacket) {
    	System.out.println("================ UNKNOWN COMMAND ================");
    	System.out.println("Unkown Command:\n" + receivedMessage);
    }
}