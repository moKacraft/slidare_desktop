/*
* Projet Slidare
* Sharing anywhere, anytime
*
*/
package controller;

//import com.corundumstudio.socketio.Configuration;
//import com.corundumstudio.socketio.SocketIOServer;
//import io.socket.client.IO;
//import io.socket.emitter.Emitter;
import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.BrowserPreferences;
import com.teamdev.jxbrowser.chromium.swing.BrowserView;
import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
//import java.awt.Dimension;
//import java.awt.Graphics2D;
//import java.awt.Rectangle;
//import java.awt.Robot;
//import java.awt.Toolkit;
//import java.awt.image.BufferedImage;
//import java.io.ByteArrayInputStream;
//
//import java.io.ByteArrayOutputStream;
import java.io.IOException;
//import java.net.DatagramPacket;
//import java.net.DatagramSocket;
//import java.net.SocketException;
import java.net.URISyntaxException;
import java.net.URL;

import java.security.NoSuchAlgorithmException;

import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javax.swing.JFrame;
import javax.swing.JPopupMenu;
import javax.swing.WindowConstants;
import org.bytedeco.javacv.CanvasFrame;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameGrabber;
import org.bytedeco.javacv.FrameRecorder;
import utils.streaming.Settings;
//import javafx.scene.control.TextArea;
//import javafx.scene.image.Image;
//import javafx.scene.image.ImageView;
//import javafx.scene.layout.AnchorPane;
//import javafx.scene.web.WebEngine;
//import javafx.scene.web.WebView;
//import javax.imageio.ImageIO;
//import javax.swing.JDialog;
//import javax.swing.JLabel;
//import org.apache.commons.codec.binary.Base64;
//
//import utils.DialogSys;
/**
 * FXML Controller class
 *
 * @author Madeline
 */
public class EventlogController extends Controller implements Initializable {

//    @FXML
//    public TextArea feedBack;
// 
//    @FXML
//    public AnchorPane anchorID;
//
//    @FXML
//    public ImageView imageview;
//    
//    DialogSys ds = new DialogSys();
//    BufferedImage[] images = null;
//
//    io.socket.client.Socket socket;
//    
//    public static JDialog dialogServer;
//    public static JLabel labelServer;
//    
//    public static boolean serverIsAvailable = false;
    //private static Frame grabbedFrame;
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    public void initialize(URL url, ResourceBundle rb) {
        
//        try {
            JPopupMenu.setDefaultLightWeightPopupEnabled(false);
            final Browser browser = new Browser();
            BrowserPreferences preferences = browser.getPreferences();
            preferences.setPluginsEnabled(true);
            preferences.setJavaScriptEnabled(true);
            browser.setPreferences(preferences);
            BrowserView view = new BrowserView(browser);
            

        JFrame frameBrowser = new JFrame("Streaming");
        //frameBrowser.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frameBrowser.add(view, BorderLayout.CENTER);
        frameBrowser.setSize(800, 500);
        frameBrowser.setLocationRelativeTo(null);
        frameBrowser.setVisible(true);

        browser.loadURL("http://34.227.142.101:8080/streaming");


//        utils.streaming.Controller controller = new utils.streaming.Controller();
//        Settings settings = controller.getSettings();
//
//
//        Toolkit kit = Toolkit.getDefaultToolkit();
//        Dimension screenSize = kit.getScreenSize();
//        int screenWidth = screenSize.width;
//        int screenHeight = screenSize.height;
//
//        double frameRate = Double.parseDouble(settings.getProperty("frameRate"));
//        FFmpegFrameGrabber grabber = new FFmpegFrameGrabber("desktop");
//        grabber.setFormat("gdigrab");
//        grabber.setFrameRate(frameRate);
//        grabber.setImageWidth(screenWidth);
//        grabber.setImageHeight(screenHeight);
//        grabber.start();
//        utils.streaming.CanvasFrame frame = new utils.streaming.CanvasFrame("Screen Capture", utils.streaming.CanvasFrame.getDefaultGamma()/grabber.getGamma());
//        frame.setCanvasSize(screenWidth/2, screenHeight/2);
//        frame.setController(controller);
//        controller.startRtmpRecorder();
//        while (frame.isVisible()) {
//            System.out.println("dans le while");
//            grabbedFrame = grabber.grab();
//            frame.showImage(grabbedFrame);
//            controller.recorder(grabbedFrame);
//        }
//        frame.dispose();
//        controller.clean();
//        grabber.stop();
//
//        } catch (FrameGrabber.Exception ex) {
//            Logger.getLogger(EventlogController.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (FrameRecorder.Exception ex) {
//            Logger.getLogger(EventlogController.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }
    
    @FXML
    public void action1(ActionEvent event) throws IOException, URISyntaxException {

    }
    
    @FXML
    public void action2(ActionEvent event) throws IOException, NoSuchAlgorithmException, AWTException {

    }
}

