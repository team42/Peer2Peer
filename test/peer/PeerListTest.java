package peer;

import static org.junit.Assert.*;
import org.junit.Test;

public class PeerListTest {

   @Test
   public void testPeerList() {
      fail("Not yet implemented"); // TODO
   }

   @Test
   public void testOpenFile() {
      System.out.println("Start of test: PeerList.openFile()");
      PeerList instance = new PeerList();
      boolean expResult = true;
      boolean result = instance.openFile(0);
      instance.closeInputFile();
      System.out.println("    Result = Expected: " + result + " = " + expResult);
      assertEquals(expResult, result);
      
      boolean result1 = instance.openFile(1);
      System.out.println("    Result = Expected: " + result1 + " = " + expResult);
      assertEquals(expResult, result1);
      
      System.out.println("End test: PeerList.openFile()\n");
   }

   @Test
   public void testReadPeerList() {
      fail("Not yet implemented"); // TODO
   }

   @Test
   public void testWritePeerList() {
      fail("Not yet implemented"); // TODO
   }

   @Test
   public void testCloseInputFile() {
      fail("Not yet implemented"); // TODO
   }

   @Test
   public void testCloseOutputFile() {
      fail("Not yet implemented"); // TODO
   }

}
