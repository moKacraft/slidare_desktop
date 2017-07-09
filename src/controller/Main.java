/*
 * Projet Slidare
 * Sharing anywhere, anytime
 * 
 */
package controller;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import java.awt.Dimension;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.swing.JDialog;
import javax.swing.JLabel;
import model.DragDropTracking;
import org.apache.commons.codec.binary.Base64;
import org.jnativehook.NativeHookException;
import service.ConfigManager;
import service.ServiceFactory;
import utils.security.encryption;

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
			else
                        {
				page = (AnchorPane) FXMLLoader.load(Main.class.getResource("/view/connect.fxml"), bundle);
                         try {
                            DragDropTracking dragDropTracking = new DragDropTracking();
                        } catch (NativeHookException ex) {
                            Logger.getLogger(ConnectController.class.getName()).log(Level.SEVERE, null, ex);
                        }}
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
