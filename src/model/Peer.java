package model;

/**
 * Object representation of values read in from "peers" text file.
 * 
 * @author Lasse
 *
 */
public class Peer {
   private String ip;
   private int status;

   /**
    * 
    * Constructor
    * 
    * Sets peer IP and status
    * 
    * @param ip
    * @param status
    */
   public Peer(String ip, int status) {
      this.ip = ip;
      this.status = status;
   }

   /**
    * Returns peer IP
    * 
    * @return
    */
   public String getIp() {
      return this.ip;
   }
   
   /**
    * Returns status of peer
    * 
    * @return
    */
   public int getStatus() {
      return this.status;
   }
   
   /**
    * Returns the IP and status as a
    * string representation
    * 
    * @return
    * 
    */
   public String toString() {
      return ip + "," + status;      
   }

}
