/*
 * Projet Slidare
 * Sharing anywhere, anytime
 * 
 */
package controller;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Flavien Maillot <flavien.maillot@epitech.eu>
 */
public class ControllerTest
{
	
	public ControllerTest()
	{
	}
	
	@BeforeClass
	public static void setUpClass()
	{
	}
	
	@AfterClass
	public static void tearDownClass()
	{
	}
	
	@Before
	public void setUp()
	{
	}
	
	@After
	public void tearDown()
	{
	}

	/**
	 * Test of isEmpty method, of class Controller.
	 */
	@Test
	public void testIsEmpty()
	{
		System.out.println("isEmpty valide");
		Controller instance = new ControllerImpl();
		String s;
		boolean expResult;
		boolean result;
				
		s = "toto";
		expResult = false;
		result = instance.isEmpty(s);
		assertEquals(expResult, result);
	}
	
	@Test
	public void testIsNotEmpty()
	{
		System.out.println("isEmpty invalide");
		Controller instance = new ControllerImpl();
		String s;
		boolean expResult;
		boolean result;
		
		s = "";
		expResult = true;
		result = instance.isEmpty(s);
		assertEquals(expResult, result);
	}

	/**
	 * Test of isVoid method, of class Controller.
	 */
	@Test
	public void testIsVoid()
	{
		System.out.println("isVoid valide");
		Controller instance = new ControllerImpl();
		Object o;
		boolean expResult;
		boolean result;
		
		o = null;
		expResult = true;
		result = instance.isVoid(o);
		assertEquals(expResult, result);
	
	}
	
	@Test
	public void testIsNotVoid()
	{
		System.out.println("isVoid invalide");
		Controller instance = new ControllerImpl();
		Object o;
		boolean expResult;
		boolean result;
		
		o = this;
		expResult = false;
		result = instance.isVoid(o);
		assertEquals(expResult, result);
	}

	/**
	 * Test of equal method, of class Controller.
	 */
	@Test
	public void testEqual()
	{
		System.out.println("equal valide");
		Controller instance = new ControllerImpl();
		boolean expResult;
		boolean result;
		
		String o1 = "toto";
		String o2 = "toto";
		expResult = true;
		result = instance.equal(o1, o2);
		assertEquals(expResult, result);
	}

	@Test
	public void testNotEqual()
	{
		System.out.println("equal invalide");
		Controller instance = new ControllerImpl();
		boolean expResult;
		boolean result;
		
		String o1 = "toto";
		String o2 = "titi";
		expResult = false;
		result = instance.equal(o1, o2);
		assertEquals(expResult, result);
	}

	public class ControllerImpl extends Controller
	{
	}
	
}
