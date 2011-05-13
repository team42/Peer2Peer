package taxicomm;

import java.io.*;
import java.net.*;
import java.util.*;
import peer.*;
import database.*;

import model.Trip;

public class TaxiComm {

   private static final int PORT = 4242;
   private static DatagramSocket datagramSocket;
   private static DatagramPacket inPacket, outPacket;
   private static byte[] buffer;

   ArrayList<Trip> tripList = new ArrayList<Trip>();
   Trip curTrip = null;

   TripsDAO tripsDAO = new TripsDAO();
   TaxiDAO taxiDAO = new TaxiDAO();
   
   public TaxiComm() {
      
	  System.out.println("Opening port...\n");
	  tripsDAO.testTrips("A00001", "AB99999999", "1234,5678", "192.168.1.102");
	  tripsDAO.testTrips("A00001", "AB12345678", "1234,5678", "192.168.1.102");
	  tripsDAO.testTrips("A00002", "AB12345678", "1234,5678", "192.168.1.102");

      try {
         datagramSocket = new DatagramSocket(PORT);
      } catch(SocketException sockEx) {
         System.out.println("Unable to attach to port!");
         System.exit(1);
      }
      handleClient();
   }

   private void handleClient() {
      String taxiID, coords, tripID = "";
      char answer;

      try {
         String messageIn,messageOut = "";

         do {
            buffer = new byte[1024];
            inPacket = new DatagramPacket(buffer, buffer.length);
            datagramSocket.receive(inPacket);

            InetAddress clientAddress = inPacket.getAddress();
            int clientPort = inPacket.getPort();

            messageIn = new String(inPacket.getData(), 0, inPacket.getLength());
            System.out.println(messageIn);

            taxiID = messageIn.substring(0, 6);
            coords = messageIn.substring(6, 15);
            answer = messageIn.charAt(15);
            
            taxiDAO.updateTaxiPosition(taxiID, coords);
            
            if(answer == '1') {
               tripID = messageIn.substring(16);
               
               String query = "TAXAC" + taxiID + tripID;
               
               String returnIP = tripsDAO.getReturnIP(tripID);
               
               UDPPeer.sendMessages(InetAddress.getByName(returnIP), query);
            } else if(answer == '2') {
               tripID = messageIn.substring(16);
               tripsDAO.taxiDeleteTrip(taxiID, tripID);
               taxiDAO.setTaxiFree(taxiID);
            } else if(answer == '3') {
               tripID = messageIn.substring(16);
               tripsDAO.taxiDeleteTrip(taxiID, tripID);
            }

            tripList = tripsDAO.getTripsByTaxiID(taxiID);
            
            String time;
            
            for(int i=0;i<tripList.size();i++) {
               curTrip = tripList.get(i);
               time = compareTime(Calendar.getInstance().getTime(), curTrip.getDate());
               messageOut += curTrip.getTripID() + curTrip.getAccepted() + time + curTrip.getCoords() + "%";
            }

            outPacket = new DatagramPacket(messageOut.getBytes(), messageOut.length(),clientAddress,clientPort);
            datagramSocket.send(outPacket);

            messageOut = "";
         } while (true);
      } catch(IOException ioEx) {
         ioEx.printStackTrace();
      } finally {
         System.out.println("\n* Closing connection... *");
         datagramSocket.close();
      }
   }

   public String compareTime(Date d1, Date d2) {       
      long difference = Math.abs(d1.getTime()-d2.getTime());
      difference = difference / 1000;
      String seconds = ""+difference % 60;  
      String minutes = ""+(difference % 3600)/60;
      if(seconds.length() < 2) seconds = "0" + seconds;
      if(minutes.length() < 2) minutes = "0" + minutes;
      String time =  minutes + ":" + seconds;  
      return time;
   }
}
