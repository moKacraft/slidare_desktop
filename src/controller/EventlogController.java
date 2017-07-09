/*
* Projet Slidare
* Sharing anywhere, anytime
*
*/
package controller;

import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.BrowserPreferences;
import com.teamdev.jxbrowser.chromium.swing.BrowserView;
import java.awt.AWTException;
import java.awt.BorderLayout;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

import java.security.NoSuchAlgorithmException;

import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javax.swing.JFrame;
import javax.swing.JPopupMenu;

/**
 * FXML Controller class
 *
 * @author Madeline
 */
public class EventlogController extends Controller implements Initializable {

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
        frameBrowser.add(view, BorderLayout.CENTER);
        frameBrowser.setSize(800, 500);
        frameBrowser.setLocationRelativeTo(null);
        frameBrowser.setVisible(true);

        browser.loadURL("http://34.227.142.101:8080/streaming");
    }
    
    @FXML
    public void action1(ActionEvent event) throws IOException, URISyntaxException {

    }
    
    @FXML
    public void action2(ActionEvent event) throws IOException, NoSuchAlgorithmException, AWTException {

    }
}

