/*
 * Projet Slidare
 * Sharing anywhere, anytime
 * 
 */
package controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import model.TrackingInfo;
import view.DragDropGroupFrame;
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
    private TextField activationButton;
    
    private boolean changeActivationButtonKey = false;
    @Override
    public void initialize(URL url, ResourceBundle rsrcs)
    {
        //TrackingServiceStub
        TrackingInfo.listKeyName = new ArrayList<String>(); 
        activationButton.setText(settings.getProperty("key") + " " +settings.getProperty("key1"));
        Boolean state = false;
        cnt = 0;
        int countValid = 0;
        String tmp = "aze";  
        while (state == false && tmp != "")
        {
            String strKeyName = "key" + String.valueOf(cnt);
            tmp = settings.getProperty(strKeyName);
            //if (tmp == null)
              //  break;
            System.out.println(tmp);
            if (tmp != null)
            {
                System.out.println("tmp=" + tmp);
                TrackingInfo.listKeyName.add(tmp);
                cnt++;
             }
            countValid++;
            if (countValid > 10)
                break;
           /// TrackingInfo.secondKeyName = settings.getProperty("key2");
        }
        activationButton.setText(getTextFromList());
    }
    
   /* @FXML
    public void changeActivationKey(ActionEvent event)
    {
        changeActivationButtonKey = true;
         TrackingInfo.listKeyName = new ArrayList<String>(); 
    }*/
    
    @FXML
    public void MouseExited(MouseEvent event)
    {
        changeActivationButtonKey = false;
    }
    
    @FXML
    public void MouseCliked(MouseEvent event)
    {
        changeActivationButtonKey = true;
    }
    
   /* @FXML
    public void KeyPressed(ActionEvent event)
    {
        
    }*/
   
    
    
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
    
    private String getTextFromList()
    {
        String str = "";
        for (int increment = 0; increment < TrackingInfo.listKeyName.size(); increment++)
        {
            if (increment > 0)
                 str +=  " " + TrackingInfo.listKeyName.get(increment);
             else
                str += TrackingInfo.listKeyName.get(increment);
            System.out.println("value:" + TrackingInfo.listKeyName.get(increment));
        }
       System.out.println("str:" + str);
       return (str);
    }
    
    @FXML
    public void  changeKeyPressedActivationButton(KeyEvent event)
    {
        if (changeActivationButtonKey == true) 
        {
            if (event.getCode().getName().contains("Enter") == true)
            {
                changeActivationButtonKey = false;
                return;
            }
            if (event.getCode().getName().contains("Backspace") == true)
            {
             if (TrackingInfo.listKeyName.size() > 0)
             {
                --cnt;
                String strLast = "key" + String.valueOf(cnt);
                System.out.println("cnt" + cnt);
                settings.removeProperty(strLast, TrackingInfo.listKeyName.get(cnt));
                settings.saveSettings();
                TrackingInfo.listKeyName.remove(cnt);
                activationButton.clear();
                activationButton.setText(getTextFromList());   
             }
             return;
            }
            if (TrackingInfo.listKeyName.contains(event.getCode().getName()) == true)
                return;
             TrackingInfo.listKeyName.add(event.getCode().getName());
             if (cnt > 0)
                activationButton.setText(activationButton.getText() + " " + event.getCode().getName());
             else
                activationButton.setText(event.getCode().getName());  
           //    settings.setProperty("key", event.getCode().getName())
               
               String strKey = "key" + String.valueOf(cnt);
               settings.setProperty(strKey, event.getCode().getName());
               settings.saveSettings();
               ++cnt;
                //changeActivationButtonKey = false;
        }
    }
    
}
