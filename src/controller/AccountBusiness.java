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

	ConfigManager configManager;

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
//		JSONObject obj = new JSONObject();
//		obj.put("id", Long.parseLong(account.getId()));
//		obj.put("firstname", account.getFirstname());
//		obj.put("lastname", account.getLastname());
//		obj.put("university", account.getUniversity());
//		obj.put("job", account.getJob());
//		obj.put("phone", account.getPhone());
//		obj.put("city", account.getCity());
//		obj.put("birth", account.getBirth());
//
//		//Prepare request to server
//		String request = this.packageManager
//				.setJSONObject(obj)
//				.getJSONString();
//
//		//Send request to serveur
//		if (this.APIManager.saveAccount(request) == false)
//		{
//			this.fileManager.writeJson(this.configManager.getPathFor("account"), obj);
//		}
		
		//MISE EN PLACE DE L'API QUI PREND EN CHARGE LA MOITIER DES FONCTIONNALITE
		//Prepare request to server
		String request = this.packageManager
				.init()
				.addParam("username", account.getUsername())
				.getJSONString();
		this.APIManager.saveStupidAccount("updateUserName", request, this.configManager.getConfig().getToken());
		System.out.println(this.APIManager.getLastCode() + this.APIManager.getLastResponse());
		//Prepare request to server
		request = this.packageManager
				.init()
				.addParam("email", account.getEmail())
				.getJSONString();
		this.APIManager.saveStupidAccount("updateUserEmail", request, this.configManager.getConfig().getToken());
		
		System.out.println(this.APIManager.getLastCode() + this.APIManager.getLastResponse());
		
		//Prepare request to server
		request = this.packageManager
				.init()
				.addParam("old_password", this.account.getOldpassword())
				.addParam("new_password", account.getPassword())
				.getJSONString();
		this.APIManager.saveStupidAccount("updateUserPassword", request, this.configManager.getConfig().getToken());
		System.out.println(this.APIManager.getLastCode() + this.APIManager.getLastResponse());
		account.setOldpassword(account.getPassword());
		this.account.setOldpassword(account.getPassword());
	}

	/**
	 * Chargement du compte a partir d'un fichier
	 */
	private void loadAccount()
	{
		JSONObject jsonObject;
		this.account = new Account();

		//Send request to serveur
		if (this.APIManager.fetchUser(this.configManager.getConfig().getToken()) != false)
		{
			String response = this.APIManager.getLastResponse();
			System.out.println("controller.AccountBusiness.loadAccount()" + response);
			this.packageManager.setJSONObject(response);

			String id = "0";
			account.setId(id);
			account.setIdapi(this.packageManager.getStringDefault("id", "Inconnu"));
			account.setFirstname(this.packageManager.getStringDefault("first_name", "Inconnu"));
			account.setLastname(this.packageManager.getStringDefault("last_name", "Inconnu"));
			account.setUniversity(this.packageManager.getStringDefault("university", "Inconnu"));
			account.setJob(this.packageManager.getStringDefault("job", "Inconnu"));
			account.setPhone(this.packageManager.getStringDefault("phone", "Inconnu"));
			account.setCity(this.packageManager.getStringDefault("city", "Inconnu"));
			account.setBirth(this.packageManager.getStringDefault("birth", "Inconnu"));
			account.setEmail(this.packageManager.getStringDefault("email", "Inconnu"));
			account.setPassword(this.packageManager.getStringDefault("password", "Inconnu"));
			account.setOldpassword(this.packageManager.getStringDefault("password", "Inconnu"));
			account.setUsername(this.packageManager.getStringDefault("username", "Inconnu"));
		} else
		{
			Object obj = this.fileManager.readJson(this.configManager.getPathFor("account"));
			jsonObject = (JSONObject) obj;

			String id = Long.toString((long) jsonObject.get("id"));
			account.setId(id);
			account.setFirstname((String) jsonObject.get("firstname"));
			account.setLastname((String) jsonObject.get("lastname"));
			account.setUniversity((String) jsonObject.get("university"));
			account.setJob((String) jsonObject.get("job"));
			account.setPhone((String) jsonObject.get("phone"));
			account.setCity((String) jsonObject.get("city"));
			account.setBirth((String) jsonObject.get("birth"));
			account.setEmail((String) jsonObject.get("email"));
			account.setPassword((String) jsonObject.get("password"));
			account.setOldpassword((String) jsonObject.get("password"));
			account.setUsername((String) jsonObject.get("username"));
		}

		System.out.println("controller.AccountBusiness.loadAccount()");
	}

	/**
	 * Permet de récupérer le compte
	 *
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
