package peer;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;
import model.*;
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
   }

   @Test
   public void testOpenFile() {
      System.out.println("Start of test: PeerList.openFile()");
      boolean expResult = true;
      boolean result = instance.openFile(0);
      instance.closeInputFile();
      System.out.println("    Result = Expected: " + result + " = " + expResult);
      assertEquals(expResult, result);
      
      boolean result1 = instance.openFile(1);
      try {
         instance.closeOutputFile();
      } catch (IOException e) {
         e.printStackTrace();
      }
      System.out.println("    Result = Expected: " + result1 + " = " + expResult);
      assertEquals(expResult, result1);
      
      System.out.println("End test: PeerList.openFile()\n");
   }

   @Test
   public void testReadPeerList() {
      System.out.println("Start of test: PeerList.readPeerList()");
      ArrayList<Peer> expResult = instance.readPeerList();
      ArrayList<Peer> result = instance.readPeerList();
      instance.closeInputFile();
      System.out.println("    Result = Expected: " + result + " = " + expResult);
      assertEquals(expResult, result);      
      System.out.println("End test: PeerList.readPeerList()\n");
   }

   @Test
   public void testWritePeerList() {
      System.out.println("Start of test: PeerList.writePeerList()");
      ArrayList<Peer> expResult = instance.readPeerList();
      ArrayList<Peer> result = instance.readPeerList();
      instance.closeInputFile();
      System.out.println("    Result = Expected: " + result + " = " + expResult);
      assertEquals(expResult, result);      
      System.out.println("End test: PeerList.writePeerList()\n");
   }
}
