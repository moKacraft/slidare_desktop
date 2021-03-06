
package model;

import com.oracle.deploy.update.Updater;
import controller.GroupTrackingController;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableMap;
import model.KeyListener;
import org.jnativehook.GlobalScreen;
import view.DragDropGroupFrame;
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
    private DragDropGroupFrame dragDropFrame;
    private MouseListener mouseListener;
    private KeyListener keyListener;
    private  List<DragDropGroupFrame> listFrame = new ArrayList<DragDropGroupFrame>();
    private boolean PopupEntered = true;
    private boolean PopupMiniEntered = true;
    public static DragDropTracking dragDropTracking;
    private boolean showPopup = false;
    private int numberOfGroupContact = 0;
    private boolean createdContacts = false;
    public  static List<JSONObject> listGroups = new ArrayList<JSONObject>();
    
    private Settings settings = Settings.getTimSettingInstance("tim.properties");
    private  List<String> listAddresses;    
    private PackageManager packageManager;
    private APIManager APIManager;
    private ConfigManager cfg;
    private PopUpRotation up;
    private PopUpRotation down;
    
    //private PopUpRotation up
       
    
    public DragDropTracking() throws NativeHookException
    {
        this.APIManager = (APIManager) ServiceFactory.getAPIManager();
        this.cfg = (ConfigManager) ServiceFactory.getConfigManager();
        this.packageManager = (PackageManager) ServiceFactory.getPackageManager();
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
        listAddresses = new ArrayList<String>();
        dragDropTracking = this;
        dragDropFrame = new DragDropGroupFrame(0);
        dragDropFrame.setPopUpType(0);
        dragDropFrame.setVisible(false);
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
    
    public void MouseInput(int type)
    {
        double rotationValue = 0.0;
        //System.out.println("x: " +  rotationValue);
      
        if (type == 0)
            rotationValue = -0.02;
         if (type == 1)
            rotationValue = 0.02;
        System.out.println("x: " +  rotationValue);
        
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
       // manager.userContacts(this.cfg.getConfig().getToken());
        manager.fetchGroups(this.cfg.getConfig().getToken());
        //manager.userContact(token, id)
        //manager.us
        //this.packageManager.
        this.packageManager.setJSONObject(this.APIManager.getLastResponse());
        JSONObject groups = this.packageManager.getJSONOBject();
        JSONArray arr = (JSONArray) groups.get("groups");
        
        
        manager.userContacts(this.cfg.getConfig().getToken());
        this.packageManager.setJSONObject(this.APIManager.getLastResponse());
        JSONObject users = this.packageManager.getJSONOBject();
        System.out.println(users);
        JSONArray arrContact = (JSONArray) users.get("contacts");
        //JSONArray Contact = (JSONArray) arrContact.get("email");
        //manager.fetchGroups(this.cfg.getConfig().getToken());
        //manager.userContact(token, id)
        //manager.us
        //this.packageManager.
        //this.packageManager.setJSONObject(this.APIManager.getLastResponse());
        //JSONObject groups = this.packageManager.getJSONOBject();
        //JSONArray arr = (JSONArray) groups.get("groups");
       // JSONArray users = (JSONArray) groups.get("users");
        if (arr == null) 
        {
            return;
        }
        Boolean state = false;
//Replace By group contact
        if (createdContacts  == false) 
        {
            /*ObservableMap<String, Group> pair = TrackingServiceStub.TSS.getGroups();
           // TrackingServiceStub.TSS.get
             for (Group s : pair.values()) 
             {
                 System.out.println(s.getName());
                 System.out.println("value= " + s.getUsers());
                CreatGroupContactPopup(s.getName(), (List<String>)((JSONObject)s.getUsers()));
               }*/
             //pair.values().
            //TrackingServiceStub.
            for (int i = 0;i < arr.size() ; i++) {
                //System.out.println("pp");
                ///      System.out.println((String)((JSONObject) arr.get(i)).get("first_name"));
               if ( (List<String>)((JSONObject) arr.get(i)).get("users") != null)
               {
                   state = true;
                //listGroups.add(((JSONObject) arr.get(i)));
                CreatGroupContactPopup((String)((JSONObject) arr.get(i)).get("name"), (List<String>)((JSONObject) arr.get(i)).get("users"));
               // List<String> strs =  (List<String>)((JSONObject) arr.get(i)).get("users");
               }
            }
              for (int i = 0;i < arrContact.size() ; i++) 
                {
                    listAddresses.add((String)((JSONObject) arrContact.get(i)).get("email"));
                  //  listGroups. add("Default");
              }
            CreatGroupContactPopup("default", listAddresses);   
            createdContacts = true;
        }
    }
    
    public void hideBubble()
    {
        dragDropFrame.setVisible(false);
        up.setVisible(false);
     
 
        down.setVisible(false);
    }
    
    public void CreatGroupContactPopup(String name, List<String> strs)
    {
        DragDropGroupFrame tmp;
       // dragDropFrame.setPopUpType(0, 1);
        listFrame.add(tmp = new DragDropGroupFrame(1));
        tmp.setMessage(name);
        //tmp
        tmp.setPopUpType(1);
        tmp.setVisible(false);
        ++numberOfGroupContact;
         if (strs != null)
                for (int inc = 0; inc < strs.size() ; inc++)
                {
                   System.out.println("**: " + strs.get(inc));
                   tmp.setPeopleInGroup(strs.get(inc));
                
                }
        tmp.setDragDropBoundaries();       
        tmp.setNumberOfContact(numberOfGroupContact);
        PopupEntered = true;
       
    }
    
    public void ShowGroupMiniPopUp()
    {
        if (PopupEntered == true) 
        {
            // PopupMiniEntered = true;
            int cnt = 0;
            while (cnt < numberOfGroupContact) 
            {
                listFrame.get(cnt).visible(true);
                listFrame.get(cnt).determineStateOfVisibility();
                //showPopup = false;
                ++cnt;
            }
            PopupEntered = false;
        }
    }
    
    public void ShowMiniPopUp(int cnt)
    {
        if (PopupMiniEntered == true) 
        {
            HideGroupMiniPopUp();
            listFrame.get(cnt).SetContactsPosition();
            listFrame.get(cnt).visible(true);
            listFrame.get(cnt).showBoundaries(true);
            //listFrame.get(cnt).SetContactsPosition();
            PopupMiniEntered = false;
        }
    }
     
    public void HideMiniPopUp()
    {
            PopupMiniEntered = true;
             int cnt = 0;
           while (cnt < numberOfGroupContact) 
            {
                listFrame.get(cnt).hideContacts();
                 listFrame.get(cnt).showBoundaries(false);
                ++cnt;
            }
            
             //showPopup = false;
        //    ++cnt;
            
    }
    
    public void HideGroupMiniPopUp()
    {
        if (PopupEntered == false && listFrame != null && listFrame.isEmpty() == false) {
            PopupEntered = true;
            int cnt = 0;
           while (cnt < numberOfGroupContact) 
            {
                listFrame.get(cnt).visible(false);
                //HideMiniPopUp(cnt);
                //showPopup = false;
                ++cnt;
            }
        }
    } 
}
