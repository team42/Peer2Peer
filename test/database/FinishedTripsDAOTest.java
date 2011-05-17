package database;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * JUnit test for <code>FinishedTripsDAO</code>.
 * 
 * @author lasse
 *
 */
public class FinishedTripsDAOTest {
   FinishedTripsDAO instance;
   String taxiID, tripID, tripCoord, returnIP;

   @Before
   public void setUp() throws Exception {
      instance = new FinishedTripsDAO();
      taxiID = "X00001";
      tripID = "XY12345678";
   }

   @After
   public void tearDown() throws Exception {
      instance = null;
   }

   @Test
   public void testAddTrip() {
      boolean expResult = true;
      boolean result = instance.addTrip(tripID, taxiID);
      System.out.println("testAddTrip result = Expected: " + result + " = " + expResult);
      assertEquals(expResult, result);
   }

   @Test
   public void testIsTripFinished() {
      boolean expResult = true;
      boolean result = instance.isTripFinished(tripID);
      System.out.println("testIsTripFinished result = Expected: " + result + " = " + expResult);
      assertEquals(expResult, result);
   }

}
