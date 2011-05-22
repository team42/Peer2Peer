package algorithm;

import static org.junit.Assert.*;

import java.util.ArrayList;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class AlgorithmTest {
   Algorithm instance;

   @Before
   public void setUp() throws Exception {
      instance = new Algorithm();
   }

   @After
   public void tearDown() throws Exception {
      instance = null;
   }

   @Test
   public void testRoute() {
      ArrayList<Integer> expResult = instance.Route("1234,5678", "1000,2000");
      ArrayList<Integer> result = instance.Route("1234,5678", "1000,2000");
      assertEquals(expResult, result);
   }

   @Test
   public void testRouteLength() {
      double expResult = instance.RouteLength("1234,5678", "1000,2000");
      double result = instance.RouteLength("1234,5678", "1000,2000");
      assertEquals(expResult, result, 0);
   }

   @Test
   public void testFindClosestPoint() {
      int expResult = instance.findClosestPoint("1234,5678");
      int result = instance.findClosestPoint("1234,5678");
      assertEquals(expResult, result);
   }

}
