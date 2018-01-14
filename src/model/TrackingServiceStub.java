/*
 * Projet Slidare
 * Sharing anywhere, anytime
 * 
 */
package model;

import controller.AccountBusiness;
import controller.ContactsBusiness;
import controller.GroupBusiness;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.collections.MapChangeListener;
import javafx.collections.MapChangeListener.Change;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

public class TrackingServiceStub implements TrackingService
{

	GroupBusiness groupBusiness = new GroupBusiness();

	ContactsBusiness contactsctrl = new ContactsBusiness(groupBusiness);

	AccountBusiness accountBusiness = new AccountBusiness();

	Account account;
        public static TrackingService TSS = null;


	
	/**
	 * Group list associate with contactid<br /><br />
	 * Array(GroupeName => array(ContactId))<br />
	 * Exemple : {Amis=[], Famille=[1, 2, 4], Non classé=[], Travail=[3]}
	 */
	final ObservableMap<String, ObservableList<String>> projectsMap;
	{
		final Map<String, ObservableList<String>> map = new TreeMap<String, ObservableList<String>>();
		projectsMap = FXCollections.observableMap(map);
                TSS  = this;

//		for (String s : contactsctrl.getGroupList())
//		{
//			projectsMap.put(s, FXCollections.<String>observableArrayList());
//		}
	}

	/**
	 * Lorsqu'un contact est ajouté ou retiré de la liste
	 */
	final MapChangeListener<String, Group> groupsMapChangeListener = new MapChangeListener<String, Group>()
	{
		@Override
		public void onChanged(Change<? extends String, ? extends Group> change)
		{
			System.out.println("groupsMapChangeListener .onChanged()");
			char status = 0;
			Group group = null;
			Group grouprm = null;
			if (change.wasAdded())
			{
//				System.out.println(".onChanged() add");
				status |= 1;
				group = change.getValueAdded();
//				projectsMap.get(contact.getGroup()).add(contact.getId());
			}
			if (change.wasRemoved())
			{
//				System.out.println(".onChanged() rem");
				status |= 2;
				grouprm = change.getValueRemoved();
//				System.out.println(""+grouprm.getName());
//				projectsMap.get(contact.getGroup()).remove(contact.getId());
			}
			switch (status)
			{
				case 1:
					System.out.println(".onChanged() add1 " + group.getName());
					if (group != null)
					{
						projectsMap.put(group.getName(), FXCollections.<String>observableArrayList());
					}
					break;
				case 2:
					System.out.println(".onChanged() rem1");
					break;
				default:
					System.out.println(".onChanged() permutted");
					if (group != null && grouprm != null)
					{
						ObservableList<String> contactForNewGroup = projectsMap.remove(grouprm.getName());
						Contact contact;
						for (String contactId : contactForNewGroup)
						{
							contact = getContact(contactId);
							if (contact.getGroup() == null ? group.getId() != null : !contact.getGroup().equals(group.getId()))
							{
								contact.setGroup(contactId);
							}
						}
						projectsMap.put(group.getName(), contactForNewGroup);
					}
					break;
			}
		}
	};

	final AtomicInteger groupCounter = new AtomicInteger(0);

	/**
	 * Group list<br /><br />
	 * Array(GroupId, Group)<br />
	 * Exemple : {1=issuetrackinglite.model.Group,
	 * 2=issuetrackinglite.model.Group}
	 */
	final ObservableMap<String, Group> groupsMap;
	{
		final Map<String, Group> map = new TreeMap<String, Group>();
		groupsMap = FXCollections.observableMap(map);
		groupsMap.addListener(groupsMapChangeListener);

		Map<String, Group> groups = groupBusiness.getGroups();
		Group group;
		for (Map.Entry<String, Group> grouppair : groups.entrySet())
		{
			group = grouppair.getValue();
			groupCounter.set(Integer.parseInt(group.getId()));
			projectsMap.put(group.getName(), FXCollections.<String>observableArrayList());
			groupsMap.put(group.getId(), group);
//			this.addGroupFor(group.get(), contact);
		}
	}

	/**
	 * Lorsqu'un group est ajouté ou retiré de la liste
	 */
	final MapChangeListener<String, ObservableList<String>> projectsMapChangeListener = new MapChangeListener<String, ObservableList<String>>()
	{
		@Override
		public void onChanged(Change<? extends String, ? extends ObservableList<String>> change)
		{
			System.out.println("projectsMapChangeListener .onChanged()");
			if (change.wasAdded())
			{
				projectNames.add(change.getKey());
			}
			if (change.wasRemoved())
			{
				projectNames.remove(change.getKey());
			}
		}
	};

	/**
	 * Group list<br /><br />
	 * Array(GroupeName)<br />
	 * Exemple : [Amis, Famille, Non classé, Travail]
	 */
	final ObservableList<String> projectNames;
	{
		projectNames = FXCollections.<String>observableArrayList();
		projectNames.addAll(projectsMap.keySet());
		projectsMap.addListener(projectsMapChangeListener);
	}

