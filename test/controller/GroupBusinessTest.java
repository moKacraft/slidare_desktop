/*
 * Projet Slidare
 * Sharing anywhere, anytime
 * 
 */
package controller;

import java.util.HashMap;
import java.util.Map;
import model.Group;
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
public class GroupBusinessTest
{
	
	public GroupBusinessTest()
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
	 * Test of saveGroups method, of class GroupBusiness.
	 */
	@Test
	public void testSaveGroups()
	{
		System.out.println("saveGroups");
		Map<String, Group> groupsList = new HashMap<>();
		
		Group group1 = new Group();
		group1.setId("1");
		group1.setName("toto");
		
		groupsList.put("1", group1);
		GroupBusiness instance = new GroupBusiness();
		instance.configManager.setPathFor("groups", "./datas/groups_test.json");

		instance.saveGroups(groupsList);
	}

	/**
	 * Test of getGroups method, of class GroupBusiness.
	 */
	@Test
	public void testGetGroups()
	{
		System.out.println("getGroups");
		GroupBusiness instance = new GroupBusiness();
		instance.configManager.setPathFor("groups", "./datas/groups_test.json");

		Map<String, Group> expResult = new HashMap<>();
		
		Group group1 = new Group();
		group1.setId("1");
		group1.setName("toto");
		
		expResult.put("1", group1);
		
		Map<String, Group> result = instance.getGroups();
		
		assertEquals(expResult.get("1"), result.get("1"));
	}

	/**
	 * Test of getGroupList method, of class GroupBusiness.
	 */
	@Test
	public void testGetGroupList()
	{
//		System.out.println("getGroupList");
//		GroupBusiness instance = new GroupBusiness();
//		List<String> expResult = null;
//		List<String> result = instance.getGroupList();
//		assertEquals(expResult, result);
//		// TODO review the generated test code and remove the default call to fail.
//		fail("The test case is a prototype.");
	}
	
}
