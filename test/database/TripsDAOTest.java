package database;

import static org.junit.Assert.*;

import java.net.InetAddress;
import java.util.ArrayList;

import model.Trip;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TripsDAOTest {
   TripsDAO instance;
   String taxiID, tripID, tripCoord, returnIP;

   @Before
   public void setUp() throws Exception {
      instance = new TripsDAO();
      taxiID = "X00001";
      tripID = "XY12345678";
      tripCoord = "1234,5678";
      returnIP = InetAddress.getLocalHost().getHostAddress();
   }

   @After
   public void tearDown() throws Exception {
      instance = null;      
   }
   
   @Test
   public void testInsertTrip() {
      boolean expResult = true;
      boolean result = instance.insertTrip(taxiID, tripID, tripCoord, returnIP);
      System.out.println("testInsertTrip result = Expected: " + result + " = " + expResult);
      assertEquals(expResult, result);
   }

   @Test
   public void testConfirmTrip() {
      boolean expResult = false;
      boolean result = instance.confirmTrip(taxiID, tripID);
      System.out.println("testConfirmTrip result = Expected: " + result + " = " + expResult);
      assertEquals(expResult, result);
   }

   @Test
   public void testTaxiTripAmount() {
      int expResult = 1;
      int result = instance.taxiTripAmount(taxiID);
      System.out.println("testTaxiTripAmount result = Expected: " + result + " = " + expResult);
      assertEquals(expResult, result);
   }

   @Test
   public void testGetTripsByTaxiID() {
      ArrayList<Trip>  expResult = instance.getTripsByTaxiID(taxiID);
      ArrayList<Trip> result = expResult;
      System.out.println("testGetTripsByTaxiID result = Expected: " + result + " = " + expResult);
      assertEquals(expResult, result);
   }

   @Test
   public void testGetReturnIP() {
      String expResult = returnIP;
      String result = instance.getReturnIP(tripID);
      System.out.println("testGetReturnIP result = Expected: " + result + " = " + expResult);
      assertEquals(expResult, result);
   }
   
   @Test
   public void testTaxiDeleteTrip() {
      boolean expResult = true;
      boolean result = instance.taxiDeleteTrip(taxiID, tripID);
      System.out.println("testTaxiDeleteTrip result = Expected: " + result + " = " + expResult);
      assertEquals(expResult, result);
   }
   
   @Test
   public void testDeleteTrip() {
      testInsertTrip();
      boolean expResult = true;
      boolean result = instance.deleteTrip(tripID);
      System.out.println("testDeleteTrip result = Expected: " + result + " = " + expResult);
      assertEquals(expResult, result);
   }

}
