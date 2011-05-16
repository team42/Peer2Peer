package model;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

/**
 * JUnit test for <code>Trip</code>.
 * 
 * @author lasse
 *
 */
public class TripTest {
   Trip instance;
   String tripID, coords;
   int accepted;
   Date date;

   @Before
   public void setUp() throws Exception {
      tripID = "XY00000042";
      coords = "1234,5678";
      accepted = 0;
      date = Calendar.getInstance().getTime();
      instance = new Trip(tripID, accepted, coords, date);
   }

   @After
   public void tearDown() throws Exception {
      instance = null;
   }

   @Test
   public void testGetTripID() {
      String expResult = tripID;
      String result = instance.getTripID();
      assertEquals(expResult, result);
   }

   @Test
   public void testGetAccepted() {
      int expResult = accepted;
      int result = instance.getAccepted();
      assertEquals(expResult, result);
   }

   @Test
   public void testGetCoords() {
      String expResult = coords;
      String result = instance.getCoords();
      assertEquals(expResult, result);
   }

   @Test
   public void testGetDate() {
      Date expResult = date;
      Date result = instance.getDate();
      assertEquals(expResult, result);
   }

}
