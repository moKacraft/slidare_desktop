/*
 * Projet Slidare
 * Sharing anywhere, anytime
 * 
 */
package service;

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
public class ConfigManagerTest
{
	
	public ConfigManagerTest()
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
	 * Test of loadBase method, of class ConfigManager.
	 */
	@Test
	public void testLoadBase()
	{
		System.out.println("loadBase");
		ConfigManager instance = new ConfigManager();
		instance.loadBase();
		// TODO review the generated test code and remove the default call to fail.
		fail("The test case is a prototype.");
	}

	/**
	 * Test of load method, of class ConfigManager.
	 */
	@Test
	public void testLoad()
	{
		System.out.println("load");
		String filepath = "";
		ConfigManager instance = new ConfigManager();
		instance.load(filepath);
		// TODO review the generated test code and remove the default call to fail.
		fail("The test case is a prototype.");
	}

	/**
	 * Test of setPathFor method, of class ConfigManager.
	 */
	@Test
	public void testSetPathFor()
	{
		System.out.println("setPathFor");
		String module = "";
		String filepath = "";
		ConfigManager instance = new ConfigManager();
		instance.setPathFor(module, filepath);
		// TODO review the generated test code and remove the default call to fail.
		fail("The test case is a prototype.");
	}

	/**
	 * Test of getPathFor method, of class ConfigManager.
	 */
	@Test
	public void testGetPathFor()
	{
		System.out.println("getPathFor");
		String module = "";
		ConfigManager instance = new ConfigManager();
		String expResult = "";
		String result = instance.getPathFor(module);
		assertEquals(expResult, result);
		// TODO review the generated test code and remove the default call to fail.
		fail("The test case is a prototype.");
	}

	/**
	 * Test of setLang method, of class ConfigManager.
	 */
	@Test
	public void testSetLang()
	{
		System.out.println("setLang");
		String _lang = "";
		ConfigManager instance = new ConfigManager();
		instance.setLang(_lang);
		// TODO review the generated test code and remove the default call to fail.
		fail("The test case is a prototype.");
	}

	/**
	 * Test of getLang method, of class ConfigManager.
	 */
	@Test
	public void testGetLang()
	{
		System.out.println("getLang");
		ConfigManager instance = new ConfigManager();
		String expResult = "";
		String result = instance.getLang();
		assertEquals(expResult, result);
		// TODO review the generated test code and remove the default call to fail.
		fail("The test case is a prototype.");
	}

	/**
	 * Test of autoconnect method, of class ConfigManager.
	 */
	@Test
	public void testAutoconnect()
	{
		System.out.println("autoconnect");
		ConfigManager instance = new ConfigManager();
		boolean expResult = false;
		boolean result = instance.autoconnect();
		assertEquals(expResult, result);
		// TODO review the generated test code and remove the default call to fail.
		fail("The test case is a prototype.");
	}

	/**
	 * Test of getUsername method, of class ConfigManager.
	 */
	@Test
	public void testGetUsername()
	{
		System.out.println("getUsername");
		ConfigManager instance = new ConfigManager();
		String expResult = "";
		String result = instance.getUsername();
		assertEquals(expResult, result);
		// TODO review the generated test code and remove the default call to fail.
		fail("The test case is a prototype.");
	}

	/**
	 * Test of getPassword method, of class ConfigManager.
	 */
	@Test
	public void testGetPassword()
	{
		System.out.println("getPassword");
		ConfigManager instance = new ConfigManager();
		String expResult = "";
		String result = instance.getPassword();
		assertEquals(expResult, result);
		// TODO review the generated test code and remove the default call to fail.
		fail("The test case is a prototype.");
	}
	
}
