import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.*;

public class PeerList {
   private static Scanner input;

   public static void openFile(String filename) {
       try {
           input = new Scanner(new File(filename));
       } catch (FileNotFoundException ex) {
           Logger.getLogger(PeerList.class.getName()).log(Level.SEVERE, null, ex);
       }
   }

   public static ArrayList<Peer> readPeers() {
       ArrayList<Peer> peerList = new ArrayList<Peer>();
       while (input.hasNext()) {
           String peerIp = input.next();
           peerList.add(new Peer(peerIp));
       }
       return peerList;
   }

   public static void closeFile() {
       if (input != null) {
           input.close();
       }
   }

}
