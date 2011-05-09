package peer;

import java.io.*;
import java.util.*;
import java.util.Formatter;
import java.util.logging.*;

/**
 * Text file manipulation of "peers"
 * 
 * @author Lasse
 *
 */
public class PeerList {
   private Scanner input;
   private Formatter output;


   /**
    * Opens file for reading or writing.
    *
    * @param filename
    * @param rw 0 = read, 1 = write
    */
   public void openFile(int rw) {
      String filename = PeerList.class.getClass().getResource("/peer/peers").getPath().substring(35);
      System.out.println(filename);
      
      if(rw == 0) {
         try {
            input = new Scanner(new File(filename));
         } catch (FileNotFoundException ex) {
            Logger.getLogger(PeerList.class.getName()).log(Level.SEVERE, null, ex);
         }		   
      } else {
         try {
            output = new Formatter(filename);
         } catch (FileNotFoundException ex) {
            Logger.getLogger(PeerList.class.getName()).log(Level.SEVERE, null, ex);
         }
      }

   }
   
   /**
    * Creates an arraylist from text file.
    * 
    * @return arraylist containing values from file
    */
   @SuppressWarnings("unchecked")
   public ArrayList<Peer> readPeerList() {
      ArrayList<Peer> peerList = new ArrayList<Peer>();
      while (input.hasNext()) {
         String ip = input.next();
         int status = 0;//input.nextInt();
         peerList.add(new Peer(ip,status));
      }
      return peerList;
   }

   public void writePeerList(ArrayList<Peer> peerList) {
      if (output != null) {         
         for(int i=0;i<peerList.size();i++) {
            output.format("%s\n", peerList.get(i).toString());
         }
      }
   }

   /**
    * Closes the file (duh).
    * 
    */
   public void closeInputFile() {
      input.close();
   }
   
   public void closeOutputFile() {
      output.close();
   }
}