	final AtomicInteger contactCounter = new AtomicInteger(0);

	/**
	 * Lorsqu'un contact est ajouté ou retiré de la liste
	 */
	static List<Contact> contacts = new ArrayList<Contact>();

	final MapChangeListener<String, Contact> contactsMapChangeListener = new MapChangeListener<String, Contact>()
	{
		@Override
		public void onChanged(Change<? extends String, ? extends Contact> change)
		{
			//System.out.println("azeaze");
//			System.out.println("contactsMapChangeListener .onChanged()");
			if (change.wasAdded())
			{

				final Contact contact = change.getValueAdded();
				contacts.add(contact);
				//Pour le groupe du contact on récupère le nom du groupe qui servira a ajouter le contact dans la groupe physique
				String groupName = getGroup(contact.getGroup()).getName();
				
				List<String> groupsName = contact.getGroups();
				
				//On parcours la liste des groupes
				if (!groupsName.isEmpty())
				{
					int size = groupsName.size();
					for (String groupid : groupsName) 
					{
						if (size > 1 && groupid == "0")
							continue;
						groupName = getGroup(groupid).getName();
						projectsMap.get(groupName).add(contact.getId());
					}
				}

				//Ajoute le contact qu'a une seule liste
//				projectsMap.get(groupName).add(contact.getId());
			}
			if (change.wasRemoved())
			{
				final Contact contact = change.getValueRemoved();
				String groupName = getGroup(contact.getGroup()).getName();
				projectsMap.get(groupName).remove(contact.getId());
			}
		}
	};

	/**
	 * Contact list<br /><br />
	 * Array(ContactId, Contact)<br />
	 * Exemple : {1=issuetrackinglite.model.Contact@3138cc8e,
	 * 2=issuetrackinglite.model.Contact@915673f}
	 */
	final ObservableMap<String, Contact> contactsMap;

	
	{
		final Map<String, Contact> map = new TreeMap<String, Contact>();
		contactsMap = FXCollections.observableMap(map);
		contactsMap.addListener(contactsMapChangeListener);

		Map<String, Contact> contacts = contactsctrl.getContacts();
		Contact contact;
		for (Map.Entry<String, Contact> contactpair : contacts.entrySet())
		{
			contact = contactpair.getValue();
			contactCounter.set(Integer.parseInt(contact.getId()));
			String groupName = getGroup(contact.getGroup()).getName();
			this.addContactFor(groupName, contact);
		}

		Contact result;
//		result = contactsMap.entrySet().stream().filter(map -> "Flavien".equals(map.getValue().getFirstname()))
//.map(map -> map.getValue()).collect(Collectors.joining());

		System.out.println("model.TrackingServiceStub.methodName()");

	}

	public TrackingServiceStub()
	{
		this.account = accountBusiness.getAccount();
	}

	@Override
	public ContactsBusiness getContactsBusiness()
	{
		return (this.contactsctrl);
	}
        
	
//	private static <T> List<T> newList(T... items)
//	{
//		return Arrays.asList(items);
//	}
	@Override
	public Contact addContactFor(String projectName, Contact contact)
	{
		this.contactsctrl.addContact(contact);
		assert projectNames.contains(projectName);
		assert contactsMap.containsKey(contact.getId()) == false;
		assert projectsMap.get(projectName).contains(contact.getId()) == false;

//		if (contact.getGroup().trim().isEmpty())
//		{
			String grpid = groupsMap.entrySet()
					.stream()
					.filter(entry -> projectName.equals(entry.getValue().getName()))
					.map(entry -> entry.getValue().getId())
					.collect(Collectors.joining());
			contact.setGroup(grpid);
//		}
		contactsMap.put(contact.getId(), contact);
		return (contact);
	}

	@Override
	public Contact createContactFor(String projectName)
	{
		assert projectNames.contains(projectName);
		final Contact contact = new Contact();
		contact.setGroup(projectName);
		contact.setId("" + contactCounter.incrementAndGet());
		assert contactsMap.containsKey(contact.getId()) == false;
		assert projectsMap.get(projectName).contains(contact.getId()) == false;
		contactsMap.put(contact.getId(), contact);
		System.out.println(contactsMap.values());
		return contact;
	}

	@Override
	public void deleteContact(String issueId)
	{
		assert contactsMap.containsKey(issueId);
		contactsctrl.deleteContact(issueId);
		contactsMap.remove(issueId);
//        contactsctrl.saveContacts(contactsMap);
	}

	@Override
	public ObservableList<String> getProjectNames()
	{
		return projectNames;
	}

	@Override
	public ObservableList<String> getAccountMenu()
	{
		ObservableList<String> accountmenu = FXCollections.<String>observableArrayList();
		accountmenu.add("Emploi et Scolarité");
		accountmenu.add("Informations générales et \rcoordonnées");
		return accountmenu;
	}

