/*
 * Projet Slidare
 * Sharing anywhere, anytime
 * 
 */
package controller;

import java.util.ArrayList;
import model.Contact;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import service.APIManager;

import service.ServiceFactory;
import service.ConfigManager;
import service.FileManager;
import service.PackageManager;

/**
 * Business for the contact entity
 * 
 * @author Flavien Maillot "flavien.maillot@epitech.eu"
 */
public class ContactsBusiness
{
	
	private Map<String, Contact> contacts = new HashMap<>();

	private FileManager fileManager;
	
	private ConfigManager configManager;
	
	private PackageManager packageManager;
	
	private APIManager APIManager;
	
	public ContactsBusiness()
	{
		this.fileManager = (FileManager) ServiceFactory.getFileManager();
		this.configManager = (ConfigManager) ServiceFactory.getConfigManager();
		this.packageManager = (PackageManager) ServiceFactory.getPackageManager();
		this.APIManager = (APIManager) ServiceFactory.getAPIManager();
	}

	/**
	 * Save the contact on the server or in a local file to sync later
	 * 
	 * @param contactList List of contact to save
	 */
	public void saveContacts(Map<String, Contact> contactList)
	{
		JSONArray contactListToSave = new JSONArray();
		Contact contact;
		for (Map.Entry<String, Contact> contactpair : contactList.entrySet())
		{
			JSONObject obj = new JSONObject();
			contact = contactpair.getValue();
			obj.put("id", Long.parseLong(contact.getId()));
			obj.put("group", Long.parseLong(contact.getGroup()));
			obj.put("firstname", contact.getFirstname());
			obj.put("lastname", contact.getLastname());
			obj.put("comment", contact.getComment());
			contactListToSave.add(obj);
		}
		
		//Prepare request to server
		String request = this.packageManager
				.setJSONArray(contactListToSave)
				.getJSONString();
		
		//Send request to serveur
		if (this.APIManager.saveContacts(request) == false)
		{
			this.fileManager.writeJson(this.configManager.getPathFor("contacts"), contactListToSave);
		}
	}

	/**
	 * Load the contacts from the server
	 */
	private void loadContacts()
	{
		this.contacts.clear();
		JSONArray jsonArray;
		
		//Send request to serveur
		if (this.APIManager.loadContacts() != false)
		{
			String response = this.APIManager.getLastResponse();
			jsonArray = this.packageManager
					.setJSONObject(response)
					.getJSONArray();
		}
		else
		{
			Object obj = this.fileManager.readJson(this.configManager.getPathFor("contacts"));

			jsonArray = (JSONArray) obj;
		}
		
		int length = jsonArray.size();
		for (int i = 0; i < length; i++)
		{
			JSONObject jsonObject = (JSONObject) jsonArray.get(i);
			Contact contact = new Contact();

			String id = Long.toString((long) jsonObject.get("id"));
			contact.setId(id);
			contact.setGroup(Long.toString((long) jsonObject.get("group")));
			contact.setFirstname((String) jsonObject.get("firstname"));
			contact.setLastname((String) jsonObject.get("lastname"));
			contact.setComment((String) jsonObject.get("comment"));
			this.contacts.put(contact.getId(), contact);
		}
	}
	
	/**
	 * Search some contacts on the server by keyword.
	 * 
	 * @param keyword keyword used to find some users on the server. Could be firstname, lastname, email...
	 */
	public void searchContacts(String keyword)
	{
		//Prepare request to server
		String request = this.packageManager
				.init()
				.addParam("keyword", keyword)
				.getJSONString();
		
		//Send request to serveur
		if (this.APIManager.searchContacts(request) == false)
		{
		}
	}
	
	/**
	 * Search a contact in a the list by the name
	 * 
	 * @param lastname Name expected
	 * @return List of the contact found
	 */
	public List<Contact> findByLastname(String lastname)
	{
		this.getContacts();
		System.out.println("controller.ContactsBusiness.findByLastname()"+lastname);
		Map<String, Contact> filtered = this.contacts.entrySet()
                        .stream()
                        .filter(a->
								lastname.equals(a.getValue().getLastname()) //&& 
//								"Flavien".equals(a.getValue().getFirstname()) 
						)
                        .collect(Collectors.toMap(e->e.getKey(), e->e.getValue()));
//		Map<String, Contact> filtered = this.contacts.entrySet()
//                        .stream()
//                        .filter(a->
//								"Maillot".equals(a.getValue().getLastname()) && 
//								"Flavien".equals(a.getValue().getFirstname()) 
//						)
//                        .collect(Collectors.toMap(e->e.getKey(), e->e.getValue()));
//		for (Map.Entry<String, Contact> contactpair : filtered.entrySet())
//		{
//			String id = contactpair.getValue().getId();
//			System.out.println("id = " + id);
//		}
		List<Contact> list = new ArrayList<Contact>(filtered.values());

		return (list);
	}
	
	/**
	 * Get all contact in a list
	 * 
	 * @return list of contacts
	 */
	public List<Contact> findAll()
	{
		List<Contact> list = new ArrayList<Contact>(this.contacts.values());
		return (list);
	}

	/**
	 * Get all contact in a map
	 * 
	 * @return list of contacts
	 */
	public Map<String, Contact> getContacts()
	{
		if (this.contacts.isEmpty())
		{
			loadContacts();
		}
		return (this.contacts);
	}
	
	
}
