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
    * @return value of ip
    */
   public String getIp() {
      return this.ip;
   }
   
   /**
    * Returns status of peer
    * 
    * @return  value of status
    */
   public int getStatus() {
      return this.status;
   }
   
   /**
    * Returns the IP and status as a
    * string representation
    * 
    * @return value of ip and status
    * 
    */
   public String toString() {
      return ip + "," + status;      
   }

}
