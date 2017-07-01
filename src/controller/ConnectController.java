/*
 * Projet Slidare
 * Sharing anywhere, anytime
 * 
 */
package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import service.APIManager;
import service.ConfigManager;
import service.PackageManager;
import service.ServiceFactory;

/**
 * FXML Controller class
 *
 * @author Flavien Maillot "flavien.maillot@epitech.eu"
 */
public class ConnectController extends Controller implements Initializable
{
	private PackageManager packageManager;
	
	private APIManager APIManager;
	
	private ConfigManager cfg;
	
	@FXML 
	private Text actiontarget;
	@FXML
	private TextField username;
	@FXML
	private PasswordField password;
	@FXML
	private CheckBox autoconnect;
	
	public ConnectController()
	{
		this.packageManager = (PackageManager) ServiceFactory.getPackageManager();
		this.APIManager = (APIManager) ServiceFactory.getAPIManager();
		this.cfg = (ConfigManager) ServiceFactory.getConfigManager();
	}
	
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
    }
	
	/**
	 * Check the identification of the user with the server
	 * 
	 * @param user username
	 * @param pass password
	 * @return identification is succeed or not
	 */
	public boolean checkIdentification(String user, String pass)
	{
		String request = this.packageManager
				.init()
				.addParam("email", user)
				.addParam("password", pass)
				.getJSONString();
		
		this.APIManager.loginUser(request);
		if (this.APIManager.inError() == false)
		{
			this.packageManager.setJSONObject(this.APIManager.getLastResponse());
			this.cfg.getConfig().setToken(this.packageManager.getStringDefault("token", "inconnu"));
			this.cfg.getConfig().setId(this.packageManager.getStringDefault("id", "inconnu"));
			
//			this.APIManager.userContacts(this.cfg.getConfig().getToken());
//			System.out.println("controller.ConnectController.checkIdentification()");
//			System.out.println(this.APIManager.getLastCode());
//			System.out.println(this.APIManager.getLastResponse());
//			
//			System.exit(0);
			return (true);
		}
		else
			return (true);
	}
	
	/**
	 * Create an account on the server
	 * 
	 * @param user username
	 * @param pass password
	 * @return creation of account is succeed or not
	 */
	public boolean createAccount(String user, String pass)
	{
		String request = this.packageManager
				.init()
				.addParam("first_name", "Nouveau")
				.addParam("last_name", "Nouveau")
				.addParam("email", user)
				.addParam("password", pass)
				.getJSONString();
		
		this.APIManager.createUser(request);
		if (this.APIManager.inError() == false)
		{
			this.packageManager.setJSONObject(this.APIManager.getLastResponse());
			this.cfg.getConfig().setToken(this.packageManager.getStringDefault("token", "inconnu"));
			this.cfg.getConfig().setId(this.packageManager.getStringDefault("id", "inconnu"));
			
			return (true);
		}
		else
			return (false);
	}
	
	@FXML
	public void doConnexion(ActionEvent event) throws IOException
	{
		if (checkIdentification(username.getText(), password.getText()) == true)
		{
			if (autoconnect.isSelected())
			{
				this.cfg.getConfig().setUsername(username.getText());
				this.cfg.getConfig().setPassword(password.getText());
				this.cfg.getConfig().setAutoConnect(true);
				this.cfg.save();
			}
			Main.loadScene("/view/ContactTracking.fxml", "Contact_title");
		}
		else
		{
			actiontarget.setText("Mauvais nom d'utilisateur ou mot de passe");
		}
	}
	
	@FXML
	public void doCreateAccount(ActionEvent event) throws IOException
	{
		if (createAccount(username.getText(), password.getText()) == true)
		{
			Main.loadScene("/view/ContactTracking.fxml", "Contact_title");
		}
		else
		{
			actiontarget.setText(this.APIManager.getLastResponse());
		}
	}
    
}
