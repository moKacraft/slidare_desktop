/*
 * Projet Slidare
 * Sharing anywhere, anytime
 * 
 */

package controller;

/**
 * Methode courrante utilisée dans les controllers
 * @author Flavien Maillot "flavien.maillot@epitech.eu"
 */
public abstract class Controller
{
    /**
     * Vérifie si une chaine est vide
     * @param s Chaine a vérifier
     * @return retourne si la chaine est vide ou non
     */
    protected boolean isEmpty(String s)
    {
        return s == null || s.trim().isEmpty();
    }
	
    /**
     * Alias de isEmpty pour correspondre a la fonction php
     * 
     * @see isEmpty
     * @param s Chaine a vérifier
     * @return retourne si la chaine est vide ou non
     */
    protected boolean empty(String s)
    {
        return (isEmpty(s));
    }
	
    protected boolean isVoid(Object o)
    {
        if (o instanceof String) {
            return isEmpty((String) o);
        } else {
            return o == null;
        }
    }
	
    protected boolean equal(Object o1, Object o2)
    {
        if (isVoid(o1)) {
            return isVoid(o2);
        }
        return o1.equals(o2);
    }
	
    protected String nonNull(String s)
    {
        return s == null ? "" : s;
    }
}
