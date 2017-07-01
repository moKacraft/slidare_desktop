/*
 * Projet Slidare
 * Sharing anywhere, anytime
 * 
 */
package controller;

import java.util.HashMap;
import java.util.Map;
import model.Contact;
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
public class ContactsBusinessTest
{
	
	public ContactsBusinessTest()
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
	 * Test of saveContacts method, of class ContactsBusiness.
	 */
	@Test
	public void testSaveContacts()
	{
		System.out.println("saveContacts");
		
		Map<String, Contact> contactList = new HashMap<>();
		Contact contact1 = new Contact();
		Contact contact2 = new Contact();
		contact1.setId("1");
		contact1.setFirstname("prenom1");
		contact1.setLastname("nom1");
		contact2.setId("2");
		contact2.setFirstname("prenom2");
		contact2.setLastname("nom2");
		
		contactList.put("1", contact1);
		contactList.put("2", contact2);
		
		ContactsBusiness instance = new ContactsBusiness();
		instance.configManager.setPathFor("contacts", "./datas/contacts_test.json");
		instance.saveContacts(contactList);
	}

	/**
	 * Test of findByLastname method, of class ContactsBusiness.
	 */
	@Test
	public void testFindByLastname()
	{
//		System.out.println("findByLastname");
//		String lastname = "";
//		ContactsBusiness instance = new ContactsBusiness();
//		List<Contact> expResult = null;
//		List<Contact> result = instance.findByLastname(lastname);
//		assertEquals(expResult, result);
//		// TODO review the generated test code and remove the default call to fail.
//		fail("The test case is a prototype.");
	}

	/**
	 * Test of findAll method, of class ContactsBusiness.
	 */
	@Test
	public void testFindAll()
	{
//		System.out.println("findAll");
//		ContactsBusiness instance = new ContactsBusiness();
//		List<Contact> expResult = null;
//		List<Contact> result = instance.findAll();
//		assertEquals(expResult, result);
//		// TODO review the generated test code and remove the default call to fail.
//		fail("The test case is a prototype.");
	}

	/**
	 * Test of getContacts method, of class ContactsBusiness.
	 */
	@Test
	public void testGetContacts()
	{
		System.out.println("getContacts");
		ContactsBusiness instance = new ContactsBusiness();
		
		Map<String, Contact> expResult = new HashMap<>();
		Contact contact1 = new Contact();
		Contact contact2 = new Contact();
		contact1.setId("1");
		contact1.setFirstname("prenom1");
		contact1.setLastname("nom1");
		contact2.setId("2");
		contact2.setFirstname("prenom2");
		contact2.setLastname("nom2");
		
		expResult.put("1", contact1);
		expResult.put("2", contact2);
		
		instance.configManager.setPathFor("contacts", "./datas/contacts_test.json");
		
		Map<String, Contact> result = instance.getContacts();
		
		assertEquals(expResult.get("1"), result.get("1"));
		assertEquals(expResult.get("2"), result.get("2"));
	}
	
}
