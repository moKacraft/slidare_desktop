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
public interface ObservableGroup extends iGroup
{
	public ObservableValue<String> IdProperty();
	public ObservableValue<iGroup.GroupStatus> StatusProperty();
	public ObservableValue<String> NameProperty(); 
}
