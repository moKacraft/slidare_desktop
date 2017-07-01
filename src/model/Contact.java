/*
 * Projet Slidare
 * Sharing anywhere, anytime
 * 
 */
package model;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;

/**
 *
 * @author Flavien Maillot "flavien.maillot@epitech.eu"
 */
public class Contact implements ObservableContact
{
	private final SimpleStringProperty id;

	private final SimpleStringProperty group;

	private final SimpleStringProperty firstname;

	private final SimpleStringProperty lastname;

	private final SimpleStringProperty comment;

	private final SimpleObjectProperty<iContact.ContactStatus> status
			= new SimpleObjectProperty<iContact.ContactStatus>(iContact.ContactStatus.NEW);

	public Contact()
	{
		this.id = new SimpleStringProperty("0");
		this.group = new SimpleStringProperty("0");
		this.firstname = new SimpleStringProperty("");
		this.lastname = new SimpleStringProperty("");
		this.comment = new SimpleStringProperty("");
	}

	@Override
	public iContact.ContactStatus getStatus()
	{
		return (this.status.get());
	}

	/**
	 * 
	 * @param issueStatus status a définir
	 */
	public void setStatus(iContact.ContactStatus issueStatus)
	{
		this.status.set(issueStatus);
	}

	public void setId(String _id)
	{
		this.id.set(_id);
	}

	@Override
	public String getId()
	{
		return (this.id.get());
	}

	public void setGroup(String _group)
	{
		this.group.set(_group);
	}

	@Override
	public String getGroup()
	{
		return (this.group.get());
	}

	public void setFirstname(String _firstname)
	{
		this.firstname.set(_firstname);
	}

	@Override
	public String getFirstname()
	{
		return (this.firstname.get());
	}

	public void setLastname(String _lastname)
	{
		this.lastname.set(_lastname);
	}

	@Override
	public String getLastname()
	{
		return (this.lastname.get());
	}

	public void setComment(String _comment)
	{
		this.comment.set(_comment);
	}

	@Override
	public String getComment()
	{
		return (this.comment.get());
	}

	@Override
	public ObservableValue<String> IdProperty()
	{
		return (this.id);
	}

	@Override
	public ObservableValue<String> GroupProperty()
	{
		return (this.group);
	}

	@Override
	public ObservableValue<String> FirstnameProperty()
	{
		return (this.firstname);
	}

	@Override
	public ObservableValue<String> LastnameProperty()
	{
		return (this.lastname);
	}

	@Override
	public ObservableValue<String> CommentProperty()
	{
		return (this.comment);
	}

	@Override
	public ObservableValue<ContactStatus> StatusProperty()
	{
		return (this.status);
	}
	
//	public boolean equals(Object obj) 
//	{
//		return (obj instanceof Contact) && 
//		((Contact)obj).getId().equals(this.getId()) && 
//		((Contact)obj).getFirstname().equals(this.getFirstname()) &&
//		((Contact)obj).getLastname().equals(this.getLastname()) &&
//		((Contact)obj).getComment().equals(this.getComment()) 
//		;
//    }
}
