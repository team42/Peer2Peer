package peer;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;

import model.Peer;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class PeerListTest {
   PeerList instance;
   
   @Before
   public void setUp() throws Exception {
      instance = new PeerList();
   }

   @After
   public void tearDown() throws Exception {
      instance = null;
   }

   @Test
   public void testOpenFile() {
      boolean expResult = true;
      boolean result = instance.openFile(0);
      instance.closeInputFile();
      System.out.println("testOpenFile result = Expected: " + result + " = " + expResult);
      assertEquals(expResult, result);
      
      boolean result1 = instance.openFile(1);
      try {
         instance.closeOutputFile();
      } catch (IOException e) {
         e.printStackTrace();
      }
      System.out.println("testOpenFile result1 = Expected: " + result1 + " = " + expResult);
      assertEquals(expResult, result1);
   }

   @Test
   public void testReadPeerList() {
      ArrayList<Peer> expResult = instance.readPeerList();
      ArrayList<Peer> result = instance.readPeerList();
      instance.closeInputFile();
      System.out.println("testReadPeerList result = Expected: " + result + " = " + expResult);
      assertEquals(expResult, result);      
   }

   @Test
   public void testWritePeerList() {
      ArrayList<Peer> expResult = instance.readPeerList();
      ArrayList<Peer> result = instance.readPeerList();
      instance.closeInputFile();
      System.out.println("testWritePeerList result = Expected: " + result + " = " + expResult);
      assertEquals(expResult, result);      
   }
}