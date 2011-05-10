package peer;
import java.io.*;
import java.net.*;
import java.util.ArrayList;

import config.Configuration;

/**
* Server thread. Waits for messages and responds to requests.
*
**/
public class UDPListenThread extends Thread {
   
   public int port;
   public InetAddress localIP = null;
   public Configuration config = Configuration.getConfiguration();
   
   
   /**
   * Constructor
   **/
   public UDPListenThread(int port) {     
      this.port  = port;
      
      try {
         localIP = InetAddress.getLocalHost();
      } catch (UnknownHostException e) {
         System.out.println(e);
      }
   }
   
   public void run() {
      try {
         DatagramSocket peerSocket = new DatagramSocket(port);
         byte[] queryRaw = new byte[1024];
         byte[] replyRaw = new byte[1024];
         
         ArrayList<Peer> peers =  config.getPeers();
         
         while(true) {
            // Creates a packet to hold the received data
            DatagramPacket receivePacket = new DatagramPacket(queryRaw, queryRaw.length);
            peerSocket.receive(receivePacket);
            
            String query = new String(receivePacket.getData(), 0, receivePacket.getLength());
            String command = query.substring(0, 5);
            InetAddress IPAddress = receivePacket.getAddress();
                        
            // Lets not show messages from our own localhost
            if(!localIP.equals(IPAddress)) {
               if(command.equals("HELLO")) {
                  System.out.println("Peer logged in from: " + IPAddress);
                  // In my list of peers?
                  if(peers.contains(IPAddress)) {
                     System.out.println("wohoo");
                  } else {
                     System.out.println("Peer not seen before, so added to list of peers");
                  }
                  
                  int peer = receivePacket.getPort();
                  
                  String reply = "PEERS";
                  replyRaw = reply.getBytes();
                  DatagramPacket sendPacket = new DatagramPacket(replyRaw, replyRaw.length, IPAddress, peer);
                  peerSocket.send(sendPacket);
               }
                             
            }                      
         }
      } catch(IOException e) {
         e.printStackTrace();         
      }
   }

}
