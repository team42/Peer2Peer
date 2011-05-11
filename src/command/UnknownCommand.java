package command;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

/**
 * <code>UnknownCommand</code> is invoked when an unknown command is received
 * from the Card Reader.
 *
 * This class implements the Command abstract class.
 *
 * @see Command
 *
 * @author Mads, Lasse
 * @version $Id: UnknownCommand.java 483 2010-12-16 08:26:14Z mads $
 */
public class UnknownCommand extends Command {

    /**
     * Send a response back to Card Reader that the command was unknown.
     * 
     * @param rFIDEventManagerSimple object holding the data packet from Card Reader.
     */
    public void execute(String receivedMessage, DatagramSocket peerSocket, DatagramPacket receivePacket) {
        System.out.println("Unkown Command:\n" + receivedMessage);
    }

    /**
     * This method is only used for our unit test. It allows us to test the
     * UnknownCommand object without using the underlying RS232 functionality.
     *
     * @param rFIDEventManagerTest
     * @return When called returns true
     */
    /*@Override
    public boolean execute(RFIDEventManagerTest rFIDEventManagerTest) {
        rFIDEventManagerTest.sendRFIDResponse("99", "UnknownCommand");
        return true;
    }*/
}