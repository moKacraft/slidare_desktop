/*
 * Projet Slidare
 * Sharing anywhere, anytime
 * 
 */
package model;

import javafx.beans.value.ObservableValue;

/**
 *
 * @author Flavien Maillot "flavien.maillot@epitech.eu"
 */
public interface ObservableContact extends iContact
{
	public ObservableValue<String> IdProperty();
	public ObservableValue<ContactStatus> StatusProperty();
	public ObservableValue<String> GroupProperty(); 
	public ObservableValue<String> FirstnameProperty();
	public ObservableValue<String> LastnameProperty();
	public ObservableValue<String> CommentProperty();
}
