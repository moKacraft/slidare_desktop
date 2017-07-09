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
//import org.jnativehook.keyboard.NativeKeyEvent;
//import org.jnativehook.keyboard.NativeKeyListener;
//import model.IKeyHandle;
//import model.KeyHandleWindows;
import model.MouseListener;
import org.jnativehook.NativeHookException;

/**
 *
 * @author Timothy
 */
public class DragDropTracking 
{
    private DragDropTestFrame dragDropFrame;
    private MouseListener mouseListener;
    private KeyListener keyListener;
    private  List<DragDropTestFrame> listFrame;
    private boolean PopupEntered = true;
    public static DragDropTracking dragDropTracking;
    private boolean showPopup = false;
    private int numberOfContact = 0;
    private boolean createdContacts = false;
       
    
    public DragDropTracking() throws NativeHookException
    {
        dragDropFrame = new DragDropTestFrame();
        dragDropFrame.setPopUpType(true, 1);
        listFrame = new ArrayList<DragDropTestFrame>();
        dragDropTracking = this;  
        
          
           
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
        
    }
    
    public void showPathText(String text)
    {
     dragDropFrame.setTextFrame(text);
    }
    
     public void showBubble()   
   {     
     dragDropFrame.setVisible(true);
     if (createdContacts  == false)
     {
       for (int i = 0;i <TrackingServiceStub.contacts.size() ; i++)
        {
            System.out.println(TrackingServiceStub.contacts.isEmpty());
            CreatContactPopup(TrackingServiceStub.contacts.get(i).getFirstname(), (i/5) + 2);
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
             System.out.println("//////////////////////////");
             System.out.println("//////////////////////////");
       
        if (PopupEntered == true)
        {
         int cnt = 0;
          while (cnt < numberOfContact)
          {
            listFrame.get(cnt).visible(true);
            showPopup = false;
            ++cnt;
          }
             PopupEntered = false;
   
        }
    }
    
    
    public void HideMiniPopUp()
    { 
       
        
        if (PopupEntered == false && listFrame != null && listFrame.isEmpty() == false)
        {
          PopupEntered = true;
          int cnt = 0;
          while (cnt < numberOfContact)
          {
            listFrame.get(cnt).visible(false);
            showPopup = false;
            ++cnt;
          }
         //(listFrame.get(0)).dispose();
         
         //(listFrame.get(1)).dispose();
         //(listFrame.get(2)).dispose();
         //listFrame.clear();
         //listFrame.removeAll(listFrame);
   
        }
    }
    
    
}
