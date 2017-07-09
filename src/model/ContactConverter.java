/*
 * Projet Slidare
 * Sharing anywhere, anytime
 * 
 */
package model;

import java.util.HashMap;
import java.util.Map;
import javafx.util.StringConverter;

/**
 *
 * @author Flavien Maillot "flavien.maillot@epitech.eu"
 */
public class ContactConverter extends StringConverter<Contact>
{
    /** Cache de productos */
    private Map<String, Contact> mapContacts = new HashMap<String, Contact>();
    
    @Override
    public String toString(Contact contact)
    {
        mapContacts.put(contact.getFirstname() + " " + contact.getLastname(), contact);
        return (contact.getFirstname() + " " + contact.getLastname());
    }
    
    @Override
    public Contact fromString(String contact)
    {
        return mapContacts.get(contact);
    }
}
