/*
 * Projet Slidare
 * Sharing anywhere, anytime
 * 
 */
package model;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
       
    
    public DragDropTracking() throws NativeHookException
    {
        dragDropFrame = new DragDropTestFrame();
        dragDropFrame.setPopUpType(true, 1);
        dragDropTracking = this;  
        this.packageManager = (PackageManager) ServiceFactory.getPackageManager();
        this.APIManager = (APIManager) ServiceFactory.getAPIManager();
        this.cfg = (ConfigManager) ServiceFactory.getConfigManager();
        
        GlobalScreen.registerNativeHook();
        Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
        logger.setLevel(Level.OFF);
        
        keyListener = new KeyListener();   
        GlobalScreen.addNativeKeyListener(keyListener);
             
             
             
        // Construct the example object.
       mouseListener = new MouseListener();

        // Add the appropriate listeners.
        GlobalScreen.addNativeMouseListener(mouseListener);
        GlobalScreen.addNativeMouseMotionListener(mouseListener);
      
        //System.out.println("azea");
        
        TrackingInfo.firstKeyName = settings.getProperty("key");
        TrackingInfo.secondKeyName = settings.getProperty("key2");
       
        
    }
    
    public void showPathText(String text)
    {
     dragDropFrame.setTextFrame(text);
    }
    
    public void showBubble()   
    {     
        dragDropFrame.setVisible(true);
        if (createdContacts  == true)
            return;
    APIManager manager = (APIManager) ServiceFactory.getAPIManager();
//     System.out.println(this.cfg.getConfig().getToken());
    manager.userContacts(this.cfg.getConfig().getToken());

    this.packageManager.setJSONObject(this.APIManager.getLastResponse());
    JSONObject contacts = this.packageManager.getJSONOBject();
    JSONArray arr = (JSONArray) contacts.get("contacts");
    if (arr == null)
        return;

    if (createdContacts  == false) {
        for (int i = 0;i < arr.size() ; i++) {
      ///      System.out.println((String)((JSONObject) arr.get(i)).get("first_name"));
            listContacts.add(((JSONObject) arr.get(i)));
            CreatContactPopup((String)((JSONObject) arr.get(i)).get("first_name"), (i/5) + 2);
        }
        createdContacts = true;
        }
    }
   
    public void hideBubble()   
    {
        dragDropFrame.setVisible(false);
    }
  
    public void CreatContactPopup(String name, int width)
    {
        DragDropTestFrame tmp;
        listFrame.add(tmp = new DragDropTestFrame());
        tmp.setPopUpType(false, width);
        tmp.setMessage(name);
        tmp.setVisible(false);
        ++numberOfContact; 
        tmp.setNumberOfContact(numberOfContact);
        PopupEntered = true;   
    }
    
    public void ShowMiniPopUp()
    {
        if (PopupEntered == true) {
            int cnt = 0;
            while (cnt < numberOfContact) {
                listFrame.get(cnt).visible(true);
                showPopup = false;
                ++cnt;
            }
            PopupEntered = false;
        }
    }
    
    
    public void HideMiniPopUp()
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
