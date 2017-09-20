/*
* Projet Slidare
* Sharing anywhere, anytime
*
 */
package controller;

import static controller.Main.dialogSend;
import static controller.Main.labelSend;
import static controller.Main.socket;
import io.socket.client.IO;
import io.socket.emitter.Emitter;
import java.awt.Dimension;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.swing.JDialog;
import javax.swing.JLabel;
import org.apache.commons.codec.binary.Base64;
import service.APIManager;
import service.ConfigManager;
import service.PackageManager;
import service.ServiceFactory;
import utils.security.encryption;

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
	 *
	 * @param url
	 * @param rb
	 */
	@Override
	public void initialize(URL url, ResourceBundle rb)
	{
	}

	/**
	 * Check the identification of the user with the server soso@gmail.com tests
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
		{
			return (false);
		}
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
		{
			return (false);
		}
	}

	@FXML
	public void doConnexion(ActionEvent event) throws IOException
	{
		if (checkIdentification(username.getText(), password.getText()) == true)
		{
//                        String str = this.packageManager.getStringDefault("email", "inconnu");
			setupConnections();
			System.out.println(username.getText());
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

	public void setupConnections()
	{
		try
		{
			socket = IO.socket("http://34.227.142.101:8090");
			System.out.println("socket init");
			socket.on("server ready", new Emitter.Listener()
			{
				@Override
				public void call(Object... args)
				{

					dialogSend.setVisible(true);
					System.out.println("Server ready");

					new Thread(new Runnable()
					{
						@Override
						public void run()
						{
							try
							{
								java.net.Socket sock;
								sock = new java.net.Socket("34.227.142.101", (int) args[0]);
								OutputStream is = sock.getOutputStream();
								FileInputStream fis = new FileInputStream((String) args[1]);
								BufferedInputStream bis = new BufferedInputStream(fis);
								byte[] buffer = new byte[4096];
								int ret;
								while ((ret = fis.read(buffer)) > 0)
								{
									is.write(buffer, 0, ret);
								}
								fis.close();
								bis.close();
								is.close();
								sock.close();
							}
							catch (IOException ex)
							{
								Logger.getLogger(EventlogController.class.getName()).log(Level.SEVERE, null, ex);
							}
						}
					}).start();
				}
			}).on(username.getText(), new Emitter.Listener()
			{
				@Override
				public void call(Object... args)
				{
					Platform.runLater(new Runnable()
					{
						@Override
						public void run()
						{
							Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
							alert.setTitle("Info");
							alert.setHeaderText("File Received");
							alert.setContentText("");
							Optional<ButtonType> result = alert.showAndWait();
							System.out.println(result);

							if (result.isPresent() && result.get() != ButtonType.OK)
							{
								return;
							}

							new Thread(new Runnable()
							{
								@Override
								public void run()
								{
									JDialog dialog = new JDialog();
									JLabel label = new JLabel("Please wait...");
									dialog.setLocationRelativeTo(null);
									dialog.setTitle("Please Wait...");
									dialog.add(label);
									dialog.setPreferredSize(new Dimension(300, 100));
									dialog.setAlwaysOnTop(true);
									dialog.pack();

									System.out.println("Receving File");
									String transferId = (String) args[2];
									try
									{
										FileOutputStream fos = new FileOutputStream((String) args[3]);
										java.net.Socket sock = new java.net.Socket("34.227.142.101", (int) args[1]);
										InputStream is = sock.getInputStream();
										byte[] buffer = new byte[4096];
										int ret;
										double sumCount = 0.0;

										dialog.setVisible(true);

										while ((ret = is.read(buffer)) > 0)
										{
											fos.write(buffer, 0, ret);

											sumCount += ret;
											System.out.println("Percentace: " + (int) ((sumCount / (int) args[9] * 100.0)) + "%");
											//Thread.sleep(1000);
											label.setText("Percentace: " + (int) ((sumCount / (int) args[9] * 100.0)) + "%");
										}
										dialog.setVisible(false);
										System.out.println("Transfer Finished");
										fos.close();
										is.close();
										sock.close();
										socket.emit("transfer finished", transferId);

										encryption _crypt = new encryption();
										byte[] salt = Base64.decodeBase64((String) args[6]);
										byte[] iv = Base64.decodeBase64((String) args[7]);
										System.out.println(salt);
										System.out.println(iv);
										System.out.println(salt.length);
										System.out.println(iv.length);
										_crypt.decryptFile((String) args[3], (String) args[4], (String) args[5], salt, iv, (String) args[8]);
									}
									catch (IOException ex)
									{
										Logger.getLogger(EventlogController.class.getName()).log(Level.SEVERE, null, ex);
									}
									catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeySpecException | InvalidAlgorithmParameterException | InvalidKeyException | BadPaddingException | IllegalBlockSizeException ex)
									{
										Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
									}
								}
							}).start();
						}
					});
				}
			}).on("receiving data", new Emitter.Listener()
			{
				@Override
				public void call(Object... args)
				{
					System.out.println("args[0] " + (String) args[0] + "args[1] " + args[1]);
					labelSend.setText((String) args[0]);
					if ((boolean) args[1] == true)
					{
						dialogSend.setVisible(false);
					}
				}
			});
			socket.connect();

		}
		catch (URISyntaxException ex)
		{
			System.out.println(ex.getMessage());
			System.out.println(ex.getReason());
		}

	}

}
