package peer;
import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import config.Configuration;
import command.CommandController;
import database.*;
import peer.*;
import taxicomm.TaxiComm;

/**
 * UDP Peer implementation.
 * 
 * Code taken from IDIST3 lecture notes (supplied by bhc) and modified to suit 
 * this project. 
 * 
 * @author Lasse
 *
 */
public class UDPPeer {

   static UDPListenThread L;
   static InetAddress IPAddress;
   static CommandController  cmdControl = new CommandController();
   static Configuration config = Configuration.getConfiguration();

   static int serverPort = 50000;
   static int clientPort = 50001;
   static DatagramSocket peerSocket;

   static byte[] queryRaw = new byte[1024];
   static byte[] replyRaw = new byte[1024];

   /**
    * Starts a client and server which enables 
    * communication with other discovered peers in the network.
    * 
    * @param args
    * @throws IOException 
    * @throws UnknownHostException
    */
   public static void main(String[] args) throws IOException {
//      DAO dao = new DAO();
//      dao.listPeers();
      
      //TaxiComm taxiComm = new TaxiComm();
      
      try {
         peerSocket = new DatagramSocket(clientPort);
         IPAddress = getAlivePeer();
      } catch (Exception e) {
         e.printStackTrace();
      }

      // Start the server
      L = new UDPListenThread(serverPort);
      L.setDaemon(true);
      L.start();

      if(IPAddress == null) {
         System.out.println("No peers found");
      } else {
         String query = "HELLO";
         sendMessages(IPAddress, query);
      }      

      while(true) {
         // Receive packet
         DatagramPacket receivePacket = new DatagramPacket(replyRaw, replyRaw.length);
         peerSocket.receive(receivePacket);
         String reply = new String(receivePacket.getData(), 0, receivePacket.getLength());
         cmdControl.processRequest(reply);
         System.out.println("Reply from: " + receivePacket.getAddress() + "\nData: " + reply);
      }
   }

   /**
    * Sends a UDP message to the supplied IP address with the given data.
    *
    * @param ip IP address to send UDP packet to.
    * @param query Data to send.
    *
    **/
   public static void sendMessages(InetAddress ip, String query) throws IOException {
      queryRaw = query.getBytes();

      // Send packet
      DatagramPacket sendPacket = new DatagramPacket(queryRaw, queryRaw.length, ip, serverPort);
      peerSocket.send(sendPacket);
      System.out.println("Send to: " + IPAddress + "\nData: " + query);
   }

   /**
    * Opens "peers" text file and pings the IP address. If it responds we return 
    * the IP to the calling method.
    * 
    * Cleans up the peer/peers text file, so that it only contains alive peers.
    *
    * @throws Exception
    * @returns 
    **/
   private static InetAddress getAlivePeer() throws Exception {
      PeerList peerList = new PeerList();
      peerList.openFile(0);
      ArrayList<Peer> peers = peerList.readPeerList();
      
      /*
       * Check if peers are alive. Removes ones that don't respond to ping.
       */
      for(int i=0;i<peers.size();i++) {
         String ip = peers.get(i).getIp();
         InetAddress peer = InetAddress.getByName(ip);
         if(peer.isReachable(20)) {
            System.out.println(peer + " is alive!");
         } else {
            peers.remove(i);
            System.out.println(peer + " is dead...");
         }
      }
      peerList.closeInputFile();
      
      // Lets write the new list of alive peers
      peerList.openFile(1);
      peerList.writePeerList(peers);
      peerList.closeOutputFile();
      
      config.setPeers(peers); // Load peers into configuration.

      // return the first alive peer in the list
      return InetAddress.getByName(peers.get(0).getIp());
   }
}
