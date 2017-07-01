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
       
    
    public DragDropTracking() throws NativeHookException
    {
        dragDropFrame = new DragDropTestFrame();
        dragDropFrame.setPopUpType(true);
         listFrame = new ArrayList<DragDropTestFrame>();
           dragDropTracking = this;  
        
          
           
        GlobalScreen.registerNativeHook();
        Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
            logger.setLevel(Level.WARNING);
        
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
   }
   
    public void hideBubble()   
   {
     dragDropFrame.setVisible(false);
   }
  
   
    
 
    
    public void ShowMiniPopUp()
    {
             System.out.println("//////////////////////////");
             System.out.println("//////////////////////////");
       
        if (PopupEntered == true)
        {
            System.out.println("---*-**--*--*-0");
        showPopup = true;
        DragDropTestFrame tmp;
        listFrame.add(tmp = new DragDropTestFrame());
        tmp.setPopUpType(false);
        tmp.setMessage("Paul");
        listFrame.add(tmp = new DragDropTestFrame());
        tmp.setPopUpType(false);
        tmp.setMessage("Bob");
        listFrame.add(tmp = new DragDropTestFrame());
        tmp.setPopUpType(false);
        tmp.setMessage("John");
             PopupEntered = false;
   
        }
    }
    
    
    public void HideMiniPopUp()
    { 
       
        
        if (showPopup == true && listFrame != null && listFrame.isEmpty() == false)
        {
          PopupEntered = true;
   
            showPopup = false;
         (listFrame.get(0)).dispose();
         
         (listFrame.get(1)).dispose();
         (listFrame.get(2)).dispose();
         listFrame.clear();
         listFrame.removeAll(listFrame);
   
        }
    }
    
    
}
