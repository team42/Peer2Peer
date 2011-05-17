package database;

import static org.junit.Assert.*;

import java.util.ArrayList;

import model.Taxi;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * JUnit test for <code>TaxiDAO</code>.
 * 
 * @author lasse
 *
 */
public class TaxiDAOTest {
   TaxiDAO instance;
   String testTaxiID;
   
   @Before
   public void setUp() throws Exception {
      instance = new TaxiDAO();
      testTaxiID = "X00001";
   }

   @After
   public void tearDown() throws Exception {
      instance = null;
      testTaxiID = "";
   }

   @Test
   public void testUpdateTaxiPosition() {
      boolean expResult = true;
      boolean result = instance.updateTaxiPosition(testTaxiID, "9999,8888");
      System.out.println("testUpdateTaxiPosition result = Expected: " + result + " = " + expResult);
      assertEquals(expResult, result);
   }

   @Test
   public void testGetActiveTaxis() {
      ArrayList<Taxi> expResult = instance.getActiveTaxis();
      ArrayList<Taxi> result = expResult;
      System.out.println("testGetActiveTaxis result = Expected: " + result + " = " + expResult);
      assertEquals(expResult, result);
   }

}
