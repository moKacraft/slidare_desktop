/*
 * Projet Slidare
 * Sharing anywhere, anytime
 * 
 */
package model;

import controller.ContactsBusiness;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

public interface TrackingService 
{
    public ObservableList<String> getContactIds(String projectName);
    public ObservableList<String> getProjectNames();
    public ObservableList<String> getAccountMenu();
    public ContactsBusiness getContactsBusiness();
    
    public Account getAccount();
    public void saveAccount(Account accountEdited);
    
    public Group getGroup(String issueId);
    public ObservableMap<String, Group> getGroups();
    public Group createGroup(String groupName);
    public void deleteGroup(String groupId);
    public void saveGroup(String issueId, iGroup groupEdited);
    
    public ObservableContact getContact(String tickectId);
    public ObservableMap<String, Contact> getContacts();
    public ObservableContact addContactFor(String projectName, Contact contact);
    public ObservableContact createContactFor(String projectName);
    public void deleteContact(String issueId);
    public void saveContact(String issueId, iContact.ContactStatus status, String synopsis, String description);
    public void saveContact(String issueId, iContact contactEdited);
}
