package peer;

import java.io.*;
import java.net.URL;
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
   private String filename = "peers.txt";

   public PeerList() {
      File f = new File(filename);
      System.out.println(f + (f.exists()? " is found " : " is missing. Creating file..."));
      try {

         if(!f.exists()) {
            f.createNewFile();

            // Read peers file from jar archive
            InputStream in = getClass().getResourceAsStream("/peer/peers");
            InputStreamReader isr = new InputStreamReader(in);
            BufferedReader br = new BufferedReader(isr);
            String line;
            
            //output to proper file
            while ((line = br.readLine()) != null) {
               output = new Formatter(filename);               
               output.format("%s\n", line);
               output.close();
            }                      
         }
      } catch (IOException io) {
         System.out.println("File not found");
      }
   }


   /**
    * Opens file for reading or writing.
    *
    * @param filename
    * @param rw 0 = read, 1 = write
    */
   public void openFile(int rw) {
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
         System.out.println("affe");
         String ip = input.next();
         int status = 0;//input.nextInt();
         peerList.add(new Peer(ip,status));
         System.out.println(peerList.size());
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
