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
import javafx.scene.control.ButtonType;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.KeyEvent;
import model.TrackingInfo;
import view.DragDropTestFrame;
import model.TrackingServiceStub;

/**
 *
 * @author Timothy
 */
public class DragDropTrackingController extends Controller implements Initializable {
    
    
   private boolean stateActivationApp = true;
   private DragDropTestFrame dragDropFrame;
   private String keyName = "CTRL";
   private Alert alert;
    
    @FXML
    private ToggleButton activationApp;
    
    @FXML
    private Button activationButton;
    
    private boolean changeActivationButtonKey = false;
    @Override
	public void initialize(URL url, ResourceBundle rsrcs)
	{
             //TrackingServiceStub
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
                TrackingInfo.keyName = event.getCode().getName();;
                changeActivationButtonKey = false;
            }
        }
       
}
