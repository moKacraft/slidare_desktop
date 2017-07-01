/*
 * Projet Slidare
 * Sharing anywhere, anytime
 * 
 */
package controller;

import model.Account;
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
public class AccountBusinessTest
{
	
	public AccountBusinessTest()
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
	 * Test of saveAccount method, of class AccountBusiness.
	 */
	@Test
	public void testSaveAccount()
	{
		System.out.println("saveAccount");
		Account account = new Account();
		account.setId("20");
		AccountBusiness instance = new AccountBusiness();
		instance.configManager.setPathFor("account", "./datas/account_test.json");
		instance.saveAccount(account);
	}

	/**
	 * Test of getAccount method, of class AccountBusiness.
	 */
	@Test
	public void testGetAccount()
	{
		System.out.println("getAccount");
		AccountBusiness instance = new AccountBusiness();
		Account account = new Account();
		account.setId("20");
		instance.configManager.setPathFor("account", "./datas/account_test.json");
		Account expResult = account;
		Account result = instance.getAccount();
		
		assertEquals(expResult, result);
	}
	
}
