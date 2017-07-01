/*
 * Projet Slidare
 * Sharing anywhere, anytime
 * 
 */
package controller;

import java.awt.Desktop;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.KeyEvent;
import javax.management.Notification;
import model.TrackingInfo;
import view.DragDropTestFrame;


/**
 *
 * @author Timothy
 */
public class DragDropTrackingController extends Controller implements Initializable {
    
    
   private boolean stateActivationApp = true;
   private DragDropTestFrame dragDropFrame;
   private String keyName = "CTRL";
    
    @FXML
    private ToggleButton activationApp;
    
    @FXML
    private Button activationButton;
    
    private boolean changeActivationButtonKey = false;
    @Override
	public void initialize(URL url, ResourceBundle rsrcs)
	{
            
        }
        
        @FXML
	public void changeActivationKey(ActionEvent event)
	{
            changeActivationButtonKey = true;
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		alert.setTitle("");
		alert.setHeaderText("PRESS A KEY");
                alert.setContentText("");
                alert.showAndWait();
                
                if (alert.getResult() == ButtonType.CLOSE || alert.getResult() == ButtonType.CANCEL)
        		changeActivationButtonKey = false;
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
                //keyName = event.getCode().getName();
                TrackingInfo.keyName = event.getCode().getName();;
                changeActivationButtonKey = false;
            }
        }
       
}
