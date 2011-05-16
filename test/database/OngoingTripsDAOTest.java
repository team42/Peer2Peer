package database;

import static org.junit.Assert.*;

import java.util.ArrayList;

import model.Taxi;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class OngoingTripsDAOTest {
   OngoingTripsDAO instance;
   String taxiID, tripID, taxiCoord, company;;
   ArrayList<Taxi> taxiList;
   Taxi taxi;

   @Before
   public void setUp() throws Exception {
      instance = new OngoingTripsDAO();
      taxiID = "X00001";
      tripID = "XY12345678";
      taxiCoord = "1234,5678";
      company = "XY";
      taxiList = new ArrayList<Taxi>();
      taxiList.add(new Taxi(taxiID, taxiCoord, company));
   }

   @After
   public void tearDown() throws Exception {
      instance = null;
   }

   @Test
   public void testAddAwaitingTaxis() {
      boolean expResult = true;
      boolean result = instance.addAwaitingTaxis(tripID, taxiList, company);
      System.out.println("testAddAwaitingTaxis result = Expected: " + result + " = " + expResult);
      assertEquals(expResult, result);
   }
   
   @Test
   public void testGetTaxiByTrip() {
      ArrayList<Taxi> expResult = taxiList;
      ArrayList<Taxi> result = instance.getTaxiByTrip(tripID);
      System.out.println("testGetTaxiByTrip result = Expected: " + result.get(0).getTaxiID() + " = " + expResult.get(0).getTaxiID());
      assertEquals(expResult.get(0).getTaxiID(), result.get(0).getTaxiID());
   }

   @Test
   public void testGetCompanyIP() {
      ArrayList<String> expResult = new ArrayList<String>();
      expResult.add("XY");
      ArrayList<String> result = instance.getCompanyIP(tripID);
      System.out.println("testGetCompanyIP result = Expected: " + result.get(0) + " = " + expResult);
      assertEquals(expResult, result);
   }

   @Test
   public void testDeleteOngoingTrip() {
      boolean expResult = true;
      boolean result = instance.deleteOngoingTrip(tripID);
      System.out.println("testDeleteOngoingTrip result = Expected: " + result + " = " + expResult);
      assertEquals(expResult, result);
   }
}
