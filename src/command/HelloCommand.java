package command;

import java.io.IOException;
import java.net.DatagramPacket;
import java.util.ArrayList;

import peer.UDPPeer;

import model.Peer;
import config.Configuration;

/**
 * This command is used when another Peer announces itself.
 * 
 * @author Nicolai
 *
 */
public class HelloCommand extends Command {

   //
   Configuration config = Configuration.getConfiguration();

   /**
    * The execute method will add the peer get it's own PeerList and send it back to the sender.
    * 
    * @param receivedMessage - The received message
    * @param receivePacket - The packet containing IP etc of sender
    */
   public void execute(String receivedMessage, DatagramPacket receivePacket) {

	   System.out.println("================ HELLO ================");
	   
      ArrayList<Peer> peerList = config.getPeers();
      String strPeerList = "";

      for(int i=0; i > peerList.size(); i++) {
         strPeerList += strPeerList + peerList.get(i) + "%";
      }

      String reply = "PEERS" + strPeerList;

      System.out.println("Announced peer: " + receivePacket.getAddress().getHostAddress());

      // Add to peer list if not present already
      int found = 0;
      for (int j=0; j<peerList.size(); j++) {         
         if(receivePacket.getAddress().getHostAddress().equals(peerList.get(j).getIp())) {
            found = 1;
         }
      }
      
      if(found == 0) {
         peerList.add(new Peer(receivePacket.getAddress().getHostAddress(),1));
         try {
            config.setPeers(peerList);
         } catch (IOException e1) {
            e1.printStackTrace();
         }
      }
      
      try {
		UDPPeer.sendMessages(receivePacket.getAddress(), reply);
	} catch (IOException e) {
		e.printStackTrace();
	}
   }

}
