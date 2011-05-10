package taxicomm;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import model.Trip;

public class TaxiComm {

   private static final int PORT = 4242;
   private static DatagramSocket datagramSocket;
   private static DatagramPacket inPacket, outPacket;
   private static byte[] buffer;

   ArrayList<Trip> tripList = new ArrayList<Trip>();
   Trip curTrip = null;

   public TaxiComm() {
      tripList.add(new Trip("257",1,"11.11;11.1122.22;22.2233.33;33.33"));
      tripList.add(new Trip("389",0,"44.44;44.4455.55;55.5566.66;66.66"));
      tripList.add(new Trip("438",0,"77.77;77.7788.88;88.8899.99;99.99"));

      System.out.println("Opening port...\n");

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
      String table = "";

      try {
         String messageIn,messageOut = "";

         do {
            buffer = new byte[512];
            inPacket = new DatagramPacket(buffer, buffer.length);
            datagramSocket.receive(inPacket);

            InetAddress clientAddress = inPacket.getAddress();
            int clientPort = inPacket.getPort();

            messageIn = new String(inPacket.getData(), 0, inPacket.getLength());

            taxiID = messageIn.substring(0, 3);
            coords = messageIn.substring(3, 14);
            answer = messageIn.charAt(14);
            if(answer == '1') {
               tripID = messageIn.substring(15);
               // accept trip by tripID
            } else if(answer == '2') {
               tripID = messageIn.substring(15);
               // decline trip by tripID
            }

            String time;
            // Set taxi coordinates
            // Get table by taxiID
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
