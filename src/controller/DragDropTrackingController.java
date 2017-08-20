/*
 * Projet Slidare
 * Sharing anywhere, anytime
 * 
 */
package controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.KeyEvent;
import model.TrackingInfo;
import view.DragDropTestFrame;
import utils.streaming.Settings;

/**
 *
 * @author Timothy
 */
public class DragDropTrackingController extends Controller implements Initializable 
{
    
    
   private boolean stateActivationApp = true;
//   private DragDropTestFrame dragDropFrame;
   private String keyName = "CTRL";
   private Alert alert;
   private int cnt = 0;
   private Settings settings = Settings.getTimSettingInstance("tim.properties");
    
    @FXML
    private ToggleButton activationApp;
    
    @FXML
    private Button activationButton;
    
    private boolean changeActivationButtonKey = false;
    @Override
    public void initialize(URL url, ResourceBundle rsrcs)
    {
        //TrackingServiceStub
        activationButton.setText(settings.getProperty("key") + " " +settings.getProperty("key2"));
        TrackingInfo.firstKeyName = settings.getProperty("key");
        TrackingInfo.secondKeyName = settings.getProperty("key2");
   
    }
    
    @FXML
    public void changeActivationKey(ActionEvent event)
    {
        changeActivationButtonKey = true;
    }
    
    
    
    @FXML
    public void  changeActivationButton(ActionEvent event)
    {
        if (stateActivationApp == true) {
            activationApp.setText("Off");
            stateActivationApp = false;
        } else {
            activationApp.setText("On");
            stateActivationApp = true;
        }
    }
    
    public String getKeyName()
    {
        return keyName;
    }
    
    @FXML
    public void  changeKeyPressedActivationButton(KeyEvent event)
    {
        if (changeActivationButtonKey == true) {
            
                ++cnt;
            if (cnt % 2 == 1)
            {
                activationButton.setText(event.getCode().getName());
                TrackingInfo.firstKeyName = event.getCode().getName();
                settings.setProperty("key", event.getCode().getName());
                settings.saveSettings();
                
            }
            if (cnt % 2 == 0)
            {
                activationButton.setText(activationButton.getText() + " " + event.getCode().getName());
                TrackingInfo.secondKeyName = event.getCode().getName();
                settings.setProperty("key2", event.getCode().getName());
                settings.saveSettings();                 
                changeActivationButtonKey = false;
            }
        }
    }
    
}
