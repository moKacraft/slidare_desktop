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
public class Group implements ObservableGroup
{
    private final SimpleStringProperty id;
	
    private final SimpleStringProperty idapi;
	
    private final SimpleStringProperty name;
	
    private String oldname;

	private String users;
	
    private final SimpleObjectProperty<iGroup.GroupStatus> status
            = new SimpleObjectProperty<iGroup.GroupStatus>(iGroup.GroupStatus.NEW);
    
    public Group()
    {
        this.id = new SimpleStringProperty("");
        this.idapi = new SimpleStringProperty("");
        this.name = new SimpleStringProperty("");
        this.users = "";
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
    
    @Override
    public ObservableValue<String> IdProperty()
    {
        return (this.id);
    }
	
	public void setIdAPI(String _idapi)
    {
        this.idapi.set(_idapi);
    }
    
//    @Override
    public String getIdAPI()
    {
        return (this.idapi.get());
    }
    
    @Override
    public ObservableValue<String> IdAPIProperty()
    {
        return (this.idapi);
    }
    
    public void setName(String _name)
    {
        this.name.set(_name);
    }
    
    @Override
    public ObservableValue<String> NameProperty()
    {
        return (this.name);
    }
    
    @Override
    public String getName()
    {
        return (this.name.get());
    }
	
	public void setOldname(String _name)
    {
        this.oldname = _name;
    }
	
	public String getOldname()
    {
        return (this.oldname);
    }
	
	public void setUsers(String _name)
    {
        this.users = _name;
    }
    
    public String getUsers()
	{
		return (this.users);
	}
	
    public void setStatus(iGroup.GroupStatus issueStatus)
    {
        this.status.set(issueStatus);
    }
    
    @Override
    public GroupStatus getStatus()
    {
        return (this.status.get());
    }
    
    @Override
    public ObservableValue<GroupStatus> StatusProperty()
    {
        return (this.status);
    }
    
    public boolean equals(Object obj)
    {
        return (obj instanceof Group) &&
                ((Group)obj).getId().equals(this.getId()) &&
                ((Group)obj).getName().equals(this.getName())
                ;
    }
}
