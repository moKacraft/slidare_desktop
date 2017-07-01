/*
 * Projet Slidare
 * Sharing anywhere, anytime
 * 
 */
package controller;

import model.Account;
import org.json.simple.JSONObject;
import service.APIManager;

import service.ConfigManager;
import service.FileManager;
import service.PackageManager;
import service.ServiceFactory;

/**
 * Business pour les comptes
 * 
 * @author Flavien Maillot "flavien.maillot@epitech.eu"
 */
public class AccountBusiness
{
	private Account account = null;
	
	private FileManager fileManager;
	
	private ConfigManager configManager;
	
	private PackageManager packageManager;
	
	private APIManager APIManager;
	
	public AccountBusiness()
	{
		this.fileManager = (FileManager) ServiceFactory.getFileManager();
		this.configManager = (ConfigManager) ServiceFactory.getConfigManager();
		this.packageManager = (PackageManager) ServiceFactory.getPackageManager();
		this.APIManager = (APIManager) ServiceFactory.getAPIManager();
	}
	
	/**
	 * Sauvegarde des modifications du compte dans un fichier
	 * 
	 * @param account Account to save
	 */
	public void saveAccount(Account account)
	{
		JSONObject obj = new JSONObject();
		obj.put("id", Long.parseLong(account.getId()));
		obj.put("firstname", account.getFirstname());
		obj.put("lastname", account.getLastname());
		obj.put("university", account.getUniversity());
		obj.put("job", account.getJob());
		obj.put("phone", account.getPhone());
		obj.put("city", account.getCity());
		obj.put("birth", account.getBirth());
		
		//Prepare request to server
		String request = this.packageManager
				.setJSONObject(obj)
				.getJSONString();
		
		//Send request to serveur
		if (this.APIManager.saveAccount(request) == false)
		{
			this.fileManager.writeJson(this.configManager.getPathFor("account"), obj);
		}
	}

	/**
	 * Chargement du compte a partir d'un fichier
	 */
	private void loadAccount()
	{
		JSONObject jsonObject;
		//Send request to serveur
		if (this.APIManager.loadAccount() != false)
		{
			String response = this.APIManager.getLastResponse();
			jsonObject = this.packageManager
					.setJSONObject(response)
					.getJSONOBject();
		}
		else
		{
			Object obj = this.fileManager.readJson(this.configManager.getPathFor("account"));
			jsonObject = (JSONObject) obj;
		}
		
		this.account = new Account();

		String id = Long.toString((long) jsonObject.get("id"));
		account.setId(id);
		account.setFirstname((String) jsonObject.get("firstname"));
		account.setLastname((String) jsonObject.get("lastname"));
		account.setUniversity((String) jsonObject.get("university"));
		account.setJob((String) jsonObject.get("job"));
		account.setPhone((String) jsonObject.get("phone"));
		account.setCity((String) jsonObject.get("city"));
		account.setBirth((String) jsonObject.get("birth"));
	}

	/**
	 * Permet de récupérer le compte
	 * @return Compte de l'utilisateur
	 */
	public Account getAccount()
	{
		if (this.account == null)
		{
			loadAccount();
		}
		return (this.account);
	}
}
