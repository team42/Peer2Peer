import java.io.*;
import java.net.*;

/**
* Server thread.
*
**/
public class UDPListenThread extends Thread {
   
   private int port;
   private InetAddress localIP = null;
   
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
         byte[] queryRaw = new byte[256];
         byte[] replyRaw = new byte[256];
         
         while(true) {
            // Creates a packet to hold the received data
            DatagramPacket receivePacket = new DatagramPacket(queryRaw, queryRaw.length);

            peerSocket.receive(receivePacket);
            System.out.println("affe");
            String query = new String(receivePacket.getData(), 0, receivePacket.getLength());
            String command = query.substring(0, 4);
            InetAddress IPAddress = receivePacket.getAddress();
                        
            // Lets not show messages from our own localhost
            if(!localIP.equals(IPAddress)) {
               if(command.equals("HELO")) {
                  System.out.println("Peer logged in from: " + IPAddress);
                  
                  int peer = receivePacket.getPort();
                  
                  String reply = "REPL";
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
