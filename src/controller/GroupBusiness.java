/*
 * Projet Slidare
 * Sharing anywhere, anytime
 * 
 */
package controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.Group;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import service.APIManager;
import service.ConfigManager;
import service.FileManager;
import service.PackageManager;
import service.ServiceFactory;

/**
 *
 * @author Flavien Maillot "flavien.maillot@epitech.eu"
 */
public class GroupBusiness
{
    private Map<String, Group> groups = new HashMap<>();
    
    private FileManager fileManager;
    
    private ConfigManager configManager;
    
    private PackageManager packageManager;
    
    private APIManager APIManager;
    
    public GroupBusiness()
    {
		System.out.println("Group Business");
        this.fileManager = (FileManager) ServiceFactory.getFileManager();
        this.configManager = (ConfigManager) ServiceFactory.getConfigManager();
        this.packageManager = (PackageManager) ServiceFactory.getPackageManager();
        this.APIManager = (APIManager) ServiceFactory.getAPIManager();
    }
    
    /**
     * Save the group on the server or in a local file to sync later
     *
     * @param groupsList List of group to save
     */
    public void saveGroups(Map<String, Group> groupsList)
    {
        JSONArray groupListToSave = new JSONArray();
        Group group;
        for (Map.Entry<String, Group> grouppair : groupsList.entrySet()) {
            JSONObject obj = new JSONObject();
            group = grouppair.getValue();
            obj.put("id", Long.parseLong(group.getId()));
            obj.put("name", group.getName());
            groupListToSave.add(obj);
        }
        
        //Prepare request to server
        String request = this.packageManager
                .setJSONArray(groupListToSave)
                .getJSONString();
        
        //Send request to serveur
        if (this.APIManager.saveGroups(request) == false) {
            this.fileManager.writeJson(this.configManager.getPathFor("groups"), groupListToSave);
        }
    }
    
    /**
     * Load the groups from the server
     */
    private void loadGroups()
    {
        this.groups.clear();
        JSONArray jsonArray;
        
		this.APIManager.fetchGroups(this.configManager.getConfig().getToken());
		
        //Send request to serveur
//        if (this.APIManager.loadGroups() != false) {
        if (this.APIManager.fetchGroups(this.configManager.getConfig().getToken()) != false) 
		{
            String response = this.APIManager.getLastResponse();
			System.out.println(response);
            jsonArray = this.packageManager
                    .setJSONObject(response)
                    .getJSONArrayDefault("groups");
			PackageManager child = this.packageManager.getChild();
			int length = jsonArray.size();
			for (int i = 0; i < length; i++) 
			{
				child.setJSONObject((JSONObject) jsonArray.get(i));
				Group group = new Group();
				int gpid = i + 1;
				String id = String.valueOf(gpid);
				String idAPI = child.getStringDefault("id", "-1");
				String name = child.getStringDefault("name", "Inconnu");
				String users = child.getJSONArrayDefault("users").toJSONString();
				
				group.setId(id);
				group.setIdAPI(idAPI);
				group.setName(name);
				group.setUsers(users);
				this.groups.put(group.getId(), group);
			}
			Group group = new Group();
			group.setId("0");
			group.setIdAPI("0");
			group.setName("Non classé");
			group.setUsers("[]");
			this.groups.put(group.getId(), group);
        }
		else 
		{
            Object obj = this.fileManager.readJson(this.configManager.getPathFor("groups"));
            jsonArray = (JSONArray) obj;
			
			int length = jsonArray.size();
			for (int i = 0; i < length; i++) 
			{
				JSONObject jsonObject = (JSONObject) jsonArray.get(i);
				Group group = new Group();

	            String id = Long.toString((long) jsonObject.get("id"));
				group.setId(id);
				group.setName((String) jsonObject.get("name"));
				this.groups.put(group.getId(), group);
			}
        }
//		System.out.println("controller.GroupBusiness.loadGroups()");
    }
    
    /**
     * Get all group in a map
     *
     * @return list of contacts
     */
    public Map<String, Group> getGroups()
    {
        if (this.groups.isEmpty()) {
			System.out.println("controller.GroupBusiness.getGroups()");
            loadGroups();
        }
        return (this.groups);
    }
    
    /**
     * Direct replace the Arrays.asList();
     *
     * @return List of the groups
     */
    public List<String> getGroupList()
    {
        if (this.groups.isEmpty()) {
            this.loadGroups();
        }
        List<String> tmpgroup = new ArrayList<>();
        for (Map.Entry<String, Group> grouppair : this.groups.entrySet()) {
            tmpgroup.add(grouppair.getValue().getName());
        }
        return (tmpgroup);
    }
}
