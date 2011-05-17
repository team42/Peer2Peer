package model;

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * JUnit test for <code>CalcedTaxi</code>.
 * 
 * @author lasse
 *
 */
public class CalcedTaxiTest {
   CalcedTaxi instance;
   String taxiID, coords, company;
   int shortestPath;
   
   @Before
   public void setUp() throws Exception {
      taxiID = "X00001";
      coords = "1234,5678";
      company = "XY";
      shortestPath = 150;
      instance = new CalcedTaxi(taxiID, coords, company, 0);
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
      String expResult = coords;
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
   public void testSetAndGetShortestPath() {
      int expResult = shortestPath;
      instance.setShortestPath(shortestPath);
      int result = instance.getShortestPath();
      assertEquals(expResult, result);
   }
}
