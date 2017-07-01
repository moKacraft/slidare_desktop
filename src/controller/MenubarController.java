/*
 * Projet Slidare
 * Sharing anywhere, anytime
 * 
 */
package controller;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.HBox;
import service.ConfigManager;
import service.FileManager;
import service.ServiceFactory;
import utils.DialogSys;

/**
 *
 * @author Flavien Maillot "flavien.maillot@epitech.eu"
 */
public class MenubarController implements Initializable
{

	@FXML
	private HBox MenuBarHB;
	
	@Override
	public void initialize(URL location, ResourceBundle resources)
	{
	}

	public void loadConfig(ActionEvent event) throws IOException
	{
		FileManager fileManager = (FileManager) ServiceFactory.getFileManager();
		fileManager.initFileChooserConfig(Main.parentWindow);
		String filepath = fileManager.getFilePath();

		if (!filepath.equals(""))
		{
			ConfigManager configManager = (ConfigManager) ServiceFactory.getConfigManager();
			configManager.load(filepath);
			configManager.configStage(Main.parentWindow);
			Main.loadScene("/view/ContactTracking.fxml", "Contact_title");
		}
	}

	public void logout(ActionEvent event) throws IOException
	{
		ConfigManager cfg = (ConfigManager) ServiceFactory.getConfigManager();
		cfg.getConfig().setUsername("");
		cfg.getConfig().setPassword("");
		cfg.getConfig().setAutoConnect(false);
		cfg.save();
		
		//Chargement de la page de connection
		Main.loadScene("/view/connect.fxml", "Connect_title");
	}
	
	public void quitFired(ActionEvent event)
	{
		Platform.exit();
		System.exit(0);
	}

	public void switchScene(ActionEvent event) throws IOException
	{
		MenuItem menu = (MenuItem) event.getSource();
		String menuId = menu.getId();
		if (null != menuId)
		{
			switch (menuId)
			{
				case "manageaccount":
					Main.loadScene("/view/AccountTracking.fxml", "Account_title");
					break;
				case "managegroups":
					Main.loadScene("/view/GroupTracking.fxml", "Group_title");
					break;
				case "managecontacts":
					Main.loadScene("/view/ContactTracking.fxml", "Contact_title");
					break;
				case "setting":
					Main.loadScene("/view/ConfigTracking.fxml", "Contact_title");
					break;
                                case "managedragdrop":
					Main.loadScene("/view/DragDropTracking.fxml", "DragDrop_title");
					break;
				case "eventlog":
					DialogSys ds = new DialogSys();
					ds.showPopup(MenuBarHB, "/view/eventlog.fxml", "Eventlog");
					break;
				default:
					System.err.println("Switch inconnu");
					break;
			}
		}
	}

	public void aboutUs(ActionEvent event)
	{

		ResourceBundle bundle = ServiceFactory.getResourceBundle(false);
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		alert.setTitle(bundle.getString("AboutUs_title"));
		alert.setHeaderText(null);
		alert.setContentText(bundle.getString("AboutUs_content"));

		ButtonType buttonTypeOne = new ButtonType(bundle.getString("AboutUs_website_btn"));
		ButtonType buttonTypeCancel = new ButtonType(bundle.getString("AboutUs_close"), ButtonBar.ButtonData.CANCEL_CLOSE);

		alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeCancel);

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == buttonTypeOne)
		{
			try
			{
				Desktop.getDesktop().browse(new URL(bundle.getString("AboutUs_URL")).toURI());
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		} 
	}
}
