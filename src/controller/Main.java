/*
 * Projet Slidare
 * Sharing anywhere, anytime
 * 
 */
package controller;

import io.socket.client.Socket;
import java.awt.Dimension;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javax.swing.JDialog;
import javax.swing.JLabel;
import model.DragDropTracking;
import org.jnativehook.NativeHookException;
import service.ConfigManager;
import service.ServiceFactory;

public class Main extends Application
{
	public static Stage parentWindow;
        public static Socket socket;
        public static JDialog dialogSend;
        public static JLabel labelSend;
	
	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args)
	{
            dialogSend = new JDialog();
            labelSend = new JLabel("Please wait...");
            dialogSend.setLocationRelativeTo(null);
            dialogSend.setTitle("Please Wait...");
            dialogSend.add(labelSend);
            dialogSend.setAlwaysOnTop(true);
            dialogSend.setPreferredSize(new Dimension(300,100));
            dialogSend.pack();
            Application.launch(Main.class, (java.lang.String[]) null);
                
	}
	
	public static void loadScene(String sceneName, String title) throws IOException
	{
		ConfigManager configManager = (ConfigManager) ServiceFactory.getConfigManager();
		ResourceBundle bundle = ResourceBundle.getBundle("lang.lang", new Locale(configManager.getConfig().getLang()));
		
		AnchorPane page = (AnchorPane) FXMLLoader.load(Main.class.getResource(sceneName), bundle);
		parentWindow.setTitle("Slidare - " + bundle.getString(title));
		parentWindow.getScene().setRoot(page); 
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception
	{
		try
		{
			ConfigManager configManager = (ConfigManager) ServiceFactory.getConfigManager();
			configManager.loadBase();
			
			ConnectController connectctrl = new ConnectController();
			
			parentWindow = primaryStage;
			
			ResourceBundle bundle = ServiceFactory.getResourceBundle(false);
			AnchorPane page;
			
			connectctrl.checkIdentification("flavien.maillot@epitech.eu", "admin");
			
			if (configManager.getConfig().getAutoConnect()== true && connectctrl.checkIdentification(configManager.getConfig().getUsername(), configManager.getConfig().getPassword()))
                        {
                            page = (AnchorPane) FXMLLoader.load(Main.class.getResource("/view/ContactTracking.fxml"), bundle);
                            try {
                                DragDropTracking dragDropTracking = new DragDropTracking();
                            } catch (NativeHookException ex) {
                            Logger.getLogger(ConnectController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                        else {
                            page = (AnchorPane) FXMLLoader.load(Main.class.getResource("/view/connect.fxml"), bundle);
                            try {
                                DragDropTracking dragDropTracking = new DragDropTracking();
                            } catch (NativeHookException ex) {
                                Logger.getLogger(ConnectController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
			Scene scene = new Scene(page);
			primaryStage.setScene(scene);
			primaryStage.setTitle("Slidare - " + bundle.getString("Connect_title"));
			configManager.configStage(primaryStage);
			primaryStage.show();	
		}
		catch (Exception ex)
		{
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
}
