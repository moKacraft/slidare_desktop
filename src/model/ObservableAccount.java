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
public interface ObservableAccount extends iAccount
{
	public ObservableValue<String> IdProperty();
	public ObservableValue<String> FirstnameProperty();
	public ObservableValue<String> LastnameProperty();
	public ObservableValue<String> UniversityProperty();
	public ObservableValue<String> JobProperty();
	public ObservableValue<String> PhoneProperty();
	public ObservableValue<String> CityProperty();
	public ObservableValue<String> BirthProperty();
}
