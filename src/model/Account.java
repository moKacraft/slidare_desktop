/*
 * Projet Slidare
 * Sharing anywhere, anytime
 * 
 */
package model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;

/**
 * Account is the entity for the user log in Slidare
 * 
 * @author Flavien Maillot "flavien.maillot@epitech.eu"
 */
public class Account implements ObservableAccount
{
	private final SimpleStringProperty id;
	
	private final SimpleStringProperty firstname;

	private final SimpleStringProperty lastname;
	
	private final SimpleStringProperty university;
			
	private final SimpleStringProperty job;
	
	private final SimpleStringProperty phone;
	
	private final SimpleStringProperty city;
	
	private final SimpleStringProperty birth;

	/**
	 *
	 */
	public Account()
	{
		this.id = new SimpleStringProperty("");
		this.firstname = new SimpleStringProperty("");
		this.lastname = new SimpleStringProperty("");
		this.university = new SimpleStringProperty("");
		this.job = new SimpleStringProperty("");
		this.phone = new SimpleStringProperty("");
		this.city = new SimpleStringProperty("");
		this.birth = new SimpleStringProperty("");
	}
	
	/**
	 *
	 * @param _id id to set
	 */
	public void setId(String _id)
	{
		this.id.set(_id);
	}

	/**
	 *
	 * @return id
	 */
	@Override
	public String getId()
	{
		return (this.id.get());
	}
	
	/**
	 *
	 * @param _firstname firsname
	 */
	public void setFirstname(String _firstname)
	{
		this.firstname.set(_firstname);
	}
	
	/**
	 *
	 * @return firstname
	 */
	@Override
	public String getFirstname()
	{
		return (this.firstname.get());
	}

	/**
	 *
	 * @param _lastname lastname
	 */
	public void setLastname(String _lastname)
	{
		this.lastname.set(_lastname);
	}

	/**
	 *
	 * @return lastname
	 */
	@Override
	public String getLastname()
	{
		return (this.lastname.get());
	}
	
	/**
	 *
	 * @param _university university
	 */
	public void setUniversity(String _university)
	{
		this.university.set(_university);
	}
	
	/**
	 *
	 * @return university
	 */
	@Override
	public String getUniversity()
	{
		return (this.university.get());
	}
		
	/**
	 *
	 * @param _job job
	 */
	public void setJob(String _job)
	{
		this.job.set(_job);
	}

	/**
	 *
	 * @return job
	 */
	@Override
	public String getJob()
	{
		return (this.job.get());
	}
	
	/**
	 *
	 * @param _phone phone
	 */
	public void setPhone(String _phone)
	{
		this.phone.set(_phone);
	}

	/**
	 *
	 * @return phone
	 */
	@Override
	public String getPhone()
	{
		return (this.phone.get());
	}
	
	/**
	 *
	 * @param _city city
	 */
	public void setCity(String _city)
	{
		this.city.set(_city);
	}

	/**
	 *
	 * @return city
	 */
	@Override
	public String getCity()
	{
		return (this.city.get());
	}
	
	/**
	 *
	 * @param _birth birth
	 */
	public void setBirth(String _birth)
	{
		this.birth.set(_birth);
	}

	/**
	 *
	 * @return birth
	 */
	@Override
	public String getBirth()
	{
		return (this.birth.get());
	}

	/**
	 *
	 * @return id
	 */
	@Override
	public ObservableValue<String> IdProperty()
	{
		return (this.id);
	}

	/**
	 *
	 * @return firstname
	 */
	@Override
	public ObservableValue<String> FirstnameProperty()
	{
		return (this.firstname);
	}

	/**
	 *
	 * @return lastname
	 */
	@Override
	public ObservableValue<String> LastnameProperty()
	{
		return (this.lastname);
	}

	/**
	 *
	 * @return university
	 */
	@Override
	public ObservableValue<String> UniversityProperty()
	{
		return (this.university);
	}

	/**
	 *
	 * @return job
	 */
	@Override
	public ObservableValue<String> JobProperty()
	{
		return (this.job);
	}

	/**
	 *
	 * @return phone
	 */
	@Override
	public ObservableValue<String> PhoneProperty()
	{
		return (this.phone);
	}

	/**
	 *
	 * @return city
	 */
	@Override
	public ObservableValue<String> CityProperty()
	{
		return (this.city);
	}

	/**
	 *
	 * @return birth
	 */
	@Override
	public ObservableValue<String> BirthProperty()
	{
		return (this.birth);
	}
	
	/**
	 *
	 * @param obj object to compare
	 * @return true or false
	 */
	public boolean equals(Object obj) 
	{
		return (obj instanceof Account) && 
		((Account)obj).getId().equals(this.getId()) && 
		((Account)obj).getFirstname().equals(this.getFirstname()) &&
		((Account)obj).getLastname().equals(this.getLastname()) &&
		((Account)obj).getUniversity().equals(this.getUniversity()) &&
		((Account)obj).getJob().equals(this.getJob()) &&
		((Account)obj).getPhone().equals(this.getPhone()) &&
		((Account)obj).getCity().equals(this.getCity()) &&
		((Account)obj).getBirth().equals(this.getBirth())
		;
    }
}
