package database;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * JUnit test for <code>TripOffersDAO</code>.
 * 
 * @author lasse
 *
 */
public class TripOffersDAOTest {
   TripOffersDAO instance;

   @Before
   public void setUp() throws Exception {
      instance = new TripOffersDAO();
   }

   @After
   public void tearDown() throws Exception {
      instance = null;
   }
   
   @Test
   public void testAddTrip() {
      boolean expResult = true;
      boolean result = instance.addTrip("1234,5678");
      System.out.println("testAddTrip result = Expected: " + result + " = " + expResult);
      assertEquals(expResult, result);
   }

   @Test
   public void testGetCustomerAndDeleteCustomer() {
      String[] expResult = new String[2];
      expResult[1] = "1234,5678";
      String[] result = instance.getCustomer();
      System.out.println("testGetCustomerAndDeleteCustomer result = Expected: " + result[1] + " = " + expResult[1]);
      assertEquals(expResult[1], result[1]);
   }
}
