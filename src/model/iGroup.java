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
public interface iGroup
{
	public static enum GroupStatus {
        NEW, OPENED, FIXED, CLOSED
    }
	public String getId();
	public GroupStatus getStatus();
	public String getName(); 
}
