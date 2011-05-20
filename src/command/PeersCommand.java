package command;

import java.io.IOException;
import java.net.DatagramPacket;
import java.util.ArrayList;

import model.Peer;


import config.Configuration;

/**
 * 
 * This command is used after the Peer has announced itself to the network, all
 * other Peers will respond with this command to share their peer list.
 * 
 * @author Nicolai
 *
 */
public class PeersCommand extends Command {

   private Configuration config = Configuration.getConfiguration();

   /**
    * This execute will identify the peerlist and split it to a string array.
    * This string array will be converted to an arraylist of strings.
    * The peerlist will be added to the old peer list.
    * 
    * @param receivedMessage - The received message
    * @param receivePacket - The packet containing IP etc of sender
    */
   public void execute(String receivedMessage, DatagramPacket receivePacket) {
	   System.out.println("================ PEER LIST ================");
	   
	   String newPeerList = receivedMessage.substring(5);

      String[] arPeerList = newPeerList.split("%");

      ArrayList<Peer> peerList = config.getPeers();

      // Add to peer list if not present already
      for(int i=0; i < arPeerList.length; i++) {
         int found = 0;
         for (int j=0; j<peerList.size(); j++) {
            if(arPeerList[i].equals(peerList.get(j).getIp())) {
               found = 1;
            }
         }
         if(found == 0) {
            peerList.add(new Peer(arPeerList[i], 1));
            try {
               config.setPeers(peerList);
            } catch (IOException e1) {
               e1.printStackTrace();
            }
         }
      }

      try {
         config.setPeers(peerList);
      } catch (IOException e) {
         e.printStackTrace();
      }
   }

}
