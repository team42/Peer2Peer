import java.io.IOException;
import java.net.*;
import java.util.ArrayList;

import command.CommandController;

import database.*;

/**
 * UDP Peer implementation.
 * 
 * Code taken from IDIST3 lecture notes and modified to suit this project. 
 * 
 * @author lasse
 *
 */
public class UDPPeer {

   static UDPListenThread L;
   static InetAddress IPAddress;
   static CommandController  cmdControl = new CommandController();

   static int serverPort = 50000;
   static int clientPort = 50001;
   static DatagramSocket peerSocket;

   static byte[] queryRaw = new byte[256];
   static byte[] replyRaw = new byte[256];

   /**
    * Starts a client and server which enables 
    * communication with other discovered peers in the network.
    * 
    * The server runs in a different thread
    * @param args
    * @throws IOException 
    * @throws UnknownHostException
    */
   public static void main(String[] args) throws IOException {
      DAO dao = new DAO();
      dao.listPeers();
      try {
         peerSocket = new DatagramSocket(clientPort);
         IPAddress = getAlivePeer();
      } catch (Exception e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }

      // Start the server
      L = new UDPListenThread(serverPort);
      L.setDaemon(true);
      L.start();

      String query = "HELO";
      sendMessages(IPAddress, query);

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
   private static void sendMessages(InetAddress ip, String query) throws IOException {
      queryRaw = query.getBytes();
      
      // Send packet
      DatagramPacket sendPacket = new DatagramPacket(queryRaw, queryRaw.length, ip, serverPort);
      peerSocket.send(sendPacket);
      System.out.println("Send to: " + IPAddress + "\nData: " + query);
   }

   /**
   * Opens "peers" textfile and pings the ip address. If it responds we return 
   * the ip to the calling method.
   *
   * @throws Exception
   * @returns 
   **/
   private static InetAddress getAlivePeer() throws Exception {
      PeerList.openFile("peers");
      ArrayList<Peer> peerList = PeerList.readPeers();

      for(int i=0;i<peerList.size();i++) {
         String ip = peerList.get(i).getIp();
         InetAddress peer = InetAddress.getByName(ip);
         if(peer.isReachable(20)) {
            System.out.println(peer + " is alive!");
            return peer;
         } else {
            System.out.println(peer + " is dead...");
         }
      }
      return null;
   }
}
