package model;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * JUnit test for <code>Taxi</code>.
 * 
 * @author lasse
 *
 */
public class TaxiTest {
   Taxi instance;
   String taxiID, taxiCoord, company;

   @Before
   public void setUp() throws Exception {
      taxiID = "X00001";
      taxiCoord = "1234,5678";
      instance = new Taxi(taxiID, taxiCoord);
   }

   @After
   public void tearDown() throws Exception {
      instance = null;
   }

   @Test
   public void testGetTaxiID() {
      String expResult = taxiID;
      String result = instance.getTaxiID();
      assertEquals(expResult, result);
   }

   @Test
   public void testGetTaxiCoord() {
      String expResult = taxiCoord;
      String result = instance.getTaxiCoord();
      assertEquals(expResult, result);
   }

   @Test
   public void testGetCompanyIP() {
      String expResult = company;
      String result = instance.getCompanyIP();
      assertEquals(expResult, result);
   }

   @Test
   public void testSetAndGetHeuristic() {
      int expResult = 1;
      instance.setHeuristic(1);
      int result = instance.getHeuristic();
      assertEquals(expResult, result);
   }
}
