/*
 * Projet Slidare
 * Sharing anywhere, anytime
 * 
 */
package controller;

import java.lang.reflect.Method;
import javafx.event.ActionEvent;
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
public class ConnectControllerTest
{
	
	public ConnectControllerTest()
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
	 * Test of doConnexion method, of class ConnectController.
	 */
	@Test
	public void testCheckIdentification() throws Exception
	{
		System.out.println("checkIdentification valide");
		ConnectController instance = new ConnectController();
		Method method = instance.getClass().getDeclaredMethod("checkIdentification", String.class, String.class);
		method.setAccessible(true);
		String user = "admin";
		String pass = "admin";
		boolean expResult;
		boolean result;
		
		expResult = true;
		result = (boolean) method.invoke(instance, user, pass);
		assertEquals(expResult, result);
	}
	
	@Test
	public void testCheckBadIdentification() throws Exception
	{
		System.out.println("checkIdentification invalide");
		ConnectController instance = new ConnectController();
		Method method = instance.getClass().getDeclaredMethod("checkIdentification", String.class, String.class);
		method.setAccessible(true);
		String user = "flavien.maillot@epitech.eu";
		String pass = "test";
		boolean expResult;
		boolean result;
		
		expResult = false;
		result = (boolean) method.invoke(instance, user, pass);
		assertEquals(expResult, result);
	}

	/**
	 * Test of doConnexion method, of class ConnectController.
	 */
	@Test(expected=NullPointerException.class)
	public void testDoConnexionNullPointer() throws Exception
	{
		ActionEvent event = null;
		ConnectController instance = new ConnectController();
		instance.doConnexion(event);
	}
}
