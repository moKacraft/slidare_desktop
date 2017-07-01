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
public interface iConfig
{
	String		getLang();
	boolean		getMaximized();
	int			getWidth();
	int			getHeight();
	boolean		getAutoConnect();
	String		getUsername();
	String		getPassword();
	String		getToken();
	String		getId();
	boolean		isEqual(Object obj);
}
