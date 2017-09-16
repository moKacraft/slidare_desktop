/*
* Projet Slidare
* Sharing anywhere, anytime
*
*/
package controller;

import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.BrowserPreferences;
import com.teamdev.jxbrowser.chromium.swing.BrowserView;
import java.awt.BorderLayout;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.HBox;
import javax.swing.JFrame;
import javax.swing.JPopupMenu;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameGrabber;
import org.bytedeco.javacv.FrameRecorder;
import service.ConfigManager;
import service.FileManager;
import service.ServiceFactory;
import utils.DialogSys;
import utils.streaming.Settings;


import javax.swing.JFrame;
import utils.streaming.displayStreaming;

/**
 *
 * @author Flavien Maillot "flavien.maillot@epitech.eu"
 */
public class MenubarController implements Initializable
{
    
    @FXML
    private HBox MenuBarHB;
    
    private static Frame grabbedFrame;
    
    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
    }
    
    public void loadConfig(ActionEvent event) throws IOException
    {
        FileManager fileManager = (FileManager) ServiceFactory.getFileManager();
        fileManager.initFileChooserConfig(Main.parentWindow);
        String filepath = fileManager.getFilePath();
        
        if (!filepath.equals("")) {
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
        if (null != menuId) {
            switch (menuId) {
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
                    utils.streaming.Controller controller = new utils.streaming.Controller();
                    //Settings settings = controller.getSettings();
                    Toolkit kit = Toolkit.getDefaultToolkit();
                    Dimension screenSize = kit.getScreenSize();
                    int screenWidth = screenSize.width;
                    int screenHeight = screenSize.height;
                    System.out.println("CA VA PLANTEEEER!");
                    //double frameRate = Double.parseDouble(settings.getProperty("frameRate"));
                    double frameRate = 10;
                    FFmpegFrameGrabber grabber = new FFmpegFrameGrabber("desktop");
                    grabber.setFormat("gdigrab");
                    grabber.setFrameRate(frameRate);
                    grabber.setImageWidth(screenWidth);
                    grabber.setImageHeight(screenHeight);
                    grabber.start();
                    utils.streaming.CanvasFrame frame = new utils.streaming.CanvasFrame("Screen Capture", utils.streaming.CanvasFrame.getDefaultGamma()/grabber.getGamma());
                    frame.setCanvasSize(screenWidth/2, screenHeight/2);
                    frame.setController(controller);
                    System.out.println("CA VA PLANTEEEER!");
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                while (frame.isVisible()) {
                                    System.out.println("dans le while");
                                    grabbedFrame = grabber.grab();
                                    controller.recorder(grabbedFrame);
                                }
                                frame.dispose();
                                controller.clean();
                                grabber.stop();
                            } catch (FrameGrabber.Exception ex) {
                                Logger.getLogger(MenubarController.class.getName()).log(Level.SEVERE, null, ex);
                            } catch (FrameRecorder.Exception ex) {
                                Logger.getLogger(MenubarController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }}).start();
                    break;
                case "stream":
                    JPopupMenu.setDefaultLightWeightPopupEnabled(false);
                    final Browser browser = new Browser();
                    BrowserPreferences preferences = browser.getPreferences();
                    preferences.setPluginsEnabled(true);
                    preferences.setJavaScriptEnabled(true);
                    browser.setPreferences(preferences);
                    BrowserView view = new BrowserView(browser);

                    JFrame frameBrowser = new JFrame("Streaming");
                    frameBrowser.add(view, BorderLayout.CENTER);
                    frameBrowser.setSize(800, 500);
                    frameBrowser.setLocationRelativeTo(null);
                    frameBrowser.setVisible(true);
//
//                    browser.loadURL("http://34.227.142.101:8080/streaming");
                    
//                    final JFrame frameStreaming = new JFrame();
//                    
//                    frameStreaming.getContentPane().add(new displayStreaming());
//                    
//                    frameStreaming.setMinimumSize(new Dimension(640, 480));
//                    //frameStreaming.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//                    frameStreaming.setVisible(true);
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
        if (result.get() == buttonTypeOne) {
            try {
                Desktop.getDesktop().browse(new URL(bundle.getString("AboutUs_URL")).toURI());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
