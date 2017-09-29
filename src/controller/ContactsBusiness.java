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
import model.Group;
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

	private GroupBusiness groupBusiness;
	
	public static int idinc = 0;

	public ContactsBusiness()
	{
		System.out.println("Contacts Business");
		this.fileManager = (FileManager) ServiceFactory.getFileManager();
		this.configManager = (ConfigManager) ServiceFactory.getConfigManager();
		this.packageManager = (PackageManager) ServiceFactory.getPackageManager();
		this.APIManager = (APIManager) ServiceFactory.getAPIManager();

//		this.APIManager.userContacts(this.configManager.getConfig().getToken());
//		System.out.println(this.APIManager.getLastResponse());
//		
//		this.APIManager.fetchGroups(this.configManager.getConfig().getToken());
//		System.out.println(this.APIManager.getLastResponse());
	}

	/**
	 * Constructor for compatible old julien API
	 *
	 * @deprecated to update
	 * @param _groupBusiness
	 */
	public ContactsBusiness(GroupBusiness _groupBusiness)
	{
		this();
		System.out.println("Contacts Business 2");
		this.groupBusiness = _groupBusiness;
	}

	/**
	 * Save the contact on the server or in a local file to sync later
	 * PAS GERE PAR L'API
	 *
	 * @param contactList List of contact to save
	 */
	public void saveContacts(Map<String, Contact> contactList)
	{
		//Pas de sauvegarde possible sur un contact avec l'API Ju sauf erreur de ma part
		
//		JSONArray contactListToSave = new JSONArray();
//		Contact contact;
//		for (Map.Entry<String, Contact> contactpair : contactList.entrySet())
//		{
//			JSONObject obj = new JSONObject();
//			contact = contactpair.getValue();
//			obj.put("id", Long.parseLong(contact.getId()));
//			obj.put("group", Long.parseLong(contact.getGroup()));
//			obj.put("firstname", contact.getFirstname());
//			obj.put("lastname", contact.getLastname());
//			obj.put("comment", contact.getComment());
//			contactListToSave.add(obj);
//		}
//
//		//Prepare request to server
//		String request = this.packageManager
//				.setJSONArray(contactListToSave)
//				.getJSONString();
//
//		//Send request to serveur
//		if (this.APIManager.saveContacts(request) == false)
//		{
//			this.fileManager.writeJson(this.configManager.getPathFor("contacts"), contactListToSave);
//		}
	}
	
	public void deleteContact(String id)
	{
		Contact contact = this.contacts.get(id);
		
		String email = contact.getComment();
	
		if (this.APIManager.removeContact(this.configManager.getConfig().getToken(), email) != false)
		{
			this.contacts.remove(id);
		}
		else
			throw new IllegalStateException(this.APIManager.getLastResponse());
	}

	/**
	 * Load the contacts from the server
	 */
	private void loadContacts()
	{
		this.contacts.clear();
		JSONArray jsonArray;

		//Send request to serveur
//        if (this.APIManager.loadContacts() != false) 
		if (this.APIManager.userContacts(this.configManager.getConfig().getToken()) != false)
		{
			String response = this.APIManager.getLastResponse();
			System.out.println(response);
			jsonArray = this.packageManager
					.setJSONObject(response)
					.getJSONArrayDefault("contacts");

			PackageManager child = this.packageManager.getChild();

			int length = jsonArray.size();
			for (int i = 0; i < length; i++)
			{
				child.setJSONObject((JSONObject) jsonArray.get(i));
				Contact contact = new Contact();
				this.idinc = this.idinc + 1;
				String id = String.valueOf(this.idinc);
				contact.setId(id);
				contact.setGroup("0");
				contact.setFirstname(child.getStringDefault("first_name", "Inconnu"));
				contact.setLastname(child.getStringDefault("last_name", "Inconnu"));
				contact.setComment(child.getStringDefault("email", "Inconnu"));
				this.contacts.put(contact.getId(), contact);
				System.out.println(contact.getFirstname());
			}
		}
		else
		{
			Object obj = this.fileManager.readJson(this.configManager.getPathFor("contacts"));
			jsonArray = (JSONArray) obj;

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
				System.out.println(contact.getFirstname());
			}
		}
		System.out.println("controller.ContactsBusiness.loadContacts()");
	}

	/**
	 * Search some contacts on the server by keyword.
	 *
	 * @param keyword keyword used to find some users on the server. Could be
	 * firstname, lastname, email...
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
		
		
//		List<Contact> list = new ArrayList<Contact>(filtered.values());
		System.out.println("controller.ContactsBusiness.findByLastname()" + lastname);
		Map<String, Contact> filtered = this.contacts.entrySet()
				.stream()
				.filter(a -> lastname.equals(a.getValue().getLastname()))
				.collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue()));
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

	public void addContact(Contact contact)
	{
		String request = this.packageManager
				.init()
				.addParam("email", contact.getComment())
				.getJSONString();
		
		
		if (this.APIManager.addContact(this.configManager.getConfig().getToken(), request) != false)
		{
			this.contacts.put(contact.getId(), contact);
		}
	}
	
	public List<Contact> findOneOnApiByEmail(String email)
	{
		JSONArray jsonArray;
		JSONObject jsonObject;
		List<Contact> list = new ArrayList<>();
		String request = this.packageManager
				.init()
				.addParam("email", email)
				.getJSONString();
		
		
		if (this.APIManager.addContact(this.configManager.getConfig().getToken(), request) != false)
		{
			String response = this.APIManager.getLastResponse();
			
			
			jsonObject = this.packageManager.setJSONObject(response).getJSONObjectDefault("contact");
			PackageManager child = this.packageManager.getChild().setJSONObject(jsonObject);
			Contact contact = new Contact();
			this.idinc = this.idinc + 1;
			String id = String.valueOf(this.idinc);
			contact.setId(id);
			contact.setGroup("0");
			contact.setFirstname(child.getStringDefault("first_name", "Inconnu"));
			contact.setLastname(child.getStringDefault("last_name", "Inconnu"));
			contact.setComment(child.getStringDefault("email", "Inconnu"));
//			this.contacts.put(contact.getId(), contact);
			list.add(contact);
			
			//Just because user didn't add yet only search email. FIX API
			this.APIManager.removeContact(this.configManager.getConfig().getToken(), email);
		}
		
		return (list);
	}
	
	/**
	 * Search directly in the contacts list
	 * 
	 * @param email
	 * @return 
	 */
	public Contact findOneByEmail(String email)
	{
		this.getContacts();
		Map<String, Contact> filtered = this.contacts.entrySet()
				.stream()
				.filter(a -> email.equals(a.getValue().getComment()))
				.collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue()));
		List<Contact> list = new ArrayList<Contact>(filtered.values());
		if (list.size() > 1)
		{
			throw new IllegalStateException("More than one result for find by email.");
		}
		else if (list.size() != 0)
        		return (list.get(0));
                else
                    return null;
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
	 * Connect group to contact for compatible old julien API
	 *
	 * @deprecated to update
	 */
	private void connectGroupToContacts()
	{
		PackageManager child = this.packageManager.getChild();
		JSONArray jsonArray;
		Group group;
		Contact contact_tmp;
		Map<String, Group> groupsList = this.groupBusiness.getGroups();
		for (Map.Entry<String, Group> grouppair : groupsList.entrySet())
		{
			JSONObject obj = new JSONObject();
			group = grouppair.getValue();
			jsonArray = child.setJSONObject(group.getUsers()).getJSONArray();
			int length = jsonArray.size();
			for (int i = 0; i < length; i++)
			{
//				if ((String) jsonArray.get(i) == "")
//					continue;
				System.out.println("Connect : " + jsonArray.get(i));
				contact_tmp = this.findOneByEmail((String) jsonArray.get(i));
                                if (contact_tmp == null)
                                    return;
				contact_tmp.setGroup(group.getId());
				this.contacts.put(contact_tmp.getId(), contact_tmp);
			}

//            obj.put("id", Long.parseLong(group.getId()));
//            obj.put("name", group.getName());
//            groupListToSave.add(obj);
		}
//		System.exit(1);
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
			System.out.println("controller.ContactsBusiness.getContacts()");
			loadContacts();
			connectGroupToContacts();
		}
		return (this.contacts);
	}
}
