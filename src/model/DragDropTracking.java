
package model;

import com.oracle.deploy.update.Updater;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.KeyListener;
import org.jnativehook.GlobalScreen;
import view.DragDropTestFrame;
import model.MouseListener;
import org.jnativehook.NativeHookException;
import utils.streaming.Settings;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import service.APIManager;
import service.ConfigManager;
import service.PackageManager;
import service.ServiceFactory;
import view.PopUpRotation;

/**
 *
 * @author Timothy
 */
public class DragDropTracking 
{
    private DragDropTestFrame dragDropFrame;
    private MouseListener mouseListener;
    private KeyListener keyListener;
    private  List<DragDropTestFrame> listFrame = new ArrayList<DragDropTestFrame>();
    private boolean PopupEntered = true;
    public static DragDropTracking dragDropTracking;
    private boolean showPopup = false;
    private int numberOfContact = 0;
    private boolean createdContacts = false;
    public  static List<JSONObject> listContacts = new ArrayList<JSONObject>();
    
    private Settings settings = Settings.getTimSettingInstance("tim.properties");

    private PackageManager packageManager;
    private APIManager APIManager;
    private ConfigManager cfg;
    private PopUpRotation up;
    private PopUpRotation down;
    
    //private PopUpRotation up
       
    
    public DragDropTracking() throws NativeHookException
    {
        dragDropFrame = new DragDropTestFrame(0);
        dragDropFrame.setPopUpType(0, 1);
        dragDropTracking = this;
        this.packageManager = (PackageManager) ServiceFactory.getPackageManager();
        this.APIManager = (APIManager) ServiceFactory.getAPIManager();
        this.cfg = (ConfigManager) ServiceFactory.getConfigManager();
        
        GlobalScreen.registerNativeHook();
        Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
        logger.setLevel(Level.OFF);
        
        keyListener = new KeyListener();
        GlobalScreen.addNativeKeyListener(keyListener);
        
        up = new PopUpRotation(240,-60,0);
        up.popUpListener.ddt = this;
        down = new PopUpRotation(-60,240,1);
        down.popUpListener.ddt = this;
        
        // Construct the example object.
        mouseListener = new MouseListener();
        
        // Add the appropriate listeners.
        GlobalScreen.addNativeMouseListener(mouseListener);
        GlobalScreen.addNativeMouseMotionListener(mouseListener);
        int cnt = 0;
        TrackingInfo.listKeyName = new ArrayList<String>();
        int countValid = 0;
        String tmp = "aze";
        Boolean state = false;
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
       
        
    }
    
    public void MouseInput(int x, int y, int type )
    {
        double rotationValue = 0.0;
        if (type == 0)
            rotationValue = (double)y;
        if (type == 1)
            rotationValue = (double)x;
        System.out.println("x: " +  rotationValue);
        rotationValue = (-rotationValue  + 120 );//Value Size;
        //rotationValue = -rotationValue;
        rotationValue = rotationValue / 120/100;
        if (type == 0)
            rotationValue = -rotationValue;     
       for (int i = 0; i < listFrame.size(); i++)
       {
           Point.Double p  = listFrame.get(i).rotation(new Point.Double(listFrame.get(i).position.x,listFrame.get(i).position.y),/*listFrame.get(i).currentAngle + */rotationValue);
           listFrame.get(i).setPosition(p);
           listFrame.get(i).determineStateOfVisibility();
       }
    }
    
    public void showPathText(String text)
    {
        dragDropFrame.setTextFrame(text);
    }
    
    public void showBubble()
    {
        //System.out.println("124");
        dragDropFrame.setVisible(true);
        up.setVisible(true);
        down.setVisible(true);
        if (createdContacts  == true) 
        {
            return;
        }
        //System.out.println("987");
        APIManager manager = (APIManager) ServiceFactory.getAPIManager();
//     System.out.println(this.cfg.getConfig().getToken());
        manager.userContacts(this.cfg.getConfig().getToken());

        this.packageManager.setJSONObject(this.APIManager.getLastResponse());
        JSONObject contacts = this.packageManager.getJSONOBject();
        JSONArray arr = (JSONArray) contacts.get("contacts");
        if (arr == null) {
            return;
        }

        if (createdContacts  == false) {
            for (int i = 0;i < arr.size() ; i++) {
                ///      System.out.println((String)((JSONObject) arr.get(i)).get("first_name"));
                listContacts.add(((JSONObject) arr.get(i)));
                CreatGroupContactPopup((String)((JSONObject) arr.get(i)).get("first_name"), (i/5) + 2);
            }
            createdContacts = true;
        }
    }
    
    public void hideBubble()
    {
        dragDropFrame.setVisible(false);
        up.setVisible(false);
     
 
        down.setVisible(false);
    }
    
    public void CreatGroupContactPopup(String name, int width)
    {
        DragDropTestFrame tmp;
       // dragDropFrame.setPopUpType(0, 1);
        listFrame.add(tmp = new DragDropTestFrame(1));
        tmp.setMessage(name);
        tmp.setPopUpType(1, width);
        tmp.setVisible(false);
        ++numberOfContact;
        tmp.setNumberOfContact(numberOfContact);
        PopupEntered = true;
    }
    
    public void ShowGroupMiniPopUp()
    {
        if (PopupEntered == true) 
        {
            System.out.println("787878");
            int cnt = 0;
            while (cnt < numberOfContact) 
            {
                System.out.println("7****");
                listFrame.get(cnt).visible(true);
                
                listFrame.get(cnt).determineStateOfVisibility();
                
                showPopup = false;
                ++cnt;
            }
            PopupEntered = false;
        }
    }
    
    public void ShowMiniPopUp(int cnt)
    {
        if (PopupEntered == true) 
        {
            HideGroupMiniPopUp();
            listFrame.get(cnt).visible(true);
            ++cnt;
            PopupEntered = false;
        }
    }
    
    public void HideGroupMiniPopUp()
    {
        if (PopupEntered == false && listFrame != null && listFrame.isEmpty() == false) {
            PopupEntered = true;
            int cnt = 0;
            while (cnt < numberOfContact) {
                listFrame.get(cnt).visible(false);
                showPopup = false;
                ++cnt;
            }
        }
    } 
}
