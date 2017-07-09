/*
 * Projet Slidare
 * Sharing anywhere, anytime
 * 
 */
package model;

/**
 *
 * @author Flavien Maillot "flavien.maillot@epitech.eu"
 */
public interface iContact
{
    public static enum ContactStatus {
        NEW, OPENED, FIXED, CLOSED
    }
    public String getId();
    public ContactStatus getStatus();
    public String getGroup(); 
    public String getFirstname();
    public String getLastname();
    public String getComment();
}
