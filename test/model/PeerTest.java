package model;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * JUnit test for <code>Peer</code>
 * 
 * @author lasse
 *
 */
public class PeerTest {
   Peer instance;

   @Before
   public void setUp() throws Exception {
      instance = new Peer("192.168.1.102", 0);
   }

   @After
   public void tearDown() throws Exception {
      instance = null;
   }

   @Test
   public void testGetIp() {
      String expResult = "192.168.1.102";
      String result = instance.getIp();
      assertEquals(expResult, result);
   }

   @Test
   public void testGetStatus() {
      int expResult = 0;
      int result = instance.getStatus();
      assertEquals(expResult, result);
   }

   @Test
   public void testToString() {
      String expResult = "192.168.1.102,0";
      String result = instance.toString();
      assertEquals(expResult, result);
   }

}
