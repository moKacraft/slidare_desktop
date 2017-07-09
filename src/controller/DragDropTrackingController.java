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
public class DragDropTrackingController extends Controller implements Initializable {
    
    
   private boolean stateActivationApp = true;
   private DragDropTestFrame dragDropFrame;
   private String keyName = "CTRL";
   private Alert alert;
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
            activationButton.setText(settings.getProperty("key"));
            TrackingInfo.keyName = settings.getProperty("key");
        }
        
        @FXML
	public void changeActivationKey(ActionEvent event)
	{
            changeActivationButtonKey = true;
           
                
        }	
        
        
        
          @FXML
	public void  changeActivationButton(ActionEvent event)
        {
            if (stateActivationApp == true)
            {
                activationApp.setText("Off");
                stateActivationApp = false;
            }
            else
            {
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
            if (changeActivationButtonKey == true)
            {
                activationButton.setText(event.getCode().getName());
                TrackingInfo.keyName = event.getCode().getName();
                
                settings.setProperty("key", event.getCode().getName());
                settings.saveSettings();
                
                changeActivationButtonKey = false;
            }
        }
       
}