	@Override
	public Account getAccount()
	{
		return account;
	}

	@Override
	public ObservableList<String> getContactIds(String projectName)
	{
		return projectsMap.get(projectName);
	}

	@Override
	public Contact getContact(String issueId)
	{
		return contactsMap.get(issueId);
	}

	@Override
	public ObservableMap<String, Contact> getContacts()
	{
		return contactsMap;
	}

	@Override
	public Group createGroup(String groupName)
	{
//		assert projectNames.contains(projectName);
		final Group group = new Group();
		group.setName(groupName);
		group.setId("" + groupCounter.incrementAndGet());
		assert groupsMap.containsKey(group.getId()) == false;
		assert projectsMap.containsKey(group.getName()) == false;
		groupsMap.put(group.getId(), group);
		return group;
	}

	@Override
	public Group getGroup(String groupId)
	{
		return groupsMap.get(groupId);
	}

	@Override
	public ObservableMap<String, Group> getGroups()
	{
//		System.out.println("---->"+groupsMap);
		return groupsMap;
	}

	@Override
	public void deleteGroup(String groupId)
	{
		assert groupsMap.containsKey(groupId);
		groupsMap.remove(groupId);
		groupBusiness.deleteGroup(groupId);
//		groupBusiness.saveGroups(groupsMap);
	}

	@Override
	public void saveContact(String issueId, iContact.ContactStatus status,
			String synopsis, String description)
	{
		Contact contact = getContact(issueId);
		contact.setComment(description);
		contact.setFirstname(synopsis);
		contact.setStatus(status);
	}

	@Override
	public void saveContact(String issueId, iContact contactEdited)
	{
		Contact contact = getContact(issueId);
		contact.setComment(contactEdited.getComment());
		contact.setLastname(contactEdited.getLastname());
		contact.setFirstname(contactEdited.getFirstname());
		contact.setStatus(contactEdited.getStatus());
		contactsctrl.saveContacts(contactsMap);
	}

	@Override
	public void saveAccount(Account accountEdited)
	{
		this.account = accountEdited;
		this.accountBusiness.saveAccount((Account) accountEdited);
	}

	@Override
	public void saveGroup(String issueId, iGroup groupEdited)
	{
//		Group group = getGroup(issueId);
//		System.out.println("4avant---->"+getContact("2").getGroup());
		Group group = new Group();
		group.setId(issueId);
		group.setName(groupEdited.getName());
		group.setStatus(groupEdited.getStatus());
//		projectsMap.add("sqsdqsDSsd", );
//		groupsMap.replace(issueId, group);
//		groupsMap.put("qsdqsfqsdf", group);
		if (groupsMap.containsKey(issueId))
		{
			System.out.println("Groupmap contain the key");
//			groupsMap.remove(issueId);
//			groupsMap.put(issueId, group);
		}
		groupsMap.replace(issueId, group);
//		projectsMap.get(contact.getGroup()).add(contact.getId());
//		projectsMap.put("sdfqsdfsdffdq", FXCollections.<String>observableArrayList());
//		groupsMap.get(issueId).add(contact.getId());

		System.out.println("1---->" + projectNames);
		System.out.println("2---->" + projectsMap);
		System.out.println("3---->" + groupsMap);
//		System.out.println("4apres---->"+getContact("2").getGroup());

		contactsctrl.saveContacts(contactsMap);
		groupBusiness.createGroups(groupsMap);
	}
	
	@Override
	public void modifyGroup(String issueId, iGroup groupEdited)
	{
//		Group group = getGroup(issueId);
//		System.out.println("4avant---->"+getContact("2").getGroup());
		Group group = this.getGroup(issueId);
//		Group group = new Group();
//		group.setId(issueId);
//		group.setName(groupEdited.getName());
//		group.setStatus(groupEdited.getStatus());
//		projectsMap.add("sqsdqsDSsd", );
//		groupsMap.replace(issueId, group);
//		groupsMap.put("qsdqsfqsdf", group);
//		if (groupsMap.containsKey(issueId))
//		{
//			System.out.println("Groupmap contain the key");
////			groupsMap.remove(issueId);
////			groupsMap.put(issueId, group);
//		}
//		groupsMap.replace(issueId, group);
//		projectsMap.get(contact.getGroup()).add(contact.getId());
//		projectsMap.put("sdfqsdfsdffdq", FXCollections.<String>observableArrayList());
//		groupsMap.get(issueId).add(contact.getId());

//		System.out.println("1---->" + projectNames);
//		System.out.println("2---->" + projectsMap);
//		System.out.println("3---->" + groupsMap);
//		System.out.println("4apres---->"+getContact("2").getGroup());

//		contactsctrl.saveContacts(contactsMap);
		groupBusiness.saveGroups(group);
	}
}
