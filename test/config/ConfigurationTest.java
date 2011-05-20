package config;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;

import model.Peer;

import org.junit.Test;


/**
 * <code>ConfigurationTest</code> will test the <code>Configuration</code> class
 *
 * @see Configuration
 *
 * @author Lasse
 */
public class ConfigurationTest {

   /**
    * Test of getConfiguration method, of class Configuration.
    *
    * Call getConfiguration() to load Configuration object into result and
    * expected result.
    *
    * assertEquals the result from getConfiguration() with the expected result.
    */
   @Test
   public void testGetConfiguration() {
      Configuration expResult = Configuration.getConfiguration();
      Configuration result = Configuration.getConfiguration();
      System.out.println("testGetConfiguration result = Expected: " + result + " = " + expResult);
      assertEquals(expResult, result);
   }

   /**
    * Test of clone method, of class Configuration.
    * We want this to throw an exception so we test for that.
    * See http://junit.sourceforge.net/doc/faq/faq.htm#tests_7
    *
    * @throws Exception clone() has CloneNotSupportedException
    */
   @SuppressWarnings("unused")
   @Test(expected=CloneNotSupportedException.class)
   public void testClone() throws Exception {
      Configuration instance = Configuration.getConfiguration();
      Object o = instance.clone();
   }

   /**
    * Test of setPeers() and getPeers() method, of class Configuration.
    * @throws IOException 
    */
   @Test
   public void testGetAndSetPeers() throws IOException {
      ArrayList<Peer> peers = new ArrayList<Peer>();
      peers.add(new Peer("192.168.1.101", 1));
      peers.add(new Peer("192.168.1.10", 0));      
      Configuration instance = Configuration.getConfiguration();
      instance.setPeers(peers);
      ArrayList<Peer> expResult = peers;
      ArrayList<Peer> result = instance.getPeers();
      assertEquals(expResult, result);
      System.out.println("testGetAndSetPeers result = Expected: " + result + " = " + expResult);
   }
}
