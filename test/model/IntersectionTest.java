package model;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class IntersectionTest {
   Intersection instance;

   @Before
   public void setUp() throws Exception {
      instance = new Intersection(42, 1, 2, 3, 4, 5, 6, 7, 8);
   }

   @After
   public void tearDown() throws Exception {
      instance = null;
   }
   
   @Test
   public void testSetAndGetParentID() {
      int parentID = 1234;
      int expResult = parentID;
      instance.setParentID(parentID);
      int result = instance.getParentID();
      assertEquals(expResult, result);
   }

   @Test
   public void testSetAndGetF() {
      double f = 1234;
      double expResult = f;
      instance.setF(f);
      double result = instance.getF();
      assertEquals(expResult, result, 0);
   }

   @Test
   public void testSetAndGetG() {
      double g = 1234;
      double expResult = g;
      instance.setG(g);
      double result = instance.getG();
      assertEquals(expResult, result, 0);
   }

   @Test
   public void testSetAndSetH() {
      double h = 1234;
      double expResult = h;
      instance.setH(h);
      double result = instance.getH();
      assertEquals(expResult, result, 0);
   }


   @Test
   public void testSetAndGetTempG() {
      double temp = 1234;
      double expResult = temp;
      instance.setTempG(temp);
      double result = instance.getTempG();
      assertEquals(expResult, result, 0);
   }

   @Test
   public void testSetAndGetLinks() {
      int links = 1234;
      int expResult = links;
      instance.setLinks(links);
      int result = instance.getLinks();
      assertEquals(expResult, result, 0);
   }

   @Test
   public void testGetID() {
      int expResult = 42;
      int result = instance.getID();
      assertEquals(expResult, result);
   }

   @Test
   public void testGetXCoord() {
      int expResult = 1;
      int result = instance.getXCoord();
      assertEquals(expResult, result);
   }

   @Test
   public void testGetYCoord() {
      int expResult = 2;
      int result = instance.getYCoord();
      assertEquals(expResult, result);
   }

   @Test
   public void testGetN1() {
      int expResult = 4;
      int result = instance.getN1();
      assertEquals(expResult, result);
   }

   @Test
   public void testGetN2() {
      int expResult = 5;
      int result = instance.getN2();
      assertEquals(expResult, result);
   }

   @Test
   public void testGetN3() {
      int expResult = 6;
      int result = instance.getN3();
      assertEquals(expResult, result);
   }

   @Test
   public void testGetN4() {
      int expResult = 7;
      int result = instance.getN4();
      assertEquals(expResult, result);
   }

   @Test
   public void testGetN5() {
      int expResult = 8;
      int result = instance.getN5();
      assertEquals(expResult, result);
   }

   @Test
   public void testGetNn() {
      int expResult = 8;
      int result = instance.getNn(5);
      assertEquals(expResult, result);
   }

}
