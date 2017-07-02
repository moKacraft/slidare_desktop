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
            dialogSend.setPreferredSize(new Dimension(300,100));
            dialogSend.pack();
                   
            try {
               socket = IO.socket("http://54.224.110.79:8080");
               System.out.println("socket init");
               socket.on("server ready", new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    
                    dialogSend.setVisible(true);     
                    System.out.println("Server ready");
                    
                    new Thread(new Runnable() {
                               @Override
                               public void run() {
                    try {
                        java.net.Socket sock;
                        sock = new java.net.Socket("54.224.110.79", (int)args[0]);
                        OutputStream is = sock.getOutputStream();
                        FileInputStream fis = new FileInputStream((String)args[1]);
                        BufferedInputStream bis = new BufferedInputStream(fis);
                        byte[] buffer = new byte[4096];
                        int ret;
                        while ((ret = fis.read(buffer)) > 0) {
                            is.write(buffer, 0, ret);
                        }
                        fis.close();
                        bis.close();
                        is.close();
                        sock.close();
                    } catch (IOException ex) {
                        Logger.getLogger(EventlogController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    }
                            }).start();
                }
            }).on("sophie@slidare.com", new Emitter.Listener() {
                @Override
                public void call(Object... args) 
                {
                   Platform.runLater(new Runnable() {
                       @Override
                        public void run() {
                            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                            alert.setTitle("Info");
                            alert.setHeaderText("File Received");
                            alert.setContentText("");
                            Optional<ButtonType> result = alert.showAndWait();
                            System.out.println(result);

                            if (result.isPresent() && result.get() != ButtonType.OK) {
                                return;
                            }

                            new Thread(new Runnable() {
                               @Override
                               public void run() {
                                    JDialog dialog = new JDialog();
                                    JLabel label = new JLabel("Please wait...");
                                    dialog.setLocationRelativeTo(null);
                                    dialog.setTitle("Please Wait...");
                                    dialog.add(label);
                                    dialog.setPreferredSize(new Dimension(300, 100));
                                    dialog.pack();
                                    
                                   System.out.println("Receving File");
                                   String transferId = (String) args[2];
                                   try {
                                       FileOutputStream fos = new FileOutputStream((String) args[3]);
                                       java.net.Socket sock = new java.net.Socket("54.224.110.79", (int)args[1]);
                                       InputStream is = sock.getInputStream();
                                       byte[] buffer = new byte[4096];
                                       int ret;
                                       
                                       double sumCount = 0.0;
                                       
                                       dialog.setVisible(true);
                                       
                                       while ((ret = is.read(buffer)) > 0) {
                                           fos.write(buffer, 0, ret);
                                           
                                           sumCount += ret;
                                           System.out.println("Percentace: " + (sumCount / (int) args[9] * 100.0) + "%");
                                           //Thread.sleep(1000);
                                           label.setText("Percentace: " + (sumCount / (int) args[9] * 100.0) + "%");
                                       }
                                       dialog.setVisible(false);
                                       System.out.println("Transfer Finished");
                                       fos.close();
                                       is.close();
                                       sock.close();
                                       socket.emit("transfer finished", transferId);

                                       encryption _crypt = new  encryption();
                                       byte[] salt = Base64.decodeBase64((String) args[6]);
                                       byte[] iv = Base64.decodeBase64((String) args[7]);
                                       System.out.println(salt);
                                       System.out.println(iv);
                                       System.out.println(salt.length);
                                       System.out.println(iv.length);
                                       _crypt.decryptFile((String) args[3], (String) args[4], (String) args[5], salt, iv, (String) args[8]);
                                   } catch (IOException ex) {
                                       Logger.getLogger(EventlogController.class.getName()).log(Level.SEVERE, null, ex);
                                   } catch (NoSuchAlgorithmException ex) {
                                       Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                                   } catch (NoSuchPaddingException ex) {
                                       Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                                   } catch (InvalidKeySpecException ex) {
                                       Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                                   } catch (InvalidAlgorithmParameterException ex) {
                                       Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                                   } catch (InvalidKeyException ex) {
                                       Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                                   } catch (BadPaddingException ex) {
                                       Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                                   } catch (IllegalBlockSizeException ex) {
                                       Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                                   }
                                  }
                            }).start();
                        }
                    });
                }
            }).on("receiving data", new Emitter.Listener() {
                    @Override
                    public void call(Object... args) {
                       System.out.println("args[0] " + (String)args[0] + "args[1] " + args[1]);
                       labelSend.setText((String) args[0]);
                       if ((boolean) args[1] == true)
                        dialogSend.setVisible(false);
                    }
            });
            socket.connect();
               
            } catch (URISyntaxException ex) {
                System.out.println(ex.getMessage());
                System.out.println(ex.getReason());
            }
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
